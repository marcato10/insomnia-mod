package com.dangerlevelsystem.mixin.entity;

import com.dangerlevelsystem.entity.ai.TrackTargetDistanceGoal;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.TrackedPosition;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.SpiderEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(targets = "net.minecraft.entity.mob.SpiderEntity")
public class InsomniaSpiderMixin extends MobEntity {
    protected InsomniaSpiderMixin(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }
    /*
    @Inject(method = "tick",at = @At("HEAD"))
    private void checkForAttack(CallbackInfo callbackInfo) {
        if(this.isAttacking() && this.getTarget() != null){
            if(this.squaredDistanceTo(this.getTarget()) > 8 && this.getVisibilityCache().canSee(this.getTarget())){
                Vec3d directional = this.getPos().subtract(this.getTarget().getPos());
                this.move(MovementType.SELF,directional);
                System.out.println(this.getBlockPos());
            }
        }
    }
*/

    @Inject(method = "initGoals",at = @At("TAIL"))
    private void initializeHybridCombat(CallbackInfo callbackInfo){
        this.goalSelector.add(2,new TrackTargetDistanceGoal((SpiderEntity)(Object)this,1.0f,4.0f,12.0f));
    }

    @Inject(method = "tick",at = @At("HEAD"))
    private void changePosture(CallbackInfo callbackInfo){

    }

}
