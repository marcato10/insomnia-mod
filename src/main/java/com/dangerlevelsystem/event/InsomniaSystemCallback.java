package com.dangerlevelsystem.event;

import com.dangerlevelsystem.core.Danger;
import net.fabricmc.fabric.api.entity.event.v1.EntitySleepEvents;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;

import java.util.List;

public class InsomniaSystemCallback implements ServerTickEvents.StartWorldTick, ServerLivingEntityEvents.AfterDeath {
    @Override
    public void onStartTick(ServerWorld world) {
        if(world.getTimeOfDay()%12000 == 0){
            for(ServerPlayerEntity player : world.getPlayers()){
                ((PlayerInterface)player).setInsomnia$danger(Danger.fetchAndCreate(player));
            }
        }
    }
    @Override
    public void afterDeath(LivingEntity entity, DamageSource damageSource) {
        if(entity.isPlayer() && entity instanceof ServerPlayerEntity){
            ((PlayerInterface)entity).setInsomnia$danger(Danger.resetedDanger());
        }
    }
}
