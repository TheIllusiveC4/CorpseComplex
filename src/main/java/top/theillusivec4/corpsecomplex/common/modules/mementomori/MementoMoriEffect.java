package top.theillusivec4.corpsecomplex.common.modules.mementomori;

import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import top.theillusivec4.corpsecomplex.CorpseComplex;

public class MementoMoriEffect extends Effect {

  public MementoMoriEffect() {
    super(EffectType.HARMFUL, 0);
    this.setRegistryName(CorpseComplex.MODID, "memento_mori");
  }


}
