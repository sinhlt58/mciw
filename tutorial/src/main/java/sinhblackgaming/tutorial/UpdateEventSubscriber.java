package sinhblackgaming.tutorial;

import net.minecraft.block.Block;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.monster.SilverfishEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod.EventBusSubscriber(modid = TutorialMod.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class UpdateEventSubscriber {

    public static final Logger LOGGER = LogManager.getLogger();
    public static int silverFishSpawnedCount = 0;

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        PlayerEntity player = event.player;
        if (!player.world.isRemote) {
//            BlockPos pos = player.getPosition();
//            Direction facingDirection = player.getHorizontalFacing();
//            pos = pos.offset(facingDirection).offset(facingDirection);
//            pos = pos.up();
//
////            SilverfishEntity silverFish = EntityType.SILVERFISH.create(player.world);
////            silverFish.setPosition(pos.getX(), pos.getY(), pos.getZ());
////
////            player.world.addEntity(silverFish);
//
//            LOGGER.info("Player pos: " + player.getPosition());
//            LOGGER.info("Facing direction: " + facingDirection);
//            LOGGER.info("Offset pos: " + pos);
        }
//        if (event.player.world.isRemote){
//            LOGGER.info("onPlayerTick IS REMOTE");
//        } else {
//            LOGGER.info("onPlayerTick NOT REMOTE");
//        }
    }

    @SubscribeEvent
    public static void onBlockBreak(BlockEvent.BreakEvent event) {
        if (!event.getPlayer().world.isRemote) {
            PlayerEntity player = event.getPlayer();
            BlockPos pos = event.getPos();

            SilverfishEntity silverFish = EntityType.SILVERFISH.create(player.world);
            silverFish.setPosition(pos.getX(), pos.getY(), pos.getZ());
            player.world.addEntity(silverFish);

//            silverFishSpawnedCount += 1;
        }
    }

//    @SubscribeEvent
//    public static void onLivingUpdate(LivingEvent.LivingUpdateEvent event) {
//        if (event.getEntity().world.isRemote){
//            LOGGER.info("IS REMOTE");
//        } else {
//            LOGGER.info("NOT REMOTE");
//        }
//    }
}
