package top.theillusivec4.corpsecomplex.common.modules;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.living.PotionEvent.PotionRemoveEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerRespawnEvent;
import net.minecraftforge.eventbus.api.Event.Result;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.server.FMLServerStartedEvent;
import net.minecraftforge.registries.ForgeRegistries;
import top.theillusivec4.corpsecomplex.common.CorpseComplexConfig;

public class EffectModule {

  private static final List<EffectInstance> effects = new ArrayList<>();
  private static final List<ItemStack> cures = new ArrayList<>();

  @SubscribeEvent
  public void finishItemUse(LivingEntityUseItemEvent.Finish evt) {
    LivingEntity entity = evt.getEntityLiving();

    if (!entity.getEntityWorld().isRemote() && !cures.isEmpty()) {
      entity.curePotionEffects(evt.getItem());

      if (evt.getItem().getItem() == Items.MILK_BUCKET) {
        evt.setResult(Result.ALLOW);
      }
    }
  }

  @SubscribeEvent
  public void serverStart(final FMLServerStartedEvent evt) {
    List<? extends String> configCures = CorpseComplexConfig.SERVER.cures.get();
    configCures.forEach(cure -> {
      Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(cure));

      if (item != null) {
        cures.add(new ItemStack(item));
      }
    });
    List<? extends String> configEffects = CorpseComplexConfig.SERVER.effects.get();
    configEffects.forEach(effect -> {
      String[] parse = effect.split(";");

      if (parse.length >= 2) {
        Effect effect1 = ForgeRegistries.POTIONS.getValue(new ResourceLocation(parse[0]));

        if (effect1 != null) {
          int amplifier = parse.length >= 3 ? Integer.parseInt(parse[2]) : 0;
          EffectInstance instance = new EffectInstance(effect1, Integer.parseInt(parse[1]) * 20,
              amplifier);

          if (parse.length >= 4) {
            instance.setCurativeItems(new ArrayList<>());
          } else {
            instance.setCurativeItems(cures);
          }
          effects.add(instance);
        }
      }
    });
  }

  @SubscribeEvent
  public void playerRespawn(final PlayerRespawnEvent evt) {

    if (!evt.isEndConquered()) {
      PlayerEntity player = evt.getPlayer();
      effects.forEach(effectInstance -> {
        EffectInstance newEffect = new EffectInstance(effectInstance.getPotion(),
            effectInstance.getDuration(), effectInstance.getAmplifier());
        newEffect.setCurativeItems(effectInstance.getCurativeItems());
        player.addPotionEffect(newEffect);
      });
    }
  }
}
