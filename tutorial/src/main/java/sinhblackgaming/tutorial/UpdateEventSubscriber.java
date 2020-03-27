package sinhblackgaming.tutorial;

import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod.EventBusSubscriber(modid = TutorialMod.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class UpdateEventSubscriber {

    public static final Logger LOGGER = LogManager.getLogger();

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.player.world.isRemote){
            LOGGER.info("onPlayerTick IS REMOTE");
        } else {
            LOGGER.info("onPlayerTick NOT REMOTE");
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
