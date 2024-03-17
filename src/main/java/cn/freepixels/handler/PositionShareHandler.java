package cn.freepixels.handler;

import cn.freepixels.models.Position;
import cn.freepixels.repositories.PositionShareRepository;
import cn.freepixels.utils.MarkUtils;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.MutableText;
import net.minecraft.util.Formatting;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

public class PositionShareHandler extends CommandHandler {

    public static int playersPosition(@NotNull CommandContext<ServerCommandSource> context) {
        ServerCommandSource source = context.getSource();
        MinecraftServer server = source.getServer();
        PlayerManager playerManager = server.getPlayerManager();
        List<ServerPlayerEntity> players = playerManager.getPlayerList();

        List<ServerPlayerEntity> sharePlayers = players.stream().filter(player -> PositionShareRepository.playerIsShare(player.getUuidAsString())).toList();

        MutableText text = MarkUtils.markWarp("[Freepixels] 玩家位置").formatted(Formatting.BLUE, Formatting.BOLD);

        int index = 1;
        for (ServerPlayerEntity player : sharePlayers) {
            String name = player.getName().getString();
            String world = player.getWorld().getRegistryKey().getValue().toString();
            Position position = new Position(player.getPos());

            MutableText line = MarkUtils.markSpace("[#" + index + "]")
                    .append(MarkUtils.markSpace(world).formatted(Formatting.GREEN))
                    .append(MarkUtils.markSpace(name).formatted(Formatting.GOLD))
                    .append(MarkUtils.markPosition(position));

            text.append(MarkUtils.markWarp(line));

            index++;
        }

        source.sendFeedback(() -> text, false);
        return 1;
    }

    public static int share(@NotNull CommandContext<ServerCommandSource> context) {
        ServerCommandSource source = context.getSource();
        boolean value = context.getArgument("value", boolean.class);

        String uuid = Objects.requireNonNull(source.getPlayer()).getUuidAsString();

        boolean result;
        if (value) {
            result = PositionShareRepository.open(uuid);
        } else {
            result = PositionShareRepository.close(uuid);
        }

        if (result) {
            sendSuccessFeedback(source, "设置成功!");
        } else {
            sendErrorFeedback(source, "设置失败!");
        }

        return 1;
    }
}
