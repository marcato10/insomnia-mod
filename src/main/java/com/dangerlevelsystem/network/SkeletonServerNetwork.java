package com.dangerlevelsystem.network;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.mob.AbstractSkeletonEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public class SkeletonServerNetwork {

    public static void receive(ServerPlayerEntity player, AbstractSkeletonEntity skeleton, Identifier channelName, PacketByteBuf buf){
        
    }
}
