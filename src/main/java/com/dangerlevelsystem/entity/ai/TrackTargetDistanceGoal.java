package com.dangerlevelsystem.entity.ai;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.PounceAtTargetGoal;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3d;

import java.util.EnumSet;

public class TrackTargetDistanceGoal extends PounceAtTargetGoal {

    public enum ATTACK_STATE{
        DONE,STARING,ATTACKING
    }

    private final MobEntity actor;

    private final Vec3d defaultActorSpeed;

    private final float velocity;
    private ATTACK_STATE attackStage;

    private final double minRange;

    private final double maxRange;

    private int cooldown;

    private final double DEFAULT_MAX_RANGE = 16.0;
    private final double DEFAULT_MIN_RANGE = 6.0;

    private final int DEFAULT_COOLDOWN_TICKS = 100;

    public TrackTargetDistanceGoal(MobEntity mob, float velocity){
        super(mob, velocity);
        this.actor = mob;
        this.defaultActorSpeed = this.actor.getVelocity();
        this.velocity = velocity;
        this.minRange = DEFAULT_MIN_RANGE;
        this.maxRange = DEFAULT_MAX_RANGE;
        this.attackStage = ATTACK_STATE.STARING;
        this.cooldown = DEFAULT_COOLDOWN_TICKS;
    }

    //TODO: tick()

    public TrackTargetDistanceGoal(MobEntity mob, float velocity, double minRange, double maxRange, int cooldown){
        super(mob, velocity);
        this.actor = mob;
        this.defaultActorSpeed = this.actor.getVelocity();
        this.velocity = velocity;
        this.minRange = minRange;
        this.maxRange = maxRange;
        this.attackStage = ATTACK_STATE.STARING;
        this.cooldown = cooldown;

    }

    @Override
    public void start() {
        this.actor.setAttacking(true);
        this.setAttackStage(ATTACK_STATE.STARING);
    }

    @Override
    public void stop(){
        this.actor.setVelocity(this.defaultActorSpeed);
        this.setControls(EnumSet.of(Goal.Control.MOVE));
        super.stop();
    }

    @Override
    public boolean shouldRunEveryTick(){return true;}

    @Override
    public boolean shouldContinue() {
        if(this.getAttackStage().equals(ATTACK_STATE.DONE)){
            return false;
        }
        return super.shouldContinue();
    }

    @Override
    public boolean canStart(){
        if(this.getActor().getTarget() == null ){
            return false;
        }
        if (this.getActor().hasControllingPassenger()) {
            return false;
        }
        if(!this.getActor().isAttacking()){
            return false;
        }
        if (!this.getActor().isOnGround()) {
            return false;
        }
        return this.isTargetAtRange(this.getActor().getTarget());
    }

    public boolean isTargetAtRange(LivingEntity target){
        if(target == null){
            return false;
        }
        return this.actor.squaredDistanceTo(target) <= this.getMaxRange() && this.actor.squaredDistanceTo(target)>= this.getMinRange();
    }

    private ATTACK_STATE stateTransition(ATTACK_STATE attackState){
        switch (attackState){
            case STARING:
                this.setControls(EnumSet.of(Control.LOOK));
                this.getActor().swingHand(Hand.MAIN_HAND);
                this.getActor().getLookControl().lookAt(this.getActor().getTarget(), 30.0f, 30.0f);
                this.getActor().setStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH,-1),this.getActor());
                if(this.isTargetAtRange(this.getActor().getTarget())){
                    return ATTACK_STATE.DONE;
                }
                if(this.cooldown > 0){
                    this.cooldown = this.cooldown - 20;
                    return ATTACK_STATE.STARING;
                }
                return ATTACK_STATE.ATTACKING;
            case ATTACKING:
                Vec3d vec3d = this.actor.getVelocity();
                Vec3d vec3d2 = new Vec3d(this.getActor().getTarget().getX() - this.actor.getX(), 0.0, this.getActor().getTarget().getZ() - this.actor.getZ());
                if (vec3d2.lengthSquared() > 1.0E-7) {
                    vec3d2 = vec3d2.normalize().multiply(0.8).add(vec3d.multiply(0.2));
                }
                this.actor.setVelocity(vec3d2.x, this.velocity, vec3d2.z);
                this.setControls(EnumSet.of(Goal.Control.JUMP, Goal.Control.MOVE));
                this.actor.playSound(SoundEvents.ENTITY_SPIDER_STEP, 0.30f, 2.0f);
                return ATTACK_STATE.DONE;

                default:
                    if(this.getActor().isInAttackRange(this.getActor().getTarget()) && this.getActor().collidesWith(this.getActor().getTarget()) && this.actor.isAttacking()){

                        this.actor.tryAttack(this.getActor().getTarget());
                    }
                    stop();
                    return ATTACK_STATE.DONE;
        }
    }



    public MobEntity getActor() {
        return this.actor;
    }

    public ATTACK_STATE getAttackStage() {
        return attackStage;
    }

    public void setAttackStage(ATTACK_STATE attackStage) {
        this.attackStage = attackStage;
    }

    public double getMinRange() {
        return minRange;
    }

    public double getMaxRange() {
        return maxRange;
    }

}
