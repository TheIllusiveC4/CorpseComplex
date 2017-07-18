package c4.corpserun.core.modules;

import c4.corpserun.config.ConfigEffectsHelper;
import c4.corpserun.config.ConfigHandler;
import c4.corpserun.config.values.ConfigBool;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

import java.util.ArrayList;

public final class EffectsModule {

    private static boolean isEnabled = ConfigHandler.isEffectsModuleEnabled();

    public static void addPotionEffects (EntityPlayer player) {

        if (!isEnabled) { return;}

        ArrayList<String[]> effectsToApply = ConfigEffectsHelper.getEffectsList();

        if (effectsToApply.isEmpty()) { return;}

        for (String[] s : effectsToApply) {

            Potion potion = Potion.getPotionFromResourceLocation(s[0]);
            int duration = Integer.parseInt(s[1]) * 20;
            int amp = Integer.parseInt(s[2]) - 1;

            if (potion == null) { continue;}

            PotionEffect potionEffect = new PotionEffect(potion, duration, amp);

            if (!ConfigBool.ENABLE_CURE.getValue()) {
                potionEffect.setCurativeItems(new ArrayList<>(0));
            }

            player.addPotionEffect(potionEffect);
        }
    }
}
