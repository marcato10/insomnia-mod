package com.dangerlevelsystem.core;
import com.dangerlevelsystem.network.InsomniaNbt;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.StatHandler;
import net.minecraft.stat.Stats;
import net.minecraft.util.math.MathHelper;

import java.util.UUID;

public class Danger implements InsomniaNbt {
    private int level;

    private int daysNotSlept;

    public Danger(int level,int daysNotSlept){
        this.level = level;
        this.daysNotSlept = daysNotSlept;
    }

    public static final int TICKS_LEVEL_ONE = 0;

    //3 days
    public static final int TICKS_LEVEL_TWO = 72000;
    //7 days (one week)
    public static final int TICKS_LEVEL_THREE = 168000;
    //14 days
    public static final int TICKS_LEVEL_FOUR = 336000;

    public static int computeDangerLevelByDays(int daysNotSlept){
        if(daysNotSlept < TICKS_LEVEL_TWO){
            return 1;
        }
        else if(daysNotSlept < TICKS_LEVEL_THREE){
            return 2;
        }
        else if(daysNotSlept < TICKS_LEVEL_FOUR){
            return 3;
        }
        else{
            return 4;
        }
    }

    public static int fetchTimeNotSlept(StatHandler statHandler){
        return MathHelper.clamp(statHandler.getStat(Stats.CUSTOM.getOrCreateStat(Stats.TIME_SINCE_REST)), 1, Integer.MAX_VALUE);
    }

    public static Danger fetchAndCreate(ServerPlayerEntity player){
        int time = fetchTimeNotSlept(player.getStatHandler());
        return new Danger(computeDangerLevelByDays(time),time);
    }

    public static Danger resetedDanger(){
        return new Danger(1,0);
    }

    private int getDaysNotSlept() {
        return daysNotSlept;
    }

    private void setDaysNotSlept(int daysNotSlept) {
        this.daysNotSlept = daysNotSlept;
    }

    public static NbtCompound fetchAndCreateNbt(ServerPlayerEntity serverPlayer){
        Danger fetchedDanger = fetchAndCreate(serverPlayer);
        NbtCompound dangerNbt = new NbtCompound();
        dangerNbt.putInt("level",fetchedDanger.getLevel());
        dangerNbt.putInt("daysNotSlept",fetchedDanger.getDaysNotSlept());
        return dangerNbt;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public NbtCompound toNbt(){
        NbtCompound dangerNbt = new NbtCompound();
        dangerNbt.putInt("level",this.getLevel());
        dangerNbt.putInt("daysNotSlept",this.getDaysNotSlept());
        return dangerNbt;
    }

    @Override
    public Danger createFromNbt(NbtCompound nbtCompound) {
        Danger danger = new Danger(1,0);
        danger.setLevel(nbtCompound.getInt("level"));
        danger.setDaysNotSlept(nbtCompound.getInt("daysNotSlept"));
        return danger;
    }
}



