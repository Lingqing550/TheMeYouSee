package com.lingqing.client.commands;


import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;

import java.util.HashMap;
import java.util.Map;

public class SetRotational {
    public static int speed = 0;//全局旋转
    public static boolean rotationalSwitch = true;
    public static float attenuation = 15;//每秒速度衰减 度
    public static float accelerated = 36;//攻击加速 度
    public static int maxSpeed = 2160;
    public static Map<Integer, Float> playerSpeedMap = new HashMap<>();
    public static Map<Integer,Float> playerAllMap = new HashMap<>();
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
                )))
                .then(ClientCommandManager.literal("attenuation")
                        .then(ClientCommandManager.argument("attenuation",IntegerArgumentType.integer(0,maxSpeed)).executes(context-> {
                            attenuation = IntegerArgumentType.getInteger(context,"attenuation");
                            return 1;
                        }
                )))
                .then(ClientCommandManager.literal("maxSpeed")
                        .then(ClientCommandManager.argument("maxSpeed",IntegerArgumentType.integer(0,3600)).executes(context-> {
                             maxSpeed = IntegerArgumentType.getInteger(context,"maxSpeed");
                             return 1;
                        }
                )))
                .then(ClientCommandManager.literal("accelerated")
                        .then(ClientCommandManager.argument("accelerated",IntegerArgumentType.integer(0,3600)).executes(context-> {
                            accelerated = IntegerArgumentType.getInteger(context,"accelerated");
                              return 1;
                        }
                )));
    };

}
