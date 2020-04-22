package sinhblackgaming.mciw.capabilities;

import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;

public class GroupGoalAttackAll extends GroupGoalAttack {
    public NearestAttackableTargetGoal<CreatureEntity> nearestAll = null;

    @Override
    public void addGoals(CreatureEntity mob) {
        super.addGoals(mob);
        if (this.nearestAll == null){
            this.nearestAll = new NearestAttackableTargetGoal<>(mob, CreatureEntity.class, true);
        }
        mob.targetSelector.addGoal(2, this.nearestAll);
    }

    @Override
    public void removeGoals(CreatureEntity mob) {
        if (this.goalOwner == mob){
            super.removeGoals(mob);
            mob.targetSelector.removeGoal(this.nearestAll);
        }
    }
}
