/*
 * Copyright (c) 2017. C4, MIT License
 */

package c4.corpsecomplex.common.modules.effects;

import c4.corpsecomplex.CorpseComplex;
import com.google.common.collect.Maps;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.*;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.logging.log4j.Level;

import javax.annotation.Nonnull;
import java.util.Map;
import java.util.UUID;

public class MoriPotion extends Potion {

    private static final String name = "Memento Mori";
    private static final double intIncrement = 1.0;
    private static final double percentIncrement = 0.05;

    private ResourceLocation icon;
    private Map<IAttribute, Double> changeAmountMap = Maps.newHashMap();
    private Map<IAttribute, AttributeModifier> attributeModifierMap = Maps.newHashMap();
    private double tickHealth;
    private double tickMove;
    private double tickSpeed;
    private double tickDamage;

    public MoriPotion() {
        super(true, 1);
        this.setRegistryName("mori");
        this.setPotionName("effect." + CorpseComplex.MODID + ".mori");
        icon = new ResourceLocation(CorpseComplex.MODID, "textures/icons/mori.png");
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        return (duration < (MoriModule.duration * 20)) && (MoriModule.doRecover) && (isChangeTick(duration));
    }

    @Override
    public void performEffect(@Nonnull EntityLivingBase entityLivingBaseIn, int amplifier) {
        int duration = entityLivingBaseIn.getActivePotionEffect(MoriModule.moriPotion).getDuration();

        if (MoriModule.modHealth != 0 && duration % tickHealth == 0) {
            incrementRecover(entityLivingBaseIn, SharedMonsterAttributes.MAX_HEALTH);
            if (entityLivingBaseIn.getHealth() > entityLivingBaseIn.getMaxHealth()) {
                entityLivingBaseIn.setHealth(entityLivingBaseIn.getMaxHealth());
            }
        }

        if (MoriModule.modMove != 0 && duration % tickMove == 0) {
            incrementRecover(entityLivingBaseIn, SharedMonsterAttributes.MOVEMENT_SPEED);
        }

        if (MoriModule.modSpeed != 0 && duration % tickSpeed == 0) {
            incrementRecover(entityLivingBaseIn, SharedMonsterAttributes.ATTACK_SPEED);
        }

        if (MoriModule.modDamage != 0 && duration % tickDamage == 0) {
            incrementRecover(entityLivingBaseIn, SharedMonsterAttributes.ATTACK_DAMAGE);
        }
    }

    void setModifiers() {
        attributeModifierMap.put(SharedMonsterAttributes.MAX_HEALTH, new AttributeModifier(UUID.fromString("ca572ca7-d11e-4054-b225-f4c797cdf69b"), name, MoriModule.modHealth, 0));
        attributeModifierMap.put(SharedMonsterAttributes.MOVEMENT_SPEED, new AttributeModifier(UUID.fromString("f9a9495d-89b5-4676-8345-bc2e92936821"), name, MoriModule.modMove, 2));
        attributeModifierMap.put(SharedMonsterAttributes.ATTACK_SPEED, new AttributeModifier(UUID.fromString("9fe627b8-3477-4ccf-9587-87776259172f"), name, MoriModule.modSpeed, 2));
        attributeModifierMap.put(SharedMonsterAttributes.ATTACK_DAMAGE, new AttributeModifier(UUID.fromString("ae5b003a-65f3-41f2-b104-4c71a2261d5b"), name, MoriModule.modDamage, 0));
        tickHealth = setIncrement(SharedMonsterAttributes.MAX_HEALTH, MoriModule.modHealth, false);
        tickMove = setIncrement(SharedMonsterAttributes.MOVEMENT_SPEED, MoriModule.modMove, true);
        tickSpeed = setIncrement(SharedMonsterAttributes.ATTACK_SPEED, MoriModule.modSpeed, true);
        tickDamage = setIncrement(SharedMonsterAttributes.ATTACK_DAMAGE, MoriModule.modDamage, false);
    }

    private boolean isChangeTick(int duration) {
        return (duration % tickHealth == 0) || (duration % tickMove == 0) || (duration % tickSpeed == 0) || (duration % tickDamage == 0);
    }

    private void incrementRecover(EntityLivingBase entityIn, IAttribute attribute) {
        IAttributeInstance iAttributeInstance = entityIn.getAttributeMap().getAttributeInstance(attribute);
        AttributeModifier attributeModifier = iAttributeInstance.getModifier(attributeModifierMap.get(attribute).getID());

        if (attributeModifier == null || entityIn.getEntityWorld().isRemote) { return; }

        iAttributeInstance.removeModifier(attributeModifier);
        double changeAmount = changeAmountMap.get(attribute);
        iAttributeInstance.applyModifier(new AttributeModifier(attributeModifier.getID(), attributeModifier.getName(), attributeModifier.getAmount() + changeAmount, attributeModifier.getOperation()));
    }

    private double setIncrement(IAttribute attribute, double amount, boolean isPercent) {

        if (amount == 0) { return 0; }

        double changeAmount;
        double sign = Math.signum(amount) * -1;
        double changeTick = Math.abs(MoriModule.duration / amount) * 20;

        if (isPercent) {
            changeAmount = sign * percentIncrement;
            changeTick *= percentIncrement;
        } else {
            changeAmount = sign * intIncrement;
            changeTick *= intIncrement;
        }

        if (changeTick >= 1) {
            changeTick = Math.floor(changeTick);
        } else if (isPercent) {
            changeAmount = sign * (1 / changeTick);
            changeTick = 1;
        } else {
            changeAmount = sign * Math.floor(1 / changeTick);
            changeTick = 1;
        }

        changeAmountMap.put(attribute, changeAmount);

        return changeTick;
    }

    @Override
    public void removeAttributesModifiersFromEntity(EntityLivingBase entityLivingBaseIn, AbstractAttributeMap attributeMapIn, int amplifier)
    {
        for (Map.Entry<IAttribute, AttributeModifier> entry : this.attributeModifierMap.entrySet())
        {
            IAttributeInstance iattributeinstance = attributeMapIn.getAttributeInstance(entry.getKey());

            if (iattributeinstance != null)
            {
                iattributeinstance.removeModifier(entry.getValue());
            }
        }
    }

    @Override
    public void applyAttributesModifiersToEntity(EntityLivingBase entityLivingBaseIn, AbstractAttributeMap attributeMapIn, int amplifier) {
        for (Map.Entry<IAttribute, AttributeModifier> entry : this.attributeModifierMap.entrySet())
        {
            IAttributeInstance iattributeinstance = attributeMapIn.getAttributeInstance(entry.getKey());

            if (iattributeinstance != null)
            {
                AttributeModifier attributemodifier = entry.getValue();
                iattributeinstance.removeModifier(attributemodifier);
                iattributeinstance.applyModifier(new AttributeModifier(attributemodifier.getID(), attributemodifier.getName(), attributemodifier.getAmount(), attributemodifier.getOperation()));
            }
        }
    }

    @Override
    public boolean hasStatusIcon() {
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void renderInventoryEffect(int x, int y, PotionEffect effect, Minecraft mc) {
        if (mc.currentScreen != null) {
            mc.getTextureManager().bindTexture(icon);
            Gui.drawModalRectWithCustomSizedTexture(x + 6, y + 7, 0, 0, 16, 16, 16, 16);
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void renderHUDEffect(int x, int y, PotionEffect effect, Minecraft mc, float alpha) {
        mc.getTextureManager().bindTexture(icon);
        Gui.drawModalRectWithCustomSizedTexture(x + 4, y + 4, 0, 0, 16, 16, 16, 16);
    }
}
