package sinhblackgaming.mciw.modes;

import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.goal.LeapAtTargetGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.player.PlayerEntity;
import sinhblackgaming.mciw.capabilities.IMobCapability;
import sinhblackgaming.mciw.capabilities.MobCapabilityProvider;

public class ModeMobsAttackPlayers extends ModeMobsAttack {
    public ModeMobsAttackPlayers(String name) {
        super(name);
    }

    public void onLivingUpdate(CreatureEntity mob){
//        if (!this.isRunning()) return;
//
//        IMobCapability cap = mob.getCapability(MobCapabilityProvider.MOB_CAPABILITY).orElseThrow(RuntimeException::new);
//
//        if (!cap.isAttackPlayers()){
//            mob.goalSelector.addGoal(2, new MeleeAttackGoal(mob, 1.2D, true));
//            mob.goalSelector.addGoal(3, new LeapAtTargetGoal(mob, 0.6f));
//            mob.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(mob, PlayerEntity.class, true));
//            if (mob.getAttributes().getAttributeInstanceByName(SharedMonsterAttributes.ATTACK_DAMAGE.getName()) == null){
//                mob.getAttributes().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(3.0D);
//            }
//            cap.setAttackPlayer(true);
//        }
    }
}
