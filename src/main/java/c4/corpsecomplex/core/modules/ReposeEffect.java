package c4.corpsecomplex.core.modules;

import c4.corpsecomplex.CorpseComplex;
import com.google.common.collect.Maps;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;

import java.util.Map;

public class ReposeEffect extends Potion {

    ResourceLocation icon = new ResourceLocation(CorpseComplex.MODID, "textures/icons/repose.png");
    private Map<IAttribute, AttributeModifier> modAttributeModifiers = Maps.<IAttribute, AttributeModifier>newHashMap();
    private int maxHealth;
    private int healthTick;
    private int modArmor;
    private int armorTick;
    private int bonusToughness;
    private int toughnessTick;
    private double movSpeedMod;
    private double changeSpeed;
    private double atkSpdMod;
    private double changeAtkSpd;
    private int atkDmgMod;
    private int changeAtkDmg;

    public ReposeEffect() {
        super(true, 1);
        this.setRegistryName("repose");
        this.setPotionName("effect." + CorpseComplex.MODID + ".repose");
    }

    public void gradualRecovery(EntityPlayer player, int durationIn) {
        healthTick = (int) (durationIn / ((player.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).getAttributeValue() - ReposeModule.maxHealth)));
        armorTick = (durationIn / Math.abs(ReposeModule.modArmor));
        toughnessTick = (durationIn / ReposeModule.bonusToughness);
    }

    public void loadModifiers() {
        modAttributeModifiers.put(SharedMonsterAttributes.MAX_HEALTH, new AttributeModifier("maxhealth", ReposeModule.maxHealth, 0));
        modAttributeModifiers.put(SharedMonsterAttributes.ARMOR, new AttributeModifier("armor", ReposeModule.modArmor, 0));
        modAttributeModifiers.put(SharedMonsterAttributes.ARMOR_TOUGHNESS, new AttributeModifier("toughness", ReposeModule.bonusToughness, 0));
        modAttributeModifiers.put(SharedMonsterAttributes.MOVEMENT_SPEED, new AttributeModifier("speed", ReposeModule.movSpeedMod, 2));
        modAttributeModifiers.put(SharedMonsterAttributes.ATTACK_SPEED, new AttributeModifier("attackspeed", ReposeModule.atkSpdMod, 2));
        modAttributeModifiers.put(SharedMonsterAttributes.ATTACK_DAMAGE, new AttributeModifier("attackdamage", ReposeModule.atkDmgMod, 0));
    }

    public void updateModifier(IAttribute attribute, ) {
        modAttributeModifiers.replace()
    }

    @Override
    public void applyAttributesModifiersToEntity(EntityLivingBase entityLivingBaseIn, AbstractAttributeMap attributeMapIn, int amplifier) {
        for (Map.Entry<IAttribute, AttributeModifier> entry : this.modAttributeModifiers.entrySet())
        {
            IAttributeInstance iattributeinstance = attributeMapIn.getAttributeInstance(entry.getKey());

            if (iattributeinstance != null)
            {
                AttributeModifier attributemodifier = entry.getValue();
                iattributeinstance.removeModifier(attributemodifier);
                iattributeinstance.applyModifier(new AttributeModifier(attributemodifier.getID(), this.getName() + " " + amplifier, this.getAttributeModifierAmount(amplifier, attributemodifier), attributemodifier.getOperation()));
            }
        }
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        return true;
    }

}
