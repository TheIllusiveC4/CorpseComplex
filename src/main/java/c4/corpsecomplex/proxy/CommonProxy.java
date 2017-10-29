/*
 * Copyright (c) 2017. C4, MIT License
 */

package c4.corpsecomplex.proxy;

import c4.corpsecomplex.common.modules.effects.MoriPotion;
import c4.corpsecomplex.common.modules.inventory.capability.DeathInvCapHandler;
import c4.corpsecomplex.common.modules.inventory.capability.DeathInventory;
import c4.corpsecomplex.common.modules.inventory.capability.IDeathInventory;
import c4.corpsecomplex.common.helpers.ModuleHelper;
import c4.corpsecomplex.common.modules.effects.MoriModule;
import c4.corpsecomplex.common.modules.inventory.enchantment.EnchantmentModule;
import c4.corpsecomplex.common.modules.inventory.enchantment.EnchantmentSoulbound;
import c4.corpsecomplex.common.modules.spawning.ItemScroll;
import c4.corpsecomplex.common.modules.spawning.SpawningModule;
import c4.corpsecomplex.common.modules.spawning.capability.DeathLocCapHandler;
import c4.corpsecomplex.common.modules.spawning.capability.DeathLocation;
import c4.corpsecomplex.common.modules.spawning.capability.IDeathLocation;
import c4.corpsecomplex.network.NetworkHandler;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.potion.Potion;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class CommonProxy {

    public void preInit(FMLPreInitializationEvent e) {

        ModuleHelper.preInit(e);
        NetworkHandler.init();
    }

    public void init(FMLInitializationEvent e) {

        MinecraftForge.EVENT_BUS.register(new DeathInvCapHandler());
        MinecraftForge.EVENT_BUS.register(new DeathLocCapHandler());
        CapabilityManager.INSTANCE.register(IDeathInventory.class, new DeathInventory.Storage(), DeathInventory.class);
        CapabilityManager.INSTANCE.register(IDeathLocation.class, new DeathLocation.Storage(), DeathLocation.class);
    }

    @SubscribeEvent
    public static void registerPotions(RegistryEvent.Register<Potion> e) {
        if (MoriModule.registerPotion) {
            e.getRegistry().register(new MoriPotion());
        }
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> e) {
        if (SpawningModule.registerScroll) {
            e.getRegistry().register(new ItemScroll());
        }
    }

    @SubscribeEvent
    public static void registerEnchantments(RegistryEvent.Register<Enchantment> e) {
        if (EnchantmentModule.registerEnchant) {
            e.getRegistry().register(new EnchantmentSoulbound(Enchantment.Rarity.valueOf(EnchantmentModule.rarity)));
        }
    }
}
