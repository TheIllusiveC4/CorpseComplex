package top.theillusivec4.corpsecomplex.common.modules.mementomori;

import java.util.UUID;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.server.FMLServerStartedEvent;
import top.theillusivec4.corpsecomplex.common.CorpseComplexConfig;
import top.theillusivec4.corpsecomplex.common.modules.mementomori.MementoMoriEffect.AttributeInfo;

public class MementoMoriModule {

  @SubscribeEvent
  public void serverStart(final FMLServerStartedEvent evt) {
    MementoMoriEffect.ATTRIBUTES.put(SharedMonsterAttributes.MAX_HEALTH,
        new AttributeInfo(CorpseComplexConfig.SERVER.healthMod.get(),
            UUID.fromString("ca572ca7-d11e-4054-b225-f4c797cdf69b"), Operation.ADDITION));
    MementoMoriEffect.ATTRIBUTES.put(SharedMonsterAttributes.ARMOR,
        new AttributeInfo(CorpseComplexConfig.SERVER.armorMod.get(),
            UUID.fromString("b3bd0150-1953-4971-a822-8445953c4195"), Operation.ADDITION));
    MementoMoriEffect.ATTRIBUTES.put(SharedMonsterAttributes.ARMOR_TOUGHNESS,
        new AttributeInfo(CorpseComplexConfig.SERVER.toughnessMod.get(),
            UUID.fromString("5113ef1e-5200-4d6a-a898-946f0e4b5d26"), Operation.ADDITION));
    MementoMoriEffect.ATTRIBUTES.put(SharedMonsterAttributes.MOVEMENT_SPEED,
        new AttributeInfo(CorpseComplexConfig.SERVER.movementMod.get(),
            UUID.fromString("f9a9495d-89b5-4676-8345-bc2e92936821"), Operation.MULTIPLY_TOTAL));
    MementoMoriEffect.ATTRIBUTES.put(SharedMonsterAttributes.ATTACK_SPEED,
        new AttributeInfo(CorpseComplexConfig.SERVER.attackSpeedMod.get(),
            UUID.fromString("9fe627b8-3477-4ccf-9587-87776259172f"), Operation.MULTIPLY_TOTAL));
    MementoMoriEffect.ATTRIBUTES.put(SharedMonsterAttributes.ATTACK_DAMAGE,
        new AttributeInfo(CorpseComplexConfig.SERVER.damageMod.get(),
            UUID.fromString("ae5b003a-65f3-41f2-b104-4c71a2261d5b"), Operation.ADDITION));
  }
}
