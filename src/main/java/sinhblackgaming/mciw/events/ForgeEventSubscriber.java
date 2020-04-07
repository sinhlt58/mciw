package sinhblackgaming.mciw.events;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sinhblackgaming.mciw.MCIWMod;
import sinhblackgaming.mciw.capabilities.IMoreMode;
import sinhblackgaming.mciw.capabilities.MoreModeProvider;
import sinhblackgaming.mciw.commands.ModCommands;
import sinhblackgaming.mciw.network.MoreModeSyncHandler;

@Mod.EventBusSubscriber(modid = MCIWMod.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ForgeEventSubscriber {

    public static final Logger LOGGER = LogManager.getLogger();

    @SubscribeEvent
    public static void onServerStarting(final FMLServerStartingEvent event) {
        LOGGER.info("register mod commands");
        ModCommands.register(event.getCommandDispatcher());
    }

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
        World world = event.getPlayer().world;
        if (world.isRemote()) return;
        world.getCapability(MoreModeProvider.MORE_MODE_CAPABILITY).ifPresent((IMoreMode capMoreMode) -> {
            capMoreMode.getModeBlockBreakSilverFish().onBlockBreak(event);
            // send to clients
            MoreModeSyncHandler.sendNecessaryDataToClients(capMoreMode);
        });
    }

    @SubscribeEvent
    public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event){
        World world = event.getPlayer().world;
        if (world.isRemote()) return;
        world.getCapability(MoreModeProvider.MORE_MODE_CAPABILITY).ifPresent((IMoreMode capMoreMode) -> {
            // send to clients
            MoreModeSyncHandler.sendNecessaryDataToClients(capMoreMode);
        });
    }

    @SubscribeEvent
    public static void onAttachCapabilitiesWorld(AttachCapabilitiesEvent<World> event){
//        if (event.getObject().isRemote) return;
        // IMPORTANT: we both use for client and server worlds
        event.addCapability(new ResourceLocation(MCIWMod.MODID, "moremodes"), new MoreModeProvider());
    }
}
