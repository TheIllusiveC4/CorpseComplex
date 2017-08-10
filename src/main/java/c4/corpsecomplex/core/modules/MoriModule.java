package c4.corpsecomplex.core.modules;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerPickupXpEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class MoriModule extends Submodule {

    public static MoriPotion moriPotion = new MoriPotion();
    public static double modHealth;
    public static double modArmor;
    public static double modToughness;
    public static double modMove;
    public static double modDamage;
    public static double modSpeed;
    public static boolean doRecover;
    public static int duration;
    public static boolean cfgEnabled;
    public static boolean noFood;
    public static boolean noXP;
    public static String[] curativeItems;
    private static ArrayList<ItemStack> cureList;

    public MoriModule(Module parentModule) {
        super(parentModule, "Custom Respawn Effect");
        configCategory.setComment("Custom Respawn Effect Management");
    }

    public void loadModuleConfig() {
        setCategoryComment();
        cfgEnabled = getBool("Enable Custom Respawn Effect", false, "Set to true to enable custom effect applied on respawn");
        modHealth = getFloat("Maximum Health Modifier", 0, -20, 20, "Set maximum health modifier");
        modArmor = getFloat("Armor Modifier", 0, -30, 30, "Set armor modifier");
        modToughness = getFloat("Armor Toughness Modifier", 0, -20, 20, "Set armor toughness modifier");
        modMove = getFloat("Movement Speed Percent Modifier", 0.0f, -1.0f, 1.0f, "Set movement speed percent modifier");
        modDamage = getFloat("Attack Damage Modifier", 0.0f, -2048.0f, 2048.0f, "Set attack damage modifier");
        modSpeed = getFloat("Attack Speed Percent Modifier", 0.0f, -1.0f, 1.0f, "Set attack speed percent modifier");
        doRecover = getBool("Gradual Recovery", false, "Set to true to enable gradual recovery (modifiers will diminish gradually as the effect goes on)");
        duration = getInt("Duration", 0, 0, 1600, "Set duration (seconds) for the effect");
        noFood = getBool("Cannot Eat Food", false, "Set to true to disable eating food while effect is active");
        noXP = getBool("Cannot Gain XP", false, "Set to true to disable gaining experience while effect is active");
        curativeItems = getStringList("Curative Items", new String[]{"minecraft:milk_bucket"}, "List of items that can cure the effect");
        moriPotion.setModifiers();
        initCureList();
    }

    private static void initCureList() {

        cureList = new ArrayList<>();

        for (String s : curativeItems) {
            Item item = Item.getByNameOrId(s);
            if (item != null) {
                cureList.add(new ItemStack(item));
            }
        }
    }

    @SubscribeEvent
    public void onPlayerRespawnFinish(net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerRespawnEvent e) {

        PotionEffect moriEffect = new PotionEffect(moriPotion, duration * 20, 0, true, true);
        moriEffect.setCurativeItems(cureList);
        e.player.addPotionEffect(moriEffect);
    }

    @SubscribeEvent
    public void onPlayerXPPickUp(PlayerPickupXpEvent e) {

        if (!noXP) { return; }

        if (e.getEntityPlayer().isPotionActive(MoriModule.moriPotion)) {
            e.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void onEatingFood(PlayerInteractEvent.RightClickItem e) {

        if (!noFood) { return; }

        if ((e.getEntityPlayer().isPotionActive(MoriModule.moriPotion)) && (e.getItemStack().getItemUseAction() == EnumAction.EAT)) {
            e.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void onEatingCake(PlayerInteractEvent.RightClickBlock e) {

        if (!noFood) { return; }

        if ((e.getEntityPlayer().isPotionActive(MoriModule.moriPotion)) && e.getWorld().getBlockState(e.getPos()).getBlock() == Blocks.CAKE) {
            e.setCanceled(true);
        }
    }

    @Override
    public void setEnabled() {
        enabled = cfgEnabled && parentModule.enabled;
    }

    @Override
    public void initPropOrder() {
        propOrder = new ArrayList<>(Arrays.asList("Enable Custom Respawn Effect", "Duration", "Curative Items", "Gradual Recovery", "Maximum Health Modifier",
                "Armor Modifier", "Armor Toughness Modifier", "Attack Damage Modifier", "Attack Speed Percent Modifier", "Movement Speed Percent Modifier"));
    }

    @Override
    public boolean hasEvents() {
        return true;
    }
}
