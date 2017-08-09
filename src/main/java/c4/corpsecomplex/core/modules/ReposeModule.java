package c4.corpsecomplex.core.modules;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.Collections;

public class ReposeModule extends Submodule {

    public static ReposePotion reposePotion = new ReposePotion();
    public static double maxHealth;
    public static double modArmor;
    public static double bonusToughness;
    public static double movSpeedMod;
    public static double atkDmgMod;
    public static double atkSpdMod;
    public static boolean gradRecover;
    public static int duration;
    public static boolean cfgEnabled;

    public ReposeModule(Module parentModule) {
        super(parentModule, "Repose Effect");
        configCategory.setComment("Repose Effect Management");
    }

    public void loadModuleConfig() {
        setCategoryComment();
        cfgEnabled = getBool("Enable Repose Effect", false, "Set to true to enable repose effect on respawn");
        maxHealth = getFloat("Maximum Health", 0, -20, 20, "Set maximum health for the player");
        modArmor = getFloat("Modify Armor Level", 0, -30, 30, "Set armor modifier for the player");
        bonusToughness = getFloat("Bonus Armor Toughness", 0, -20, 20, "Set bonus armor toughness for the player");
        movSpeedMod = getFloat("Movement Speed Modifier", 0.0f, -1.0f, 1.0f, "Set movement speed modifier for the player");
        atkDmgMod = getFloat("Attack Damage Modifier", 0.0f, -1024.0f, 0.5f, "Set attack damage modifier for the player");
        atkSpdMod = getFloat("Attack Speed Modifier", 0.0f, -1.0f, 0.5f, "Set attack speed modifier for the player");
        gradRecover = getBool("Gradual Recovery", false, "Set to true to enable gradual recovery (effects will diminish as the effect goes on)");
        duration = getInt("Duration", 0, 0, 1600, "Set duration (seconds) for repose effect");
        reposePotion.setModifiers();
    }

    @SubscribeEvent
    public void onPlayerRespawnFinish(net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerRespawnEvent e) {

        EntityPlayer player = e.player;
        player.addPotionEffect(new PotionEffect(reposePotion, duration * 20));
    }

    @Override
    public void setEnabled() {
        enabled = cfgEnabled;
    }

    @Override
    public void initPropOrder() {
        propOrder = new ArrayList<>(Collections.singletonList("Enable Repose Effect"));
    }

    @Override
    public boolean hasEvents() {
        return true;
    }
}
