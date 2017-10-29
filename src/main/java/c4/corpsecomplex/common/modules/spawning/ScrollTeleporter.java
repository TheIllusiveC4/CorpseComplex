/*
 * Copyright (c) 2017. C4, MIT License
 */

package c4.corpsecomplex.common.modules.spawning;

import c4.corpsecomplex.network.NetworkHandler;
import c4.corpsecomplex.network.TeleportEffectMessage;
import c4.corpsecomplex.network.TeleportSoundMessage;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.play.server.SPacketEntityEffect;
import net.minecraft.network.play.server.SPacketRespawn;
import net.minecraft.potion.PotionEffect;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.PlayerList;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;

import java.util.Random;

public class ScrollTeleporter {

    public static void sendTeleportEffect(World world, BlockPos pos) {
        NetworkHandler.INSTANCE.sendToAllAround(new TeleportEffectMessage(pos), new NetworkRegistry.TargetPoint(world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), 64));
    }

    /**
     * Copyright (c) 2016 BlayTheNinth
     * MIT License
     * Waystone's WaystoneManager (https://github.com/blay09/Waystones/blob/1.12/src/main/java/net/blay09/mods/waystones/WaystoneManager.java)
     * =======================================================================================================================================
     */
    public static void teleportToPosition(EntityPlayer player, BlockPos pos, EnumFacing facing, int dimensionId) {
        sendTeleportEffect(player.world, new BlockPos(player));
        if(dimensionId != player.getEntityWorld().provider.getDimension()) {
            MinecraftServer server = player.world.getMinecraftServer();
            if(server != null) {
                transferPlayerToDimension((EntityPlayerMP) player, dimensionId, server.getPlayerList());
            }
        } else {
            if (player.isBeingRidden()) {
                player.removePassengers();
            }
            if (player.isRiding()) {
                player.dismountRidingEntity();
            }
        }
        player.rotationYaw = getRotationYaw(facing);
        pos = new BlockPos(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5);
        player.setPositionAndUpdate(pos.getX(), pos.getY(), pos.getZ());
        sendTeleportEffect(player.world, pos);
        if (player instanceof EntityPlayerMP) {
            NetworkHandler.INSTANCE.sendTo(new TeleportSoundMessage(), (EntityPlayerMP) player);
        }
    }

    private static float getRotationYaw(EnumFacing facing) {
        switch(facing) {
            case NORTH:
                return 180f;
            case SOUTH:
                return 0f;
            case WEST:
                return 90f;
            case EAST:
                return -90f;
        }
        return 0f;
    }
    /*
     * =======================================================================================================================================
     */

    /**
     * Copyright 2012-2017 Cult of the Full Hub / Team CoFH / CoFH
     * "Don't Be a Jerk" License
     * CofHCore's EntityHelper (https://github.com/CoFH/CoFHCore/blob/1.12/src/main/java/cofh/core/util/helpers/EntityHelper.java)
     * =======================================================================================================================================
     */
    private static void transferPlayerToDimension(EntityPlayerMP player, int dimension, PlayerList manager) {

        int oldDim = player.dimension;
        WorldServer worldserver = manager.getServerInstance().getWorld(player.dimension);
        player.dimension = dimension;
        WorldServer worldserver1 = manager.getServerInstance().getWorld(player.dimension);
        player.connection.sendPacket(new SPacketRespawn(player.dimension, player.world.getDifficulty(), player.world.getWorldInfo().getTerrainType(), player.interactionManager.getGameType()));
        worldserver.removeEntityDangerously(player);
        if (player.isBeingRidden()) {
            player.removePassengers();
        }
        if (player.isRiding()) {
            player.dismountRidingEntity();
        }
        player.isDead = false;
        transferEntityToWorld(player, worldserver, worldserver1);
        manager.preparePlayer(player, worldserver);
        player.connection.setPlayerLocation(player.posX, player.posY, player.posZ, player.rotationYaw, player.rotationPitch);
        player.interactionManager.setWorld(worldserver1);
        manager.updateTimeAndWeatherForPlayer(player, worldserver1);
        manager.syncPlayerInventory(player);

        for (PotionEffect potioneffect : player.getActivePotionEffects()) {
            player.connection.sendPacket(new SPacketEntityEffect(player.getEntityId(), potioneffect));
        }
        FMLCommonHandler.instance().firePlayerChangedDimensionEvent(player, oldDim, dimension);
    }

    private static void transferEntityToWorld(Entity entity, WorldServer oldWorld, WorldServer newWorld) {
        WorldProvider oldWorldProvider = oldWorld.provider;
        WorldProvider newWorldProvider = newWorld.provider;
        double moveFactor = oldWorldProvider.getMovementFactor() / newWorldProvider.getMovementFactor();
        double x = entity.posX * moveFactor;
        double z = entity.posZ * moveFactor;

        oldWorld.profiler.startSection("placing");
        x = MathHelper.clamp(x, -29999872, 29999872);
        z = MathHelper.clamp(z, -29999872, 29999872);
        if (entity.isEntityAlive()) {
            entity.setLocationAndAngles(x, entity.posY, z, entity.rotationYaw, entity.rotationPitch);
            newWorld.spawnEntity(entity);
            newWorld.updateEntityWithOptionalForce(entity, false);
        }
        oldWorld.profiler.endSection();

        entity.setWorld(newWorld);
    }
    /*
     * =======================================================================================================================================
     */
}
