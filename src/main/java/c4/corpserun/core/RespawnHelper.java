package c4.corpserun.core;

import c4.corpserun.config.ConfigEffectsHelper;
import c4.corpserun.config.values.ConfigBool;
import c4.corpserun.config.values.ConfigInt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

import java.util.ArrayList;

public final class RespawnHelper {

    public static void restoreHunger(EntityPlayer player, EntityPlayer oldPlayer) {

        if (ConfigBool.KEEP_FOOD.getValue()) {
            player.getFoodStats().setFoodLevel(Math.max(ConfigInt.MIN_FOOD.getValue(), (Math.min(ConfigInt.MAX_FOOD.getValue(), oldPlayer.getFoodStats().getFoodLevel()))));
        } else {
            player.getFoodStats().setFoodLevel(Math.max(ConfigInt.MIN_FOOD.getValue(), (Math.min(ConfigInt.MAX_FOOD.getValue(), 20))));
        }
    }

    public static void addPotionEffects(EntityPlayer player) {

        ArrayList<String[]> effectsToApply = ConfigEffectsHelper.getEffectsList();

        if (effectsToApply.isEmpty()) { return;}

        for (String[] s : effectsToApply) {
            PotionEffect potionEffect = new PotionEffect(
                    Potion.getPotionFromResourceLocation(s[0]),
                    Integer.parseInt(s[1]) * 20,
                    Integer.parseInt(s[2]) - 1);
            if (!ConfigBool.ENABLE_CURE.getValue()) {
                potionEffect.setCurativeItems(new ArrayList<>(0));
            }
            player.addPotionEffect(potionEffect);
        }
    }

}
