package top.theillusivec4.corpsecomplex.common.registry;

import net.minecraft.potion.Effect;
import net.minecraftforge.registries.ObjectHolder;
import top.theillusivec4.corpsecomplex.CorpseComplex;

@ObjectHolder(CorpseComplex.MODID)
public class CorpseComplexRegistry {

  @ObjectHolder(RegistryReference.MEMENTO_MORI)
  public static final Effect MEMENTO_MORI;

  static {
    MEMENTO_MORI = null;
  }
}
