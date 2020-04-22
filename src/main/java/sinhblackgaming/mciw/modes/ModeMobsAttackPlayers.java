package sinhblackgaming.mciw.modes;

import net.minecraft.entity.CreatureEntity;
import sinhblackgaming.mciw.capabilities.MobCapabilityProvider;

public class ModeMobsAttackPlayers extends ModeMobsAttack {
    public ModeMobsAttackPlayers(String name) {
        super(name);
    }

    public void onLivingUpdate(CreatureEntity mob){
        mob.getCapability(MobCapabilityProvider.MOB_CAPABILITY).ifPresent(cap -> {
            super.updateMobGroupGoalAttack(mob, cap.getGroupGoalAttackPlayer());
        });
    }
}
