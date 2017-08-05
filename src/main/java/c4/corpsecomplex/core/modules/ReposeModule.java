package c4.corpsecomplex.core.modules;

public class ReposeModule extends Submodule {

    public static int maxHealth;
    public static int modArmor;
    public static int bonusToughness;
    public static double movSpeedMod;
    public static double atkDmgMod;
    public static double atkSpdMod;
    public static boolean gradRecover;

    public ReposeModule(Module parentModule) {
        super(parentModule);
    }

    public void loadModuleConfig() {
        maxHealth = getInt("Maximum Health", 20, 1, 1024, "Set maximum health for the player");
        modArmor = getInt("Modify Armor Level", 0, -30, 30, "Set armor modifier for the player");
        bonusToughness = getInt("Bonus Armor Toughness", 0, -20, 20, "Set bonus armor toughness for the player");
        movSpeedMod = getFloat("Movement Speed Modifier", 0.0f, -0.5f, 0.5f, "Set movement speed modifier for the player");
        atkDmgMod = getFloat("Attack Damage Modifier", 0.0f, -0.5f, 0.5f, "Set attack damage modifier for the player");
        atkSpdMod = getFloat("Attack Speed Modifier", 0.0f, -0.5f, 0.5f, "Set attack speed modifier for the player");
        gradRecover = getBool("Gradual Recovery", false, "Set to true to enable gradual recovery (effects will diminish as the effect goes on)");
    }
}
