package sinhblackgaming.mciw.network;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.fml.network.simple.SimpleChannel;
import sinhblackgaming.mciw.MCIWMod;
import sinhblackgaming.mciw.capabilities.IMoreMode;
import sinhblackgaming.mciw.capabilities.IScaleCapability;
import sinhblackgaming.mciw.capabilities.MoreMode;
import sinhblackgaming.mciw.modes.ModeBlockBreakSilverFish;
import sinhblackgaming.mciw.network.packet.MessageMoreModeSync;
import sinhblackgaming.mciw.network.packet.MessageScaleMobSync;

public class MoreModeSyncHandler {
    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel CHANNEL = NetworkRegistry.ChannelBuilder
            .named(new ResourceLocation(MCIWMod.MODID, "sync"))
            .clientAcceptedVersions(s -> true)
            .serverAcceptedVersions(s -> true)
            .networkProtocolVersion(() -> PROTOCOL_VERSION)
            .simpleChannel();

    public static void init() {
        // init messages here
        CHANNEL.registerMessage(1, MessageMoreModeSync.class,
                MessageMoreModeSync::encode,
                MessageMoreModeSync::decode,
                MessageMoreModeSync::handle);

        // init scale mob message
        CHANNEL.registerMessage(2, MessageScaleMobSync.class,
                MessageScaleMobSync::encode,
                MessageScaleMobSync::decode,
                MessageScaleMobSync::handle);
    }

    public static void sendMoreModeDataToClients(IMoreMode moreModeCap){
        // silver fish mode data
        ModeBlockBreakSilverFish m1 = moreModeCap.getMode(MoreMode.MODE_BLOCK_BREAK_SILVER_FISH);
        MessageMoreModeSync msg = new MessageMoreModeSync(
                m1.getState(),
                m1.getCountSilverSilverFish(),
                moreModeCap.getMode(MoreMode.MODE_RAIN_LAVA).getState(), // rain lava mode data
                moreModeCap.getMode(MoreMode.MODE_SCALE_MOB_RANDOM).getState() // scale mob random data
        );

        // send to all connected players
        CHANNEL.send(PacketDistributor.ALL.noArg(), msg);
    }

    public  static void sendScaleMobRandomToClients(int mobId, IScaleCapability cap){
        MessageScaleMobSync msg = new MessageScaleMobSync(
                mobId,
                cap.isActivate(),
                cap.getScaleFactor()
        );

        // sent to all connected players
        CHANNEL.send(PacketDistributor.ALL.noArg(), msg);
    }
}
