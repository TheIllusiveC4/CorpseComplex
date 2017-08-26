package c4.corpsecomplex.common.modules.inventory.enchantment;

import c4.corpsecomplex.CorpseComplex;
import c4.corpsecomplex.common.Module;
import c4.corpsecomplex.common.Submodule;
import c4.corpsecomplex.common.helpers.ModuleHelper;
import net.minecraft.enchantment.Enchantment;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import org.apache.logging.log4j.Level;

import java.util.ArrayList;
import java.util.Collections;

public class EnchantmentModule extends Submodule {

    @GameRegistry.ObjectHolder("corpsecomplex:soulbound")
    public static EnchantmentSoulbound soulbound;

    public static boolean registerEnchant;
    public static String rarity;

    static boolean canApplyEnchantingTable;

    private static boolean cfgEnabled;

    public EnchantmentModule(Module parentModule) {
        super(parentModule, "Soulbound");
        configCategory.setComment("Enable and customize the soulbound enchantment");
    }

    public void loadModuleConfig() {
        setCategoryComment();
        cfgEnabled = getBool("Enable Soulbound Enchantment", false, "Set to true to enable soulbound enchantment");
        canApplyEnchantingTable = getBool("Can Apply at Enchanting Table", true, "Set to true to allow enchanting soulbound at the enchantment table");
        rarity = getString("Rarity", "VERY_RARE", "The rarity of the enchantment (COMMON, UNCOMMON, RARE, or VERY_RARE)", new String[] {"COMMON", "UNCOMMON", "RARE", "VERY_RARE"});
        ModuleHelper.cfg.getCategory(configCategory.getQualifiedName()).setRequiresMcRestart(true);
    }

    public void initPropOrder() {
        propOrder = new ArrayList<>(Collections.singletonList("Enable Soulbound Enchantment"));
    }

    public void setEnabled() {
        enabled = registerEnchant = cfgEnabled && parentModule.enabled;
    }

    @Override
    public boolean hasEvents() {
        return true;
    }
}
