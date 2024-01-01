package com.dangerlevelsystem.entity.ai;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.PounceAtTargetGoal;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.jetbrains.annotations.Nullable;

public class TrackTargetDistanceGoal extends PounceAtTargetGoal {

    private final float closeDistanceTargetRange;
    private final float farDistanceTargetRange;

    private final MobEntity actor;
    @Nullable
    private LivingEntity target;

    private COMBAT_TYPE combatType;

    public TrackTargetDistanceGoal(MobEntity actor, float velocity,float closeDistanceTargetRange,float farDistanceTargetRange) {
        super(actor, velocity);
        this.actor = actor;
        this.target = actor.getTarget();
        this.closeDistanceTargetRange = closeDistanceTargetRange;
        this.farDistanceTargetRange = farDistanceTargetRange;
        this.combatType = this.getCombatTypeByDistance();
    }


    @Override
    public boolean canStart() {
        super.canStart();
        this.target = actor.getTarget();
        if((this.target != null) && this.actor.isAttacking()){
            if(target.isPlayer()){
                return isTargetAtFarRange(this.actor.getTarget());
            }
        }
        return false;
    }

    @Override
    public boolean shouldContinue() {
        if(this.target!=null && super.shouldContinue()){
            return this.actor.squaredDistanceTo(this.target) > (double) farDistanceTargetRange * farDistanceTargetRange;
        }
        return false;
    }


    private boolean isTargetAtFarRange(LivingEntity livingEntity){
        if(livingEntity == null)
            return false;
        return this.actor.squaredDistanceTo(livingEntity) > farDistanceTargetRange;
    }

    private boolean isTargetClose(LivingEntity livingEntity){
        if(livingEntity == null)
            return false;
        return this.actor.squaredDistanceTo(livingEntity) <= closeDistanceTargetRange;
    }

    public COMBAT_TYPE getCombatTypeByDistance(){
        return this.isTargetClose(this.actor.getTarget()) ? COMBAT_TYPE.CLOSE : COMBAT_TYPE.RANGED;
    }

    public enum COMBAT_TYPE{
        CLOSE,RANGED
    }

}
