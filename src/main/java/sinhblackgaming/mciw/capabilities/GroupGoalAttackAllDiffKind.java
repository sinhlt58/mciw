package sinhblackgaming.mciw.capabilities;

import net.minecraft.entity.CreatureEntity;
import sinhblackgaming.mciw.entities.ai.goal.NearestAttackableDiffKindTargetGoal;

public class GroupGoalAttackAllDiffKind extends GroupGoalAttack {
    public NearestAttackableDiffKindTargetGoal<CreatureEntity> nearestAllDiffKind = null;

    @Override
    public void addGoals(CreatureEntity mob) {
        super.addGoals(mob);
        if (this.nearestAllDiffKind == null){
            this.nearestAllDiffKind = new NearestAttackableDiffKindTargetGoal<>(mob, CreatureEntity.class, true);
        }
        mob.targetSelector.addGoal(2, this.nearestAllDiffKind);
    }

    @Override
    public void removeGoals(CreatureEntity mob) {
        if (this.goalOwner == mob){
            super.removeGoals(mob);
            mob.targetSelector.removeGoal(this.nearestAllDiffKind);
        }
    }
}
