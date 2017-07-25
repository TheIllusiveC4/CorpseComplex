package c4.corpserun.core.modules;

import c4.corpserun.capability.DeathInventory;
import c4.corpserun.config.ConfigHelper;
import c4.corpserun.core.inventory.InventoryHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerDropsEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InventoryModule extends Module {

    public static boolean keepArmor;
    public static boolean keepHotbar;
    public static boolean keepMainhand;
    public static boolean keepOffhand;
    public static boolean keepMainInventory;
    public static boolean destroyCursed;
    public static double randomDrop;
    public static String[] essentialItems;
    public static String[] cursedItems;
    public static double randomDestroy;
    public static double dropLoss;
    public static double keptLoss;
    public static double dropDrain;
    public static double keptDrain;
    public static boolean enableEnergy;
    public static boolean enableDurability;
    private static boolean cfgEnabled;

    @SubscribeEvent (priority = EventPriority.HIGHEST)
    public void storeDeathInventory (LivingDeathEvent e) {

        if (!(e.getEntityLiving() instanceof EntityPlayer)
                || e.getEntity().getEntityWorld().getGameRules().getBoolean("keepInventory")) { return;}

        InventoryHandler inventoryHandler = new InventoryHandler((EntityPlayer) e.getEntityLiving());
        inventoryHandler.initStorage();
        inventoryHandler.iterateInventory();
    }

    @SubscribeEvent (priority = EventPriority.LOWEST)
    public void onPlayerDrop (PlayerDropsEvent e) {

        EntityPlayer player = e.getEntityPlayer();

        if (!player.world.getGameRules().getBoolean("keepInventory")) {
            retrieveStorage(player);
        }
    }

    @SubscribeEvent
    public void onPlayerRespawnBegin(PlayerEvent.Clone e) {

        if (!e.isWasDeath() || e.getEntityPlayer().world.isRemote) { return;}

        EntityPlayer player = e.getEntityPlayer();
        EntityPlayer oldPlayer = e.getOriginal();

        if (!player.world.getGameRules().getBoolean("keepInventory")) {
            retrieveStorage(player, oldPlayer);
        }
    }

    public InventoryModule() {
        configName = "Inventory";
        configDescription = "Inventory Management";
        configCategory = new ConfigCategory(configName);
        configCategory.setComment(configDescription);
        prevEnabled = false;
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
        setDurabilityAndEnergy();
    }

    public void initPropOrder() {
        propOrder = new ArrayList<>(Arrays.asList("Enable Inventory Module", "Keep Armor", "Keep Hotbar", "Keep Mainhand", "Keep Offhand",
                "Keep Main Inventory", "Durability Loss on Drops", "Durability Loss on Kept Items", "Energy Drain on Drops", "Energy Drain on Kept Items",
                "Random Drop Chance", "Random Destroy Chance", "Essential Items", "Cursed Items", "Destroy Cursed Items"));
    }

    public void setEnabled() {
        enabled = cfgEnabled;
    }

    private static void setDurabilityAndEnergy() {
        enableEnergy = (keptDrain != 0 || dropDrain != 0);
        enableDurability = (keptLoss != 0 || dropLoss != 0);
    }

    private static void retrieveStorage(EntityPlayer player) {

        InventoryHandler.retrieveStorage(player, player.getCapability(DeathInventory.Provider.DEATH_INV_CAP, null));
    }

    private static void retrieveStorage(EntityPlayer player, EntityPlayer oldPlayer) {

        InventoryHandler.retrieveStorage(player, oldPlayer.getCapability(DeathInventory.Provider.DEATH_INV_CAP, null));
    }
}
