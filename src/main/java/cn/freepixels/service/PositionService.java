package cn.freepixels.service;

import cn.freepixels.handler.PositionShareHandler;
import cn.freepixels.repositories.LocationRepository;
import cn.freepixels.handler.PositionCommandHandler;
import cn.freepixels.repositories.PositionShareRepository;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import org.jetbrains.annotations.NotNull;


public class PositionService {
    public static void initialize() {
        System.out.println("PositionService initialize");
        System.out.println("Freepixels plugin - build: 2024-3-13");
        System.out.println("by: Tomoko Aoyama (QQ: 1635582152) && YuanYuJunChangAn(QQ: 906506482)");
        System.out.println("[Freepixels][SiteModule] Self-testing in progress...");

        loadData();

        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            registerCommands(dispatcher);
        });

    }

    private static void loadData() {
        try {
            LocationRepository.loadLocations();
            PositionShareRepository.loadSharePlayers();
            return;
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("[Freepixels][Position] Failed to parse the site list JSON!");
        }

        // 自检失败,数据Json有问题,抛出一个未捕获的异常,让游戏退出.
        throw new RuntimeException("[Freepixels][Position] Json data parsing failed!");
    }

    private static void registerCommands(@NotNull CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(
                // 注册0级指令允许所有玩家使用
                CommandManager.literal("pos").requires(source -> source.hasPermissionLevel(0))
                        // pos help 获取帮助
                        .then(CommandManager.literal("help").executes(PositionCommandHandler::help))
                        // pos list 获取地点列表
                        .then(CommandManager.literal("list").executes(PositionCommandHandler::list))
                        // pos search <name> 通过名称获取指定的地点
                        .then(CommandManager.literal("search")
                                .then(CommandManager.argument("name", StringArgumentType.greedyString()).executes(PositionCommandHandler::search)))
                        // pos add <name> 添加一个新的地点
                        .then(CommandManager.literal("add")
                                .then(CommandManager.argument("name", StringArgumentType.greedyString()).executes(PositionCommandHandler::add)))
                        // pos delete <name> 通过名称删除一个地点(玩家只能删除自己的,OP可以删除任意的)
                        .then(CommandManager.literal("delete")
                                .then(CommandManager.argument("name", StringArgumentType.greedyString()).executes(PositionCommandHandler::delete)))
                        // /pos players 玩家位置
                        .then(CommandManager.literal("players").executes(PositionShareHandler::playersPosition))
                        // /pos share <value> 设置位置共享
                        .then(CommandManager.literal("share")
                                .then(CommandManager.argument("value", BoolArgumentType.bool()).executes(PositionShareHandler::share)))

        );
    }
}
