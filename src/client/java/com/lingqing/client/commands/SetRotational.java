package com.lingqing.client.commands;


import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;

public class SetRotational {
    public static int speed = 10;
    public static boolean rotationalSwitch = true;
    public static LiteralArgumentBuilder<FabricClientCommandSource> getCommand() {
        return ClientCommandManager.literal("setRotational")
                .then(ClientCommandManager.literal("speed")
                        .then(ClientCommandManager.argument("speed",IntegerArgumentType.integer(0,360)).executes(context -> {
                                    speed = IntegerArgumentType.getInteger(context,"speed");
                                    return 1;
                                }
                        )))
                .then(ClientCommandManager.literal("switch")
                        .then(ClientCommandManager.argument("switch", BoolArgumentType.bool()).executes(context -> {
                                    rotationalSwitch = BoolArgumentType.getBool(context,"switch");
                                    return 1;
                                }
                        )));
    };

}