package c4.corpsecomplex.common.modules.effects;

import c4.corpsecomplex.common.Module;
import c4.corpsecomplex.common.Submodule;
import c4.corpsecomplex.common.helpers.ModuleHelper;
import com.pam.harvestcraft.blocks.blocks.BlockPamCake;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerPickupXpEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import org.omg.CORBA.ObjectHolder;

import java.util.ArrayList;
import java.util.Arrays;

public class MoriModule extends Submodule {

    public static boolean registerPotion;

    @GameRegistry.ObjectHolder("corpsecomplex:mori")
    static MoriPotion moriPotion;

    static double modHealth;
    static double modArmor;
    static double modToughness;
    static double modMove;
    static double modDamage;
    static double modSpeed;
    static boolean doRecover;
    static int duration;

    private static boolean noFood;
    private static boolean noXP;
    private static String[] curativeItems;
    private static ArrayList<ItemStack> cureList;
    private static boolean cfgEnabled;

    @SubscribeEvent
    public void onPlayerRespawnFinish(net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerRespawnEvent e) {

        if (e.player.world.isRemote) { return; }

        PotionEffect moriEffect = new PotionEffect(moriPotion, duration * 20, 0, false, true);
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

        if (!noFood || !e.getEntityPlayer().isPotionActive(MoriModule.moriPotion)) { return; }

        if (e.getWorld().getBlockState(e.getPos()).getBlock() == Blocks.CAKE) {
            e.setCanceled(true);
        }
    }

    @SubscribeEvent
    @Optional.Method(modid = "harvestcraft")
    public void onEatingPamCake(PlayerInteractEvent.RightClickBlock e) {

        if (!noFood || !e.getEntityPlayer().isPotionActive(MoriModule.moriPotion)) { return; }

        if (e.getWorld().getBlockState(e.getPos()).getBlock() instanceof BlockPamCake) {
            e.setCanceled(true);
        }
    }

    public MoriModule(Module parentModule) {
        super(parentModule, "Custom Respawn Effect");
        configCategory.setComment("Customize your own respawn effect for modifying player attributes");
    }

    public void loadModuleConfig() {
        setCategoryComment();
        cfgEnabled = getBool("Enable Custom Respawn Effect", false, "Set to true to enable custom effect applied on respawn");
        modHealth = getFloat("Maximum Health Modifier", 0, -1024, 1024, "Set maximum health modifier");
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
        ModuleHelper.cfg.get(configCategory.getQualifiedName(), "Enable Custom Respawn Effect", false).setRequiresMcRestart(true);
        if (moriPotion != null) {
            moriPotion.setModifiers();
        }
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

    @Override
    public void setEnabled() {
        enabled = registerPotion = cfgEnabled && parentModule.enabled;
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
