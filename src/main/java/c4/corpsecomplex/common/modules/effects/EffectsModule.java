/*
 * Copyright (c) 2017. <C4>
 *
 * This Java class is distributed as a part of Corpse Complex.
 * Corpse Complex is open source and licensed under the GNU General Public
 * License v3.
 * A copy of the license can be found here: https://www.gnu.org/licenses/gpl
 * .text
 */

package c4.corpsecomplex.common.modules.effects;

import c4.corpsecomplex.CorpseComplex;
import c4.corpsecomplex.common.Module;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import org.apache.logging.log4j.Level;

import java.util.ArrayList;
import java.util.Collections;

public class EffectsModule extends Module {

  private static ArrayList<String> validEffectsList;
  private static ArrayList<String[]> effects;
  private static ArrayList<String[]> customCureEffects;
  private static String[] cfgCureList;
  private static boolean cfgEnabled;

  static {
    validEffectsList = new ArrayList<>();

    for (Potion potion : GameRegistry.findRegistry(Potion.class)) {

      if (potion.isInstant() || potion.getRegistryName() == null) {
        continue;
      }

      validEffectsList.add(potion.getRegistryName().toString());
    }
  }

  {
    submoduleClasses = new ArrayList<>();

    addSubmodule(MoriModule.class);
  }

  @SubscribeEvent
  public void onItemUseFinish(LivingEntityUseItemEvent.Finish evt) {

    EntityLivingBase entityLivingBase = evt.getEntityLiving();

    if (!entityLivingBase.world.isRemote && cfgCureList.length > 0) {
      entityLivingBase.curePotionEffects(evt.getItem());
    }
  }

  @SubscribeEvent
  public void onPlayerRespawnFinish(
          net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerRespawnEvent e) {

    if (!e.player.world.isRemote && !e.isEndConquered()) {
      EntityPlayer player = e.player;
      addPotionEffects(player, effects, false);
      addPotionEffects(player, customCureEffects, true);
    }
  }

  public EffectsModule() {

    super("Effects",
            "Add potions effects that will be applied to players on respawn");
  }

  public void loadModuleConfig() {
    setCategoryComment();
    cfgEnabled = getBool("Enable Effects Module", false,
            "Set to true to enable effects module", true);
    cfgCureList = getStringList("Curative Items",
            new String[] {"minecraft:milk_bucket"},
            "List of items that will be used by 'Curable Respawn Effects'",
            false);

    String[] cfgEffects = getStringList("Uncurable Respawn Effects",
            new String[] {"minecraft:mining_fatigue 30 4"},
            "List of effects to apply to player on respawn\n" +
                    "Format: [effect] [duration(secs)] [power]", false);
    String[] cfgCustomCureEffects = getStringList("Curable Respawn Effects",
            new String[] {},
            "List of effects to apply to players on respawn that can be cured" +
                    " by the curing items list\n" +
                    "Format: [effect] [duration(secs)] [power]", false);
    effects = new ArrayList<>(initEffectsList(cfgEffects));
    customCureEffects = new ArrayList<>(initEffectsList(cfgCustomCureEffects));
  }

  public void initPropOrder() {
    propOrder = new ArrayList<>(
            Collections.singletonList("Enable Effects Module"));
  }

  public void setEnabled() {
    enabled = cfgEnabled;
  }

  private static void addPotionEffects(EntityPlayer player,
          ArrayList<String[]> effectsToApply, boolean useCureList) {

    if (effectsToApply.isEmpty()) {
      return;
    }

    for (String[] s : effectsToApply) {

      Potion potion = Potion.getPotionFromResourceLocation(s[0]);

      if (potion == null) {
        continue;
      }

      int duration = Integer.parseInt(s[1]) * 20;
      int amp = Integer.parseInt(s[2]) - 1;

      PotionEffect potionEffect = new PotionEffect(potion, duration, amp);

      if (useCureList) {

        ArrayList<ItemStack> cureList = new ArrayList<>();

        for (String s1 : cfgCureList) {
          Item item = Item.getByNameOrId(s1);

          if (item != null) {
            cureList.add(new ItemStack(item));
          }
        }

        potionEffect.setCurativeItems(cureList);

      } else {
        potionEffect.setCurativeItems(new ArrayList<>(0));
      }

      player.addPotionEffect(potionEffect);
    }
  }

  private static ArrayList<String[]> initEffectsList(String[] effectsList) {

    ArrayList<String[]> effectsToApply = new ArrayList<>();

    for (String s : effectsList) {
      String[] elements = s.split("\\s+");
      String[] effectAttributes = new String[3];

      if (!validEffectsList.contains(elements[0])) {
        continue;
      }

      int i = 0;
      int j = 0;

      try {
        i = Integer.parseInt(elements[1]);
        j = Integer.parseInt(elements[2]);
      } catch (Exception e1) {
        CorpseComplex.logger.log(Level.ERROR,
                "Problem parsing respawn effects list!", e1);
      } finally {
        elements[1] = Integer.toString(Math.max(1, Math.min(i, 1600)));
        elements[2] = Integer.toString(Math.max(1, Math.min(j, 10)));
        effectAttributes[0] = elements[0];
        effectAttributes[1] = elements[1];
        effectAttributes[2] = elements[2];
      }

      effectsToApply.add(effectAttributes);
    }

    return effectsToApply;
  }
}
