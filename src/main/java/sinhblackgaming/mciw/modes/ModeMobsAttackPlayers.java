package sinhblackgaming.mciw.modes;

import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.goal.LeapAtTargetGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;

public class ModeMobsAttackPlayers extends Mode {
    public ModeMobsAttackPlayers(String name) {
        super(name);
    }

    public void onJoinWorld(EntityJoinWorldEvent event){
        Entity entity = event.getEntity();
        if (entity.world.isRemote || !(entity instanceof CreatureEntity) ||
                !this.isRunning()) return;

        CreatureEntity mob = (CreatureEntity) entity;
        mob.goalSelector.addGoal(2, new MeleeAttackGoal(mob, 1.2D, true));
        mob.goalSelector.addGoal(3, new LeapAtTargetGoal(mob, 0.6f));
        mob.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(mob, PlayerEntity.class, true));
        if (mob.getAttributes().getAttributeInstanceByName(SharedMonsterAttributes.ATTACK_DAMAGE.getName()) == null){
            mob.getAttributes().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(3.0D);
        }
    }
}
