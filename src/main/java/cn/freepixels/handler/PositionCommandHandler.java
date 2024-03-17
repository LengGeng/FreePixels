package cn.freepixels.handler;

import cn.freepixels.models.Location;
import cn.freepixels.models.Position;
import cn.freepixels.repositories.LocationRepository;
import cn.freepixels.utils.MarkUtils;
import cn.freepixels.utils.Utils;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.*;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Objects;

public class PositionCommandHandler extends CommandHandler {
    public static @NotNull String getArgumentName(CommandContext<ServerCommandSource> context) {
        return StringArgumentType.getString(context, "name").replace(" ", "_");
    }

    public static int list(@NotNull CommandContext<ServerCommandSource> context) {
        ServerCommandSource source = context.getSource();

        ArrayList<Location> locations = LocationRepository.getLocations();


        MutableText text = MarkUtils.markWarp("[Freepixels] 地点列表").formatted(Formatting.BLUE, Formatting.BOLD);

        int index = 1;
        for (Location location : locations) {
            MutableText line = MarkUtils.markSpace("[#" + index + "]").append(MarkUtils.markLocation(location));
            text.append(MarkUtils.markWarp(line));

            index++;
        }

        sendFeedback(source, text);
        return 1;
    }

    @SuppressWarnings("SameReturnValue")
    public static int search(@NotNull CommandContext<ServerCommandSource> context) {
        ServerCommandSource source = context.getSource();

        String name = getArgumentName(context);
        // 检查地点名称
        if (name.isEmpty()) {
            sendErrorFeedback(source, "[Freepixels] 请输入正确的地点名称!");
            return 1;
        }

        Location location = LocationRepository.getLocationByName(name);
        if (location == null) {
            sendErrorFeedback(source, "[Freepixels] 该地点不存在!");
            return 1;
        }


        MutableText text = MarkUtils.markWarp("[Freepixels] 地点搜索结果").formatted(Formatting.BLUE, Formatting.BOLD);

        text.append(MarkUtils.markLocation(location));

        sendFeedback(source, text);
        return 1;
    }

    @SuppressWarnings("SameReturnValue")
    public static int add(@NotNull CommandContext<ServerCommandSource> context) {
        ServerCommandSource source = context.getSource();

        String name = getArgumentName(context);
        // 检查地点名称
        if (name.isEmpty()) {
            sendErrorFeedback(source, "[Freepixels] 请输入正确的名称!");
            return 1;
        }

        // 判断是否存在相同地点
        if (LocationRepository.getLocationByName(name) != null) {
            sendErrorFeedback(source, "[Freepixels] 地点已存在!");
            return 1;
        }

        // 获取玩家所在世界的名称
        String world = source.getWorld().getRegistryKey().getValue().toString();
        // 获取玩家位置
        Vec3d position = source.getPosition();
        // 获取玩家名称和UUID
        String playerName = Objects.requireNonNull(source.getPlayer()).getName().getString();
        String playerUUID = source.getPlayer().getUuidAsString();

        Location location = new Location();
        location.setName(name);
        location.setWorld(world);
        location.setCreator(playerName);
        location.setCreatorId(playerUUID);
        location.setCreatorTime(Utils.GetServerTime());
        location.setPosition(position);

        // 执行写入
        if (!LocationRepository.addLocation(location)) {
            sendErrorFeedback(source, "[Freepixels] 添加失败,无法更新存储记录!");
            return 1;
        }

        sendSuccessFeedback(source, "[Freepixels] 地点<" + name + ">加成功!");
        return 1;
    }

    @SuppressWarnings("SameReturnValue")
    public static int delete(@NotNull CommandContext<ServerCommandSource> context) {
        ServerCommandSource source = context.getSource();

        String name = getArgumentName(context);
        // 检查地点名称
        if (name.isEmpty()) {
            sendErrorFeedback(source, "[Freepixels] 请输入正确的地点名称!");
            return 1;
        }

        Location location = LocationRepository.getLocationByName(name);
        if (location == null) {
            sendErrorFeedback(source, "[Freepixels] 该地点不存在!");
            return 1;
        }

        // 获取玩家的UUID
        String playerUUID = Objects.requireNonNull(source.getPlayer()).getUuidAsString();

        // 检查是否是删除自己的
        if (!Objects.equals(location.getCreatorId(), playerUUID)) {
            // 检查是不是OP权限
            if (!source.hasPermissionLevel(4)) {
                sendErrorFeedback(source, "[Freepixels] 您只能删除自己创建的地点!");
                return 1;
            }
        }

        // 执行删除
        if (!LocationRepository.deleteLocation(name)) {
            sendErrorFeedback(source, "[Freepixels] 添加失败,无法更新存储记录!");
            return 1;
        }

        sendSuccessFeedback(source, "[Freepixels] 删除地点成功!");
        return 1;
    }

    public static int help(@NotNull CommandContext<ServerCommandSource> context) {
        ServerCommandSource source = context.getSource();


        Text HelpLinMessage = MarkUtils.markWarp("[Freepixels] 位置插件菜单").formatted(Formatting.BLUE, Formatting.BOLD)
                .append(MarkUtils.markWarp("[Freepixels] /pos help 帮助")
                        .formatted(Formatting.WHITE))
                .append(MarkUtils.markWarp("[Freepixels] /pos list 服务器地点列表")
                        .formatted(Formatting.WHITE))
                .append(MarkUtils.markWarp("[Freepixels] /pos search <名称> 通过名称搜索地点")
                        .formatted(Formatting.WHITE))
                .append(MarkUtils.markWarp("[Freepixels] /pos add <名称> 将当前坐标添加为一个新的地点")
                        .formatted(Formatting.WHITE))
                .append(MarkUtils.markWarp("[Freepixels] /pos delete <名称> 通过名称删除地点")
                        .formatted(Formatting.WHITE))
                .append(MarkUtils.markWarp("[Freepixels] /pos share <value> 设置共享坐标是否开启")
                        .formatted(Formatting.WHITE))
                .append(MarkUtils.markWarp("[Freepixels] /pos players 查看共享位置的玩家坐标")
                        .formatted(Formatting.WHITE))
                .append(MarkUtils.markWarp(Utils.Empty))
                .append(MarkUtils.markWarp("[Freepixels] 如有问题请在QQ群内@服主反馈!")
                        .formatted(Formatting.YELLOW, Formatting.BOLD));

        sendFeedback(source, HelpLinMessage);
        return 1;
    }
}
