package top.theillusivec4.corpsecomplex.common.modules.mementomori;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import net.minecraft.block.CakeBlock;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerRespawnEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerXpEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.server.FMLServerStartedEvent;
import net.minecraftforge.registries.ForgeRegistries;
import top.theillusivec4.corpsecomplex.common.config.CorpseComplexConfig;
import top.theillusivec4.corpsecomplex.common.modules.mementomori.MementoMoriEffect.AttributeInfo;
import top.theillusivec4.corpsecomplex.common.registry.CorpseComplexRegistry;

public class MementoMoriModule {

  private static final List<ItemStack> CURES = new ArrayList<>();

  @SubscribeEvent
  public void serverStart(final FMLServerStartedEvent evt) {

    if (CorpseComplexConfig.SERVER.healthMod.get() != 0) {
      MementoMoriEffect.ATTRIBUTES.put(SharedMonsterAttributes.MAX_HEALTH,
          new AttributeInfo(CorpseComplexConfig.SERVER.healthMod.get(),
              UUID.fromString("ca572ca7-d11e-4054-b225-f4c797cdf69b"), Operation.ADDITION));
    }

    if (CorpseComplexConfig.SERVER.armorMod.get() != 0) {
      MementoMoriEffect.ATTRIBUTES.put(SharedMonsterAttributes.ARMOR,
          new AttributeInfo(CorpseComplexConfig.SERVER.armorMod.get(),
              UUID.fromString("b3bd0150-1953-4971-a822-8445953c4195"), Operation.ADDITION));
    }

    if (CorpseComplexConfig.SERVER.toughnessMod.get() != 0) {
      MementoMoriEffect.ATTRIBUTES.put(SharedMonsterAttributes.ARMOR_TOUGHNESS,
          new AttributeInfo(CorpseComplexConfig.SERVER.toughnessMod.get(),
              UUID.fromString("5113ef1e-5200-4d6a-a898-946f0e4b5d26"), Operation.ADDITION));
    }

    if (CorpseComplexConfig.SERVER.movementMod.get() != 0) {
      MementoMoriEffect.ATTRIBUTES.put(SharedMonsterAttributes.MOVEMENT_SPEED,
          new AttributeInfo(CorpseComplexConfig.SERVER.movementMod.get(),
              UUID.fromString("f9a9495d-89b5-4676-8345-bc2e92936821"), Operation.MULTIPLY_TOTAL));
    }

    if (CorpseComplexConfig.SERVER.attackSpeedMod.get() != 0) {
      MementoMoriEffect.ATTRIBUTES.put(SharedMonsterAttributes.ATTACK_SPEED,
          new AttributeInfo(CorpseComplexConfig.SERVER.attackSpeedMod.get(),
              UUID.fromString("9fe627b8-3477-4ccf-9587-87776259172f"), Operation.MULTIPLY_TOTAL));
    }

    if (CorpseComplexConfig.SERVER.damageMod.get() != 0) {
      MementoMoriEffect.ATTRIBUTES.put(SharedMonsterAttributes.ATTACK_DAMAGE,
          new AttributeInfo(CorpseComplexConfig.SERVER.damageMod.get(),
              UUID.fromString("ae5b003a-65f3-41f2-b104-4c71a2261d5b"), Operation.ADDITION));
    }
    CorpseComplexConfig.SERVER.mementoCures.get().forEach(cure -> {
      Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(cure));

      if (item != null) {
        CURES.add(new ItemStack(item));
      }
    });
  }

  @SubscribeEvent
  public void playerRespawn(final PlayerRespawnEvent evt) {
    PlayerEntity playerEntity = evt.getPlayer();

    if (!MementoMoriEffect.ATTRIBUTES.isEmpty() || CorpseComplexConfig.SERVER.noFood.get()
        || CorpseComplexConfig.SERVER.percentXp.get() != 0) {
      EffectInstance instance = new EffectInstance(CorpseComplexRegistry.MEMENTO_MORI,
          CorpseComplexConfig.SERVER.duration.get() * 20);
      instance.setCurativeItems(CURES);
      playerEntity.addPotionEffect(instance);

      if (playerEntity.getHealth() < playerEntity.getMaxHealth()) {
        playerEntity.setHealth(playerEntity.getMaxHealth());
      }
    }
  }

  @SubscribeEvent
  public void eatingFood(final PlayerInteractEvent.RightClickItem evt) {

    if (CorpseComplexConfig.SERVER.noFood.get() && evt.getPlayer()
        .isPotionActive(CorpseComplexRegistry.MEMENTO_MORI)
        && evt.getItemStack().getUseAction() == UseAction.EAT) {
      evt.setCanceled(true);
    }
  }

  @SubscribeEvent
  public void eatingCake(final PlayerInteractEvent.RightClickBlock evt) {

    if (CorpseComplexConfig.SERVER.noFood.get() && evt.getPlayer()
        .isPotionActive(CorpseComplexRegistry.MEMENTO_MORI) && evt.getWorld()
        .getBlockState(evt.getPos()).getBlock() instanceof CakeBlock) {
      evt.setCanceled(true);
    }
  }

  @SubscribeEvent
  public void playerChangeXp(final PlayerXpEvent.XpChange evt) {
    double percentXp = CorpseComplexConfig.SERVER.percentXp.get();
    PlayerEntity playerEntity = evt.getPlayer();
    EffectInstance effectInstance = playerEntity
        .getActivePotionEffect(CorpseComplexRegistry.MEMENTO_MORI);

    if (percentXp != 0 && effectInstance != null) {
      double modifier =
          CorpseComplexConfig.SERVER.gradualRecovery.get() ? (((float) effectInstance.getDuration())
              / CorpseComplexConfig.SERVER.duration.get()) : 1.0D;
      percentXp *= modifier;
      evt.setAmount(Math.max(1, (int) (evt.getAmount() * (1 + percentXp))));
    }
  }
}
