package c4.corpsecomplex.common.modules.inventory;

import c4.corpsecomplex.CorpseComplex;
import c4.corpsecomplex.api.DeathInventoryHandler;
import c4.corpsecomplex.api.capability.DeathInventory;
import c4.corpsecomplex.api.capability.IDeathInventory;
import c4.corpsecomplex.common.Module;
import c4.corpsecomplex.common.Submodule;
import c4.corpsecomplex.common.modules.compatibility.advinv.AdvHandler;
import c4.corpsecomplex.common.modules.compatibility.advinv.AdvModule;
import c4.corpsecomplex.common.modules.compatibility.baubles.BaublesHandler;
import c4.corpsecomplex.common.modules.compatibility.cosmeticarmorreworked.CosmeticHandler;
import c4.corpsecomplex.common.modules.compatibility.powerinventory.OPHandler;
import c4.corpsecomplex.common.modules.compatibility.rpginventory.RPGHandler;
import c4.corpsecomplex.common.modules.compatibility.thut_wearables.ThutHandler;
import c4.corpsecomplex.common.modules.compatibility.wearablebackpacks.WBHandler;
import c4.corpsecomplex.common.modules.inventory.enchantment.EnchantmentModule;
import c4.corpsecomplex.common.modules.compatibility.baubles.BaublesModule;
import c4.corpsecomplex.common.modules.compatibility.cosmeticarmorreworked.CosmeticModule;
import c4.corpsecomplex.common.modules.compatibility.powerinventory.OPModule;
import c4.corpsecomplex.common.modules.compatibility.rpginventory.RPGModule;
import c4.corpsecomplex.common.modules.compatibility.thut_wearables.ThutModule;
import c4.corpsecomplex.common.modules.compatibility.wearablebackpacks.WBModule;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerDropsEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.logging.log4j.Level;

import java.util.ArrayList;
import java.util.Arrays;

public class InventoryModule extends Module {

    public static ArrayList<Class<? extends DeathInventoryHandler>> handlerClasses;
    public static boolean destroyCursed;
    public static double randomDrop;
    public static String[] essentialItems;
    public static String[] cursedItems;
    public static double randomDestroy;
    public static double dropLoss;
    public static double keptLoss;
    public static double dropDrain;
    public static double keptDrain;

    static boolean keepArmor;
    static boolean keepHotbar;
    static boolean keepMainhand;
    static boolean keepOffhand;
    static boolean keepMainInventory;

    private static boolean cfgEnabled;

    {
        submoduleClasses = new ArrayList<>();
        handlerClasses = new ArrayList<>();

        addSubmodule(EnchantmentModule.class);
        addSubmodule("wearablebackpacks", WBModule.class, WBHandler.class);
        addSubmodule("thut_wearables", ThutModule.class, ThutHandler.class);
        addSubmodule("rpginventory", RPGModule.class, RPGHandler.class);
        addSubmodule("powerinventory", OPModule.class, OPHandler.class);
        addSubmodule("baubles", BaublesModule.class, BaublesHandler.class);
        addSubmodule("cosmeticarmorreworked", CosmeticModule.class, CosmeticHandler.class);
        addSubmodule("advinv", AdvModule.class, AdvHandler.class);

        handlerClasses.add(InventoryHandler.class);
    }

    @SubscribeEvent (priority = EventPriority.HIGHEST)
    public void storeDeathInventory (LivingDeathEvent e) {

        if (!(e.getEntityLiving() instanceof EntityPlayer)) { return; }

        EntityPlayer player = (EntityPlayer) e.getEntityLiving();

        if (!player.world.getGameRules().getBoolean("keepInventory") && !player.world.isRemote) {
            storeInventories(player);
        }
    }

    @SubscribeEvent (priority = EventPriority.LOWEST)
    public void onPlayerDrop (PlayerDropsEvent e) {

        EntityPlayer player = e.getEntityPlayer();

        if (!player.world.getGameRules().getBoolean("keepInventory") && !player.world.isRemote) {
            retrieveInventories(player);
        }
    }

    @SubscribeEvent
    public void onPlayerRespawnBegin(PlayerEvent.Clone e) {

        if (!e.isWasDeath() || e.getEntityPlayer().world.isRemote) { return;}

        EntityPlayer player = e.getEntityPlayer();
        EntityPlayer oldPlayer = e.getOriginal();

        if (!player.world.getGameRules().getBoolean("keepInventory")) {
            retrieveInventories(player, oldPlayer);
        }
    }

    public InventoryModule() {
        super("Inventory","Customize how your inventory is handled on death and respawn");
    }

    public void loadModuleConfig() {
        setCategoryComment();
        cfgEnabled = getBool("Enable Inventory Module", false, "Set to true to enable inventory module");
        keepArmor = getBool("Keep Armor", false, "Set to true to keep equipped armor on death");
        keepHotbar = getBool("Keep Hotbar", false, "Set to true to keep hotbar items on death");
        keepMainhand = getBool("Keep Mainhand", false, "Set to true to keep mainhand item on death");
        keepOffhand = getBool("Keep Offhand", false, "Set to true to keep offhand item on death");
        keepMainInventory = getBool("Keep Main Inventory", false, "Set to true to keep main inventory (non-equipped non-hotbar) items on death");
        destroyCursed = getBool("Destroy Cursed Items", false, "Set to true to destroy cursed items instead of dropping them");
        randomDrop = getFloat("Random Drop Chance", 0,0,1,"Percent chance that items that are kept will still drop");
        randomDestroy = getFloat("Random Destroy Chance", 0, 0, 1, "Percent chance that dropped items will be destroyed");
        essentialItems = getStringList("Essential Items", new String[]{}, "List of items that are always kept");
        cursedItems = getStringList("Cursed Items", new String[]{}, "List of items that are always dropped");
        dropLoss = getFloat("Durability Loss on Drops",0,0,1, "Percent of durability lost on death for drops");
        keptLoss = getFloat("Durability Loss on Kept Items", 0, 0, 1, "Percent of durability lost on death for kept items");
        dropDrain = getFloat("Energy Drain on Drops",0, 0, 1,"Percent of energy drained on death for drops");
        keptDrain = getFloat("Energy Drain on Kept Items", 0, 0, 1, "Percent of energy drained on death for kept items");
    }

    public void initPropOrder() {
        propOrder = new ArrayList<>(Arrays.asList("Enable Inventory Module", "Keep Armor", "Keep Hotbar", "Keep Mainhand", "Keep Offhand",
                "Keep Main Inventory", "Durability Loss on Drops", "Durability Loss on Kept Items", "Energy Drain on Drops", "Energy Drain on Kept Items",
                "Random Drop Chance", "Random Destroy Chance", "Essential Items", "Cursed Items", "Destroy Cursed Items"));
    }

    public void setEnabled() {
        enabled = cfgEnabled;
    }

    private void storeInventories(EntityPlayer player) {

        handlerClasses.forEach(handler -> {
            try {
                handler.getDeclaredConstructor(EntityPlayer.class).newInstance(player).storeInventory();
            } catch (Exception e1) {
                CorpseComplex.logger.log(Level.ERROR, "Failed to initialize handler " + handler, e1);
            }
        });
    }

    private void retrieveInventories(EntityPlayer player) {

        retrieveInventories(player, player);
    }

    private void retrieveInventories(EntityPlayer player, EntityPlayer oldPlayer) {

        IDeathInventory oldDeathInventory = oldPlayer.getCapability(DeathInventory.Provider.DEATH_INV_CAP, null);

        handlerClasses.forEach(handler -> {
            try {
                handler.getDeclaredConstructor(EntityPlayer.class).newInstance(player).retrieveInventory(oldDeathInventory);
            } catch (Exception e1) {
                CorpseComplex.logger.log(Level.ERROR, "Failed to initialize handler " + handler, e1);
            }
        });
    }

    private void addSubmodule(String modid, Class<? extends Submodule> submodule, Class<? extends DeathInventoryHandler> handler) {
        if (Loader.isModLoaded(modid) && !submoduleClasses.contains(submodule)) {
            submoduleClasses.add(submodule);
            if (!handlerClasses.contains(handler)) {
                handlerClasses.add(handler);
            }
        }
    }
}
