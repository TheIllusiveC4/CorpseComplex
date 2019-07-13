/*
 * Copyright (c) 2017. <C4>
 *
 * This Java class is distributed as a part of Corpse Complex.
 * Corpse Complex is open source and licensed under the GNU General Public
 * License v3.
 * A copy of the license can be found here: https://www.gnu.org/licenses/gpl
 * .text
 */

package c4.corpsecomplex.common.modules.spawning;

import c4.corpsecomplex.CorpseComplex;
import c4.corpsecomplex.common.modules.spawning.capability.DeathLocation;
import c4.corpsecomplex.common.modules.spawning.capability.IDeathLocation;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

public class ItemScroll extends Item {

  public ItemScroll() {
    this.setCreativeTab(CreativeTabs.TRANSPORTATION);
    this.setRegistryName("scroll");
    this.setTranslationKey(CorpseComplex.MODID + ".scroll");
  }

  @Override
  public int getMaxItemUseDuration(ItemStack itemStack) {
    return 20;
  }

  @Override
  public EnumAction getItemUseAction(ItemStack itemStack) {
    return EnumAction.BOW;
  }

  @Override
  public ActionResult<ItemStack> onItemRightClick(World worldIn,
          EntityPlayer playerIn, EnumHand handIn) {
    if (!worldIn.isRemote) {
      IDeathLocation deathLoc = playerIn.getCapability(
              DeathLocation.Provider.DEATH_LOC_CAP, null);
      if (deathLoc == null || deathLoc.hasUsedScroll()) {
        playerIn.sendStatusMessage(
                new TextComponentTranslation("item.scroll.noUse"), true);
        return new ActionResult<>(EnumActionResult.FAIL,
                playerIn.getHeldItem(handIn));
      } else if (deathLoc.getDeathLocation() == null) {
        playerIn.sendStatusMessage(
                new TextComponentTranslation("item.scroll.noLocation"), true);
        return new ActionResult<>(EnumActionResult.FAIL,
                playerIn.getHeldItem(handIn));
      } else {
        playerIn.setActiveHand(handIn);
        return new ActionResult<>(EnumActionResult.SUCCESS,
                playerIn.getHeldItem(handIn));
      }
    } else {
      return new ActionResult<>(EnumActionResult.SUCCESS,
              playerIn.getHeldItem(handIn));
    }
  }

  @Override
  public ItemStack onItemUseFinish(ItemStack stack, World world,
          EntityLivingBase entity) {

    if (entity instanceof EntityPlayer && !world.isRemote) {
      EntityPlayer player = (EntityPlayer) entity;
      IDeathLocation deathLoc = player.getCapability(
              DeathLocation.Provider.DEATH_LOC_CAP, null);
      if (deathLoc != null) {
        BlockPos pos = deathLoc.getDeathLocation();
        ScrollTeleporter.teleportToPosition(player, pos,
                player.getHorizontalFacing(), deathLoc.getDeathDimension());
        if (player instanceof EntityPlayerMP) {
          CriteriaTriggers.CONSUME_ITEM.trigger((EntityPlayerMP) player, stack);
        }
        if (!player.capabilities.isCreativeMode) {
          stack.shrink(1);
        }
        deathLoc.setUsedScroll(true);
        player.getCooldownTracker().setCooldown(SpawningModule.scroll, 20);
      }
    }
    return stack;
  }

  @Override
  @SideOnly(Side.CLIENT)
  public boolean hasEffect(ItemStack stack) {
    return true;
  }

  @SideOnly(Side.CLIENT)
  public void initModel() {
    ModelLoader.setCustomModelResourceLocation(this, 0,
            new ModelResourceLocation(getRegistryName(), "inventory"));
  }

  @Override
  @SideOnly(Side.CLIENT)
  public void addInformation(ItemStack stack, @Nullable World worldIn,
          List<String> tooltip, ITooltipFlag flagIn) {
    tooltip.add("Return to death location");
  }
}
