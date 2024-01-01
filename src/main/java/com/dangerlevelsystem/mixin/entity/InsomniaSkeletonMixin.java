package com.dangerlevelsystem.mixin.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.ai.goal.BowAttackGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.mob.AbstractSkeletonEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(net.minecraft.entity.mob.AbstractSkeletonEntity.class)
public abstract class InsomniaSkeletonMixin extends MobEntity {

    protected InsomniaSkeletonMixin(EntityType<? extends MobEntity> entityType, World world) {
        super(entityType, world);
    }

    @Shadow public abstract void equipStack(EquipmentSlot slot, ItemStack stack);
    @Shadow @Final private MeleeAttackGoal meleeAttackGoal;

    @Shadow @Final private BowAttackGoal<AbstractSkeletonEntity> bowAttackGoal;

    @Inject(method = "tickMovement",at = @At("RETURN"))
    private void changeToMelee(CallbackInfo callbackInfo){
        if(this.getTarget()!=null){
            if(this.squaredDistanceTo(this.getTarget()) < 6){
                if(this.getEquippedStack(EquipmentSlot.MAINHAND).isOf(Items.BOW))
                    this.equipStack(EquipmentSlot.MAINHAND,new ItemStack(Items.STONE_SWORD));
            }
            else{
                if(this.getEquippedStack(EquipmentSlot.MAINHAND).isOf(Items.STONE_SWORD))
                    this.equipStack(EquipmentSlot.MAINHAND, new ItemStack(Items.BOW));
            }

        }
    }
}
