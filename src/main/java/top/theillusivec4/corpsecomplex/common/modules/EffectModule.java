package top.theillusivec4.corpsecomplex.common.modules;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerRespawnEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.server.FMLServerStartedEvent;
import net.minecraftforge.registries.ForgeRegistries;
import top.theillusivec4.corpsecomplex.common.capability.DeathStorageCapability;
import top.theillusivec4.corpsecomplex.common.config.CorpseComplexConfig;
import top.theillusivec4.corpsecomplex.common.config.CorpseComplexConfig.PermissionMode;

public class EffectModule {

  private static final List<EffectInstance> effects = new ArrayList<>();
  private static final List<ItemStack> cures = new ArrayList<>();
  private static final List<Effect> keepEffects = new ArrayList<>();

  @SubscribeEvent
  public void finishItemUse(LivingEntityUseItemEvent.Finish evt) {
    LivingEntity entity = evt.getEntityLiving();

    if (!entity.getEntityWorld().isRemote()) {
      cures.forEach(itemStack -> {
        if (ItemStack.areItemsEqual(evt.getItem(), itemStack)) {
          entity.curePotionEffects(evt.getItem());
        }
      });
    }
  }

  @SubscribeEvent
  public void playerDeath(final LivingDeathEvent evt) {

    if (!(evt.getEntityLiving() instanceof PlayerEntity)) {
      return;
    }
    PlayerEntity playerEntity = (PlayerEntity) evt.getEntityLiving();
    World world = playerEntity.getEntityWorld();

    if (!world.isRemote()) {
      DeathStorageCapability.getCapability(playerEntity).ifPresent(
          deathStorage -> playerEntity.getActivePotionEffects().forEach(effectInstance -> {
            boolean flag;

            if (keepEffects.isEmpty()) {
              flag = true;
            } else if (CorpseComplexConfig.SERVER.keepEffectsMode.get()
                == PermissionMode.BLACKLIST) {
              flag = !keepEffects.contains(effectInstance.getPotion());
            } else {
              flag = keepEffects.contains(effectInstance.getPotion());
            }

            if (flag) {
              deathStorage.addEffectInstance(effectInstance);
            }
          }));
    }
  }

  @SubscribeEvent
  public void playerClone(final PlayerEvent.Clone evt) {

    if (evt.isWasDeath()) {
      DeathStorageCapability.getCapability(evt.getPlayer()).ifPresent(
          deathStorage -> DeathStorageCapability.getCapability(evt.getOriginal()).ifPresent(
              oldDeathStorage -> oldDeathStorage.getEffects()
                  .forEach(deathStorage::addEffectInstance)));
    }
  }

  @SubscribeEvent
  public void playerRespawn(final PlayerRespawnEvent evt) {

    if (!evt.isEndConquered()) {
      PlayerEntity player = evt.getPlayer();
      DeathStorageCapability.getCapability(player).ifPresent(deathStorage -> {
        deathStorage.getEffects().forEach(player::addPotionEffect);
        deathStorage.clearEffects();
      });
      effects.forEach(effectInstance -> {
        EffectInstance newEffect = new EffectInstance(effectInstance.getPotion(),
            effectInstance.getDuration(), effectInstance.getAmplifier());
        newEffect.setCurativeItems(effectInstance.getCurativeItems());
        player.addPotionEffect(newEffect);
      });
    }
  }
}
