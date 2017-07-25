package c4.corpserun.core.modules;

import c4.corpserun.CorpseRun;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import org.apache.logging.log4j.Level;

import java.util.ArrayList;
import java.util.Collections;

public class EffectsModule extends Module {

    private static boolean enableCure;
    private static String[] respawnEffects;
    private static ArrayList<String> validEffectsList;
    private static ArrayList<String[]> effectsToApply;
    private static boolean cfgEnabled;

    static {
        validEffectsList = new ArrayList<>();

        for (Potion potion : GameRegistry.findRegistry(Potion.class)) {

            if (potion.isInstant() || potion.getRegistryName() == null) { continue;}

            validEffectsList.add(potion.getRegistryName().toString());
        }
    }

    @SubscribeEvent
    public void onPlayerRespawnFinish(net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerRespawnEvent e) {

        addPotionEffects(e.player);
    }

    public EffectsModule() {
        configName = "Effects";
        configDescription = "Effects on Respawn Management";
        configCategory = new ConfigCategory(configName);
        configCategory.setComment(configDescription);
        prevEnabled = false;
    }

    public void loadModuleConfig() {
        setCategoryComment();
        cfgEnabled = getBool("Enable Effects Module", false, "Set to true to enable effects module");
        enableCure = getBool("Enable Curing Items", true, "Set to true to enable curing buffs/debuffs (via milk buckets or other implementations");
        respawnEffects = getStringList("Respawn Effects", new String[]{"minecraft:mining_fatigue 30 4"}, "List of effects to apply to player on respawn\n" +"Format: [effect] [duration(secs)] [power]");
        initEffectsList();
    }

    public void initPropOrder() {
        propOrder = new ArrayList<>(Collections.singletonList("Enable Effects Module"));
    }

    public void setEnabled() {
        enabled = cfgEnabled;
    }

    private static void addPotionEffects (EntityPlayer player) {

        if (effectsToApply.isEmpty()) { return;}

        for (String[] s : effectsToApply) {

            Potion potion = Potion.getPotionFromResourceLocation(s[0]);

            if (potion == null) { continue;}

            int duration = Integer.parseInt(s[1]) * 20;
            int amp = Integer.parseInt(s[2]) - 1;

            PotionEffect potionEffect = new PotionEffect(potion, duration, amp);

            if (!enableCure) {
                potionEffect.setCurativeItems(new ArrayList<>(0));
            }

            player.addPotionEffect(potionEffect);
        }
    }

    private static void initEffectsList() {

        effectsToApply = new ArrayList<String[]>();

        for (String s : respawnEffects) {
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
                CorpseRun.logger.log(Level.ERROR, "Problem parsing respawn effects list!", e1);
            } finally {
                elements[1] = Integer.toString(Math.max(1,Math.min(i, 1600)));
                elements[2] = Integer.toString(Math.max(1,Math.min(j, 4)));
                effectAttributes[0] = elements[0];
                effectAttributes[1] = elements[1];
                effectAttributes[2] = elements[2];
            }

            effectsToApply.add(effectAttributes);
        }
    }
}
