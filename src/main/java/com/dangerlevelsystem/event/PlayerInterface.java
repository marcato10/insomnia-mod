package com.dangerlevelsystem.event;

import com.dangerlevelsystem.core.Danger;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;

public interface PlayerInterface{

     Danger getInsomnia$danger(PlayerEntity player);

     void setInsomnia$danger(Danger $danger);
}
