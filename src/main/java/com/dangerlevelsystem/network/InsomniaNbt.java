package com.dangerlevelsystem.network;

import net.minecraft.nbt.NbtCompound;

public interface InsomniaNbt<T> {
    T createFromNbt(NbtCompound nbtCompound);

}
