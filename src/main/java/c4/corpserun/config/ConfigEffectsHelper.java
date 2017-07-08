package c4.corpserun.config;

import c4.corpserun.CorpseRun;
import c4.corpserun.config.values.ConfigStringList;
import net.minecraft.potion.Potion;
import net.minecraftforge.fml.common.registry.GameRegistry;
import org.apache.logging.log4j.Level;

import java.util.ArrayList;

public class ConfigEffectsHelper {

    private static ArrayList<String> validEffectsList = new ArrayList<String>();

    public static void initValidEffectsList() {

        for (Potion potion : GameRegistry.findRegistry(Potion.class)) {
            if (!potion.isInstant()) {
                validEffectsList.add(potion.getRegistryName().toString());
            }
        }
    }

    public static ArrayList<String> getValidEffectsList() {
        return validEffectsList;
    }

    public static ArrayList<String[]> getEffectsList() {

        ArrayList<String[]> effectsList = new ArrayList<String[]>();
        String[] respawnEffects = ConfigStringList.RESPAWN_EFFECTS.value;

        if (respawnEffects.length <= 0) {
            return effectsList;
        }

        for (String s : respawnEffects) {
            String[] elements = s.split("\\s+");
            String[] effectAttributes = new String[3];

            if (!validEffectsList.contains(elements[0])) {
                continue;
            }

            try {
                Integer.parseInt(elements[1]);
                Integer.parseInt(elements[2]);
            } catch (Exception e1) {
                CorpseRun.logger.log(Level.ERROR, "Problem parsing respawn effects list!", e1);
                elements[1] = elements[2] = "0";
            } finally {
                elements[1] = Integer.toString(Math.max(1,Math.min(Integer.parseInt(elements[1]), 1600)));
                elements[2] = Integer.toString(Math.max(1,Math.min(Integer.parseInt(elements[2]), 4)));
                effectAttributes[0] = elements[0];
                effectAttributes[1] = elements[1];
                effectAttributes[2] = elements[2];
            }

            effectsList.add(effectAttributes);
        }

        return effectsList;
    }
}
