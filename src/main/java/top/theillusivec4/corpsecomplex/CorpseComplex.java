/*
 * Copyright (c) 2017. <C4>
 *
 * This Java class is distributed as a part of Corpse Complex.
 * Corpse Complex is open source and licensed under the GNU General Public
 * License v3.
 * A copy of the license can be found here: https://www.gnu.org/licenses/gpl
 * .text
 */

package top.theillusivec4.corpsecomplex;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig.ModConfigEvent;
import net.minecraftforge.fml.config.ModConfig.Type;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import top.theillusivec4.corpsecomplex.common.capability.DeathStorageCapability;
import top.theillusivec4.corpsecomplex.common.config.CorpseComplexConfig;
import top.theillusivec4.corpsecomplex.common.modules.EffectModule;
import top.theillusivec4.corpsecomplex.common.modules.ExperienceModule;
import top.theillusivec4.corpsecomplex.common.modules.HungerModule;
import top.theillusivec4.corpsecomplex.common.modules.MiscModule;
import top.theillusivec4.corpsecomplex.common.modules.inventory.InventoryModule;
import top.theillusivec4.corpsecomplex.common.modules.mementomori.MementoMoriModule;

@Mod(CorpseComplex.MODID)
public class CorpseComplex {

  public static final String MODID = "corpsecomplex";
  public static final Logger LOGGER = LogManager.getLogger();

  public CorpseComplex() {
    ModLoadingContext.get().registerConfig(Type.SERVER, CorpseComplexConfig.serverSpec);
    IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
    eventBus.addListener(this::setup);
    eventBus.addListener(this::config);
    MinecraftForge.EVENT_BUS.register(new InventoryModule());
    MinecraftForge.EVENT_BUS.register(new ExperienceModule());
    MinecraftForge.EVENT_BUS.register(new HungerModule());
    MinecraftForge.EVENT_BUS.register(new EffectModule());
    MinecraftForge.EVENT_BUS.register(new MementoMoriModule());
    MinecraftForge.EVENT_BUS.register(new MiscModule());
  }

  private void setup(final FMLCommonSetupEvent evt) {
    DeathStorageCapability.register();
    InventoryModule.STORAGE_ADDONS.forEach((modid, clazz) -> {
      if (ModList.get().isLoaded(modid)) {
        try {
          InventoryModule.STORAGE.add(clazz.newInstance());
        } catch (InstantiationException | IllegalAccessException e) {
          LOGGER.error("Error trying to instantiate storage module for mod " + modid);
        }
      }
    });
  }

  private void config(final ModConfigEvent evt) {

    if (evt.getConfig().getSpec() == CorpseComplexConfig.serverSpec) {
      CorpseComplexConfig.bakeConfigs();
    }
  }
}
