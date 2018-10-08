/*
 * Copyright (c) 2018. <C4>
 *
 * This Java class is distributed as a part of Corpse Complex.
 * Corpse Complex is open source and licensed under the GNU General Public License v3.
 * A copy of the license can be found here: https://www.gnu.org/licenses/gpl.text
 */

package c4.corpsecomplex.common.modules.compatibility.camping;

import c4.corpsecomplex.common.modules.inventory.InventoryModule;
import c4.corpsecomplex.common.modules.inventory.capability.IDeathInventory;
import c4.corpsecomplex.common.modules.inventory.enchantment.EnchantmentModule;
import c4.corpsecomplex.common.modules.inventory.helpers.DeathInventoryHandler;
import c4.corpsecomplex.common.modules.inventory.helpers.DeathStackHelper;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.util.Constants;

import java.util.Random;

public class CampingHandler extends DeathInventoryHandler {

    private static final String MOD_ID = "camping";
    private NonNullList<ItemStack> campingSlots;

    public CampingHandler (EntityPlayer player) {
        super(player, MOD_ID);
        campingSlots = getCampingSlots(player);
    }

    public void storeInventory() {

        NonNullList<ItemStack> storedStacks = NonNullList.withSize(4, ItemStack.EMPTY);

        if (campingSlots == null) { return; }

        Random generator = new Random();

        for (int i = 0; i < campingSlots.size(); i++) {
            ItemStack stack = campingSlots.get(i);

            if (stack.isEmpty()) {
                continue;
            }

            boolean essential = DeathStackHelper.isEssential(stack);
            boolean cursed = !essential && DeathStackHelper.isCursed(stack);
            boolean store = ((checkToStore(0) && !cursed) || essential);

            if (!store && EnchantmentModule.registerEnchant) {
                int level = EnchantmentHelper.getEnchantmentLevel(EnchantmentModule.soulbound, stack);
                if (level != 0) {
                    store = essential = DeathStackHelper.handleSoulbound(stack, level);
                    cursed = !essential && cursed;
                }
            }

            if (cursed && InventoryModule.destroyCursed) {
                campingSlots.set(i, ItemStack.EMPTY);
                continue;
            }

            if (InventoryModule.dropLoss > 0 || InventoryModule.keptLoss > 0) {
                DeathStackHelper.loseDurability(player, stack, store);
            }

            if (stack.isEmpty()) {
                return;
            }

            if (store) {
                if (!essential && generator.nextDouble() < InventoryModule.randomDrop) {
                    if (generator.nextDouble() < InventoryModule.randomDestroy) {
                        campingSlots.set(i, ItemStack.EMPTY);
                        continue;
                    }
                    continue;
                }
            }

            storedStacks.set(i, stack.copy());
            campingSlots.set(i, ItemStack.EMPTY);
        }

        player.getEntityData().setTag("campInv", stacksToData(campingSlots));
        NBTTagCompound tag = player.getEntityData().getCompoundTag(EntityPlayer.PERSISTED_NBT_TAG);
        tag.setTag("campInv", stacksToData(storedStacks));
        player.getEntityData().setTag(EntityPlayer.PERSISTED_NBT_TAG, tag);
    }

    private NBTTagCompound stacksToData(NonNullList<ItemStack> stacks) {
        NBTTagCompound data = new NBTTagCompound();
        NBTTagList list = new NBTTagList();
        for (int i = 0; i < stacks.size(); i++) {
            NBTTagCompound compound = new NBTTagCompound();
            compound.setByte("slotIndex", ((byte) i));
            list.appendTag(stacks.get(i).writeToNBT(compound));
        }
        data.setTag("items", list);
        return data;
    }

    private NonNullList<ItemStack> getCampingSlots(EntityPlayer player) {
        NonNullList<ItemStack> stacks = NonNullList.withSize(4, ItemStack.EMPTY);

        if (player.getEntityData().hasKey("campInv")) {
            NBTTagList taglist = player.getEntityData().getCompoundTag("campInv").getTagList("items",
                    Constants.NBT.TAG_COMPOUND);
            if (!taglist.isEmpty()) {
                for (int i = 0; i < taglist.tagCount(); i++) {
                    NBTTagCompound compound = taglist.getCompoundTagAt(i);
                    stacks.set(compound.getByte("slotIndex"), new ItemStack(compound));
                }
            }
        }
        return stacks;
    }

    public boolean checkToStore(int slot) {
        return CampingModule.keepCamping;
    }

    public void retrieveInventory(IDeathInventory oldDeathInventory) {
        //NO-OP
    }
}