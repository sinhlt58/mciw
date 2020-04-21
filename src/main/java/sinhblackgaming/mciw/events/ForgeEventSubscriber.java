package sinhblackgaming.mciw.events;

import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.LeapAtTargetGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sinhblackgaming.mciw.MCIWMod;
import sinhblackgaming.mciw.capabilities.*;
import sinhblackgaming.mciw.commands.ModCommands;
import sinhblackgaming.mciw.modes.*;
import sinhblackgaming.mciw.network.MoreModeSyncHandler;

@Mod.EventBusSubscriber(modid = MCIWMod.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ForgeEventSubscriber {

    public static final Logger LOGGER = LogManager.getLogger();

    @SubscribeEvent
    public static void onServerStarting(final FMLServerStartingEvent event) {
        ModCommands.register(event.getCommandDispatcher());
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        World world = event.player.world;
        if (world.isRemote()) return;
        IMoreMode moreModeCap =  world.getCapability(MoreModeProvider.MORE_MODE_CAPABILITY).orElseThrow(RuntimeException::new);
        // update mode rain lava
        ((ModeRainLava)moreModeCap.getMode(MoreMode.MODE_RAIN_LAVA)).onPlayerTick(event);
        // update mode random block look
        ((ModeRandomBLockLook)moreModeCap.getMode(MoreMode.MODE_RANDOM_BLOCK_LOOK)).onPlayerTick(event);
    }


    @SubscribeEvent
    public static void onBlockBreak(BlockEvent.BreakEvent event) {
        World world = event.getPlayer().world;
        if (world.isRemote()) return;
        world.getCapability(MoreModeProvider.MORE_MODE_CAPABILITY).ifPresent((IMoreMode capMoreMode) -> {
            ModeBlockBreakSilverFish mode = capMoreMode.getMode(MoreMode.MODE_BLOCK_BREAK_SILVER_FISH);
            mode.onBlockBreak(event);
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
        /**
         * IMPORTANT: We use capabilities for both client and server worlds
         * and all the worlds with the same seed use the same capability object.
        **/
        World world = event.getObject();
        event.addCapability(new ResourceLocation(MCIWMod.MODID, "moremodes"), new MoreModeProvider(world.getSeed()));
    }

    @SubscribeEvent
    public static void onAttachCapabilitiesPlayer(AttachCapabilitiesEvent<Entity> event){
        /**
         * Add capabilities to players. We add for both client and server for later use in client.
         *+*/
        if (event.getObject() instanceof PlayerEntity){
            event.addCapability(new ResourceLocation(MCIWMod.MODID, "player_capability"), new PlayerCapabilityProvider());
        }

        if (event.getObject() instanceof MobEntity){
            event.addCapability(new ResourceLocation(MCIWMod.MODID, "mob_capability"), new MobCapabilityProvider());
        }
    }


    @SubscribeEvent
    public static void onJoinWorld(EntityJoinWorldEvent event){
        Entity entity = event.getEntity();
        if (entity.world.isRemote) return;

        IMoreMode cap = entity.world.getCapability(MoreModeProvider.MORE_MODE_CAPABILITY).orElseThrow(RuntimeException::new);

        ((ModeMobsAttackPlayers)cap.getMode(MoreMode.MODE_MOBS_ATTACK_PLAYERS)).onJoinWorld(event);
        ((ModeMobsAttackAll)cap.getMode(MoreMode.MODE_MOBS_ATTACK_ALL)).onJoinWorld(event);
        ((ModeMobsAttackAllDiffKind)cap.getMode(MoreMode.MODE_MOBS_ATTACK_ALL_DIFF_KIND)).onJoinWorld(event);
    }
}
