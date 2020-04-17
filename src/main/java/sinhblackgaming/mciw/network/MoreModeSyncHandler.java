package sinhblackgaming.mciw.network;

import com.google.common.base.MoreObjects;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.fml.network.simple.SimpleChannel;
import sinhblackgaming.mciw.MCIWMod;
import sinhblackgaming.mciw.capabilities.IMoreMode;
import sinhblackgaming.mciw.capabilities.MoreMode;
import sinhblackgaming.mciw.modes.ModeBlockBreakSilverFish;
import sinhblackgaming.mciw.network.packet.MessageMoreModeSync;

public class MoreModeSyncHandler {
    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel CHANNEL = NetworkRegistry.ChannelBuilder
            .named(new ResourceLocation(MCIWMod.MODID, "sync"))
            .clientAcceptedVersions(s -> true)
            .serverAcceptedVersions(s -> true)
            .networkProtocolVersion(() -> PROTOCOL_VERSION)
            .simpleChannel();

    public static void init() {
        CHANNEL.registerMessage(1, MessageMoreModeSync.class,
                MessageMoreModeSync::encode,
                MessageMoreModeSync::decode,
                MessageMoreModeSync::handle);
    }

    public static void sendNecessaryDataToClients(IMoreMode moreModeCap){
        ModeBlockBreakSilverFish m1 = moreModeCap.getMode(MoreMode.MODE_BLOCK_BREAK_SILVER_FISH);

        MessageMoreModeSync msg = new MessageMoreModeSync(
                m1.getState(),
                m1.getCountSilverSilverFish(),
                moreModeCap.getMode(MoreMode.MODE_RAIN_LAVA).getState()
        );
        // send to all connected players
        CHANNEL.send(PacketDistributor.ALL.noArg(), msg);
    }
}
