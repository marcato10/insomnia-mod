package com.dangerlevelsystem.mixin.entity;

import com.dangerlevelsystem.core.Danger;
import com.dangerlevelsystem.event.PlayerInterface;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.spawner.PhantomSpawner;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.List;
import java.util.stream.Collectors;

@Mixin(PhantomSpawner.class)
public abstract class InsomniaPhantomSpawner implements PlayerInterface {
    @ModifyExpressionValue(method = "spawn",at = @At(value = "INVOKE",
            target = "Lnet/minecraft/server/world/ServerWorld;getPlayers()Ljava/util/List;"))
        private List<ServerPlayerEntity> getLevelThreePlayer(List<ServerPlayerEntity> original){
        return original.stream().filter(
                serverPlayer -> getInsomnia$danger(serverPlayer).getLevel() < 3
        ).toList();
    }

    @Override
    public Danger getInsomnia$danger(PlayerEntity player) {
        return Danger.fetchAndCreate((ServerPlayerEntity)player);
    }

    @Override
    public void setInsomnia$danger(Danger $danger) {
    }
}
