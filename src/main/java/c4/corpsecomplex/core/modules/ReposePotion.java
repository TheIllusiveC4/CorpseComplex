package c4.corpsecomplex.core.modules;

import c4.corpsecomplex.CorpseComplex;
import com.google.common.collect.Maps;
import com.sun.org.apache.regexp.internal.RE;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.resources.LanguageManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.logging.log4j.Level;

import javax.annotation.Nonnull;
import java.util.Map;
import java.util.UUID;

public class ReposePotion extends Potion {

    private Map<IAttribute, Double> changeAmountMap = Maps.newHashMap();
    private static final double intIncrement = 1.0;
    private static final double percentIncrement = 0.05;
    private ResourceLocation icon;
    private Map<IAttribute, AttributeModifier> attributeModifierMap = Maps.newHashMap();
    private double tickHealth;
    private double tickArmor;

    public ReposePotion() {
        super(true, 1);
        this.setRegistryName("repose");
        this.setPotionName("effect." + CorpseComplex.MODID + ".repose");
        icon = new ResourceLocation(CorpseComplex.MODID, "textures/icons/repose.png");
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        return (duration < (ReposeModule.duration * 20)) && (ReposeModule.gradRecover) && (isChangeTick(duration));
    }

    public boolean isChangeTick(int duration) {
        return (duration % tickHealth == 0) || (duration % tickArmor == 0);
    }

    public void setModifiers() {
        attributeModifierMap.put(SharedMonsterAttributes.MAX_HEALTH, new AttributeModifier(UUID.fromString("ca572ca7-d11e-4054-b225-f4c797cdf69b"), SharedMonsterAttributes.MAX_HEALTH.getName(), ReposeModule.maxHealth, 0));
        attributeModifierMap.put(SharedMonsterAttributes.ARMOR, new AttributeModifier(UUID.fromString("b3bd0150-1953-4971-a822-8445953c4195"), SharedMonsterAttributes.ARMOR.getName(), ReposeModule.modArmor, 0));
//        attributeModifierMap.put(SharedMonsterAttributes.ARMOR_TOUGHNESS, new AttributeModifier(UUID.fromString("5113ef1e-5200-4d6a-a898-946f0e4b5d26"), SharedMonsterAttributes.ARMOR_TOUGHNESS.getName(), ReposeModule.bonusToughness, 0));
//        attributeModifierMap.put(SharedMonsterAttributes.MOVEMENT_SPEED, new AttributeModifier(UUID.fromString("f9a9495d-89b5-4676-8345-bc2e92936821"), SharedMonsterAttributes.MOVEMENT_SPEED.getName(), ReposeModule.movSpeedMod, 2));
//        attributeModifierMap.put(SharedMonsterAttributes.ATTACK_SPEED, new AttributeModifier(UUID.fromString("9fe627b8-3477-4ccf-9587-87776259172f"), SharedMonsterAttributes.ATTACK_SPEED.getName(), ReposeModule.atkSpdMod, 2));
//        attributeModifierMap.put(SharedMonsterAttributes.ATTACK_DAMAGE, new AttributeModifier(UUID.fromString("ae5b003a-65f3-41f2-b104-4c71a2261d5b"), SharedMonsterAttributes.ATTACK_DAMAGE.getName(), ReposeModule.atkDmgMod, 0));
        tickHealth = setIncrement(SharedMonsterAttributes.MAX_HEALTH, ReposeModule.maxHealth, false);
        tickArmor = setIncrement(SharedMonsterAttributes.ARMOR, ReposeModule.modArmor, false);
    }

    @Override
    public void performEffect(@Nonnull EntityLivingBase entityLivingBaseIn, int amplifier) {
        int duration = entityLivingBaseIn.getActivePotionEffect(ReposeModule.reposePotion).getDuration();

        if (ReposeModule.maxHealth != 0 && duration % tickHealth == 0) {
            incrementRecover(entityLivingBaseIn, SharedMonsterAttributes.MAX_HEALTH);
        }

        if (ReposeModule.modArmor != 0 && duration % tickArmor == 0) {
            incrementRecover(entityLivingBaseIn, SharedMonsterAttributes.ARMOR);
        }
    }

    private void incrementRecover(EntityLivingBase entityIn, IAttribute attribute) {
        IAttributeInstance iAttributeInstance = entityIn.getAttributeMap().getAttributeInstance(attribute);
        AttributeModifier attributeModifier = iAttributeInstance.getModifier(attributeModifierMap.get(attribute).getID());

        if (attributeModifier == null || entityIn.getEntityWorld().isRemote) { return; }

        iAttributeInstance.removeModifier(attributeModifier);
        double changeAmount = changeAmountMap.get(attribute);
        iAttributeInstance.applyModifier(new AttributeModifier(attributeModifier.getID(), attributeModifier.getName(), attributeModifier.getAmount() + changeAmount, attributeModifier.getOperation()));
        entityIn.setHealth(entityIn.getMaxHealth());
    }

    private double setIncrement(IAttribute attribute, double amount, boolean isPercent) {

        double changeAmount;
        double sign = -Math.signum(amount);
        double changeTick = (ReposeModule.duration / amount) * 20;

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
            changeTick = 1;
            changeAmount = (1 / changeTick);
        } else {
            changeTick = 1;
            changeAmount = Math.floor(1 / changeTick);
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
