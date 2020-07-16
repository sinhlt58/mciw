package sinhblackgaming.mciw.events;

import net.minecraft.entity.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.extensions.IForgeDimension;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerSleepInBedEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.event.world.GetCollisionBoxesEvent;
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
        // update mode mobs appear near player
        ((ModeMobsAppearNearPlayers)moreModeCap.getMode(MoreMode.MODE_MOBS_APPEAR_NEAR_PLAYERS)).onPlayerTick(event);
    }

    @SubscribeEvent
    public static void onLivingUpdate(LivingEvent.LivingUpdateEvent event){
        LivingEntity entity = event.getEntityLiving();
        World world = entity.world;
        if (world.isRemote) return;

        if (entity instanceof CreatureEntity){
            CreatureEntity mob = (CreatureEntity) entity;
            world.getCapability(MoreModeProvider.MORE_MODE_CAPABILITY).ifPresent(cap -> {
                // update mobs attack modes
                ((ModeMobsAttackAll)cap.getMode(MoreMode.MODE_MOBS_ATTACK_ALL)).onLivingUpdate(mob);
                ((ModeMobsAttackAllDiffKind)cap.getMode(MoreMode.MODE_MOBS_ATTACK_ALL_DIFF_KIND)).onLivingUpdate(mob);
                ((ModeMobsAttackPlayers)cap.getMode(MoreMode.MODE_MOBS_ATTACK_PLAYERS)).onLivingUpdate(mob);
            });
        }

        if (entity instanceof MobEntity){
            // update scale mode
            MobEntity mob = (MobEntity) entity;
            world.getCapability(MoreModeProvider.MORE_MODE_CAPABILITY).ifPresent(cap -> {
                ((ModeScaleMobRandom)cap.getMode(MoreMode.MODE_SCALE_MOB_RANDOM)).onLivingUpdate(mob);
            });

        }
    }

    @SubscribeEvent
    public static void onLivingDeathEvent(LivingDeathEvent event){
        LivingEntity entity = event.getEntityLiving();
        World world = entity.world;
        if (world.isRemote) return;

        if (entity instanceof MobEntity){
            MobEntity mob = (MobEntity) entity;
            world.getCapability(MoreModeProvider.MORE_MODE_CAPABILITY).ifPresent(cap -> {
                // update mode mobs die explosion
                ((ModeMobsDieExplosion)cap.getMode(MoreMode.MODE_MOBS_DIE_EXPLOSION)).onMobDeath(mob);
                // update mode mobs die multiply
                ((ModeMobsDieMultiply)cap.getMode(MoreMode.MODE_MOBS_DIE_MULTIPLY)).onMobDeath(mob);
            });
        }
    }

    @SubscribeEvent
    public static void onBlockBreak(BlockEvent.BreakEvent event) {
        World world = event.getPlayer().world;
        if (world.isRemote()) return;
        world.getCapability(MoreModeProvider.MORE_MODE_CAPABILITY).ifPresent((IMoreMode capMoreMode) -> {
            // update block break silver fish mode
            ModeBlockBreakSilverFish mode = capMoreMode.getMode(MoreMode.MODE_BLOCK_BREAK_SILVER_FISH);
            mode.onBlockBreak(event);

            // update block break multiple mode
            ModeBlockBreakMultiple mode2 = capMoreMode.getMode(MoreMode.MODE_BLOCK_BREAK_MULTIPLE);
            mode2.onBlockBreak(event);

            // update block break switch
            ModeBlockBreakSwitch mode3 = capMoreMode.getMode(MoreMode.MODE_BLOCK_BREAK_SWITCH);
            mode3.onBlockBreak(event);

            // update block break random drop
            ModeBlockBreakRandomDrop mode4 = capMoreMode.getMode(MoreMode.MODE_BLOCK_BREAK_RANDOM_DROP);
            mode4.onBlockBreak(event);

            // update block break random spawn mob
            ModeBlockBreakRandomSpawnMob mode5 = capMoreMode.getMode(MoreMode.MODE_BLOCK_BREAK_RANDOM_SPAWN_MOB);
            mode5.onBlockBreak(event);

            // send to clients
            MoreModeSyncHandler.sendMoreModeDataToClients(capMoreMode);
        });
    }

    @SubscribeEvent
    public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event){
        World world = event.getPlayer().world;
        if (world.isRemote()) return;
        world.getCapability(MoreModeProvider.MORE_MODE_CAPABILITY).ifPresent((IMoreMode capMoreMode) -> {
            // send to clients
            MoreModeSyncHandler.sendMoreModeDataToClients(capMoreMode);

            // send all creature scale data to clients for rendering
            ((ServerWorld) world).getEntities().forEach((entity) -> {
                if (entity instanceof CreatureEntity){
                    entity.getCapability(ScaleCapabilityProvider.SCALE_CAPABILITY).ifPresent(cap -> {
                        MoreModeSyncHandler.sendScaleMobRandomToClients(entity.getEntityId(), cap);
                    });
                }
            });
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

        if (event.getObject() instanceof CreatureEntity){
            // For mobs attack modes
            event.addCapability(new ResourceLocation(MCIWMod.MODID, "mob_capability"), new MobCapabilityProvider());

            // For scale mode
            event.addCapability(new ResourceLocation(MCIWMod.MODID, "scale_capability"), new ScaleCapabilityProvider());
        }
    }

    @SubscribeEvent
    public static void onJoinWorld(EntityJoinWorldEvent event){

    }

    @SubscribeEvent
    public static void onPlayerSleepInBedEvent(PlayerSleepInBedEvent event){

    }
}
