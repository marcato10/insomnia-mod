package com.dangerlevelsystem.mixin;

import com.dangerlevelsystem.core.Danger;
import com.dangerlevelsystem.event.PlayerInterface;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class InsomniaPlayerEntityMixin implements PlayerInterface {

    @Unique
    private Danger insomnia$danger = new Danger(Danger.TICKS_LEVEL_ONE,Danger.computeDangerLevelByDays(Danger.TICKS_LEVEL_ONE));

    @Inject(method = "onDeath",at = @At("RETURN"))
    protected void insomnia$resetDangerLevel(DamageSource damageSource, CallbackInfo ci){
        this.insomnia$danger.setLevel(Danger.TICKS_LEVEL_ONE);
    }

    @Override
    public Danger getInsomnia$danger(PlayerEntity player) {
        return this.insomnia$danger;
    }

    @Override
    public void setInsomnia$danger(Danger $danger) {
        this.insomnia$danger = $danger;
    }
}
