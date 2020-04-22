package sinhblackgaming.mciw.modes;

import net.minecraft.entity.CreatureEntity;
import sinhblackgaming.mciw.capabilities.GroupGoalAttack;

public class ModeMobsAttack extends Mode {
    public ModeMobsAttack(String name) {
        super(name);
    }

    public void updateMobGroupGoalAttack(CreatureEntity mob, GroupGoalAttack gg) {
        if (this.isRunning()){
            if (!gg.isActive()){
                gg.addGoals(mob);
                gg.setActive(true);
            }
        } else {
            if (gg.isActive()){
                gg.removeGoals(mob);
                gg.setActive(false);
            }
        }
    }
}
