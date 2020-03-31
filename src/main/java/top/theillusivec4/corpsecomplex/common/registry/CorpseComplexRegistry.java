package top.theillusivec4.corpsecomplex.common.registry;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.potion.Effect;
import net.minecraftforge.registries.ObjectHolder;
import top.theillusivec4.corpsecomplex.CorpseComplex;

@ObjectHolder(CorpseComplex.MODID)
public class CorpseComplexRegistry {

  @ObjectHolder(RegistryReference.MEMENTO_MORI)
  public static final Effect MEMENTO_MORI;

  @ObjectHolder(RegistryReference.SOULBINDING)
  public static final Enchantment SOULBINDING;

  static {
    MEMENTO_MORI = null;
    SOULBINDING = null;
  }
}
