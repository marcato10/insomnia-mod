package com.dangerlevelsystem.core;

import com.dangerlevelsystem.util.InsomniaConstants;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.PersistentState;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class Insomnia extends PersistentState {

    private Map<UUID,Danger> insomniaPlayersMap = new HashMap<UUID,Danger>();

    public Map<UUID, Danger> getInsomniaPlayersMap() {
        return insomniaPlayersMap;
    }

    public void setInsomniaPlayersMap(Map<UUID, Danger> insomniaPlayersMap) {
        this.insomniaPlayersMap = insomniaPlayersMap;
    }

    public Optional<Danger> getDangerByServerPlayer(ServerPlayerEntity player){
        return Optional.ofNullable(insomniaPlayersMap.get(player.getUuid()));
    }

    public void registerServerPlayer(ServerPlayerEntity player){
        insomniaPlayersMap.putIfAbsent(player.getUuid(), Danger.fetchAndCreate(player));
    }

    public void insomniaUpdatePlayers(ServerPlayerEntity player){
        insomniaPlayersMap.compute(player.getUuid(), (k,l)-> Danger.fetchAndCreate(player));
    }

    public void setForceDangerLevel(ServerPlayerEntity player,int dangerLevel){
        insomniaPlayersMap.compute(player.getUuid(), (k,l)-> new Danger(dangerLevel,Danger.fetchTimeNotSlept(player.getStatHandler())));
    }


    @Override
    public NbtCompound writeNbt(NbtCompound nbt) {
        insomniaPlayersMap.forEach(((uuid, danger) -> {
            nbt.put(uuid.toString(),danger.toNbt());
            }
        ));
        return nbt;
    }



}
