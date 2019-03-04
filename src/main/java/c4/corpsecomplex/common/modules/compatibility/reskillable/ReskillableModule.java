/*
 * Copyright (c) 2019. <C4>
 *
 * This Java class is distributed as a part of Corpse Complex.
 * Corpse Complex is open source and licensed under the GNU General Public License v3.
 * A copy of the license can be found here: https://www.gnu.org/licenses/gpl.text
 */

package c4.corpsecomplex.common.modules.compatibility.reskillable;

import c4.corpsecomplex.common.Module;
import c4.corpsecomplex.common.Submodule;
import codersafterdark.reskillable.api.data.PlayerData;
import codersafterdark.reskillable.api.data.PlayerDataHandler;
import codersafterdark.reskillable.api.data.PlayerSkillInfo;
import codersafterdark.reskillable.api.event.LevelUpEvent;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

import java.util.Collection;

public class ReskillableModule extends Submodule {

    static boolean resetSkills;

    public ReskillableModule(Module parentModule) {
        super(parentModule, null);
    }

    public void loadModuleConfig() {
        resetSkills = getBool("Reset Skills on Respawn", false, "Set to true to reset Reskillable skills on respawn", false);
    }

    @Override
    public boolean hasEvents() {
        return true;
    }

    @SubscribeEvent
    public void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent evt) {

        if (resetSkills && !evt.isEndConquered()) {
            EntityPlayerMP player = (EntityPlayerMP)evt.player;
            PlayerData data = PlayerDataHandler.get(player);
            Collection<PlayerSkillInfo> allSkills = data.getAllSkillInfo();

            for (PlayerSkillInfo skillInfo : allSkills) {
                int oldLevel = skillInfo.getLevel();

                if (!MinecraftForge.EVENT_BUS.post(new LevelUpEvent.Pre(player, skillInfo.skill, 1, oldLevel))) {
                    skillInfo.setLevel(1);
                    skillInfo.respec();
                    MinecraftForge.EVENT_BUS.post(new LevelUpEvent.Post(player, skillInfo.skill, 1, oldLevel));
                }
            }
            data.saveAndSync();
        }
    }
}
