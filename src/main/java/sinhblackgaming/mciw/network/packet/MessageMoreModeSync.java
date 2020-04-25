package sinhblackgaming.mciw.network.packet;

import net.minecraft.client.Minecraft;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sinhblackgaming.mciw.capabilities.IMoreMode;
import sinhblackgaming.mciw.capabilities.MoreMode;
import sinhblackgaming.mciw.capabilities.MoreModeProvider;
import sinhblackgaming.mciw.modes.ModeBlockBreakSilverFish;

import java.util.function.Supplier;

public class MessageMoreModeSync {
    public static final Logger LOGGER = LogManager.getLogger();

    public String modeBlockBreakSilverFishState;
    public int blockBreakSilverFishCount;
    public String modeRainLavaState;
    public String modeScaleMobRandomState;

    public MessageMoreModeSync(
            String modeBlockBreakSilverFishState,
            int blockBreakSilverFishCount,
            String modeRainLavaState,
            String modeScaleMobRandomState
    ){
        this.modeBlockBreakSilverFishState = modeBlockBreakSilverFishState;
        this.blockBreakSilverFishCount = blockBreakSilverFishCount;
        this.modeRainLavaState = modeRainLavaState;
        this.modeScaleMobRandomState = modeScaleMobRandomState;
    }

    public static void encode(MessageMoreModeSync pkt, PacketBuffer buf){
        buf.writeString(pkt.modeBlockBreakSilverFishState);
        buf.writeInt(pkt.blockBreakSilverFishCount);
        buf.writeString(pkt.modeRainLavaState);
        buf.writeString(pkt.modeScaleMobRandomState);
    }

    public static MessageMoreModeSync decode(PacketBuffer buf){
        return new MessageMoreModeSync(
                buf.readString(),
                buf.readInt(),
                buf.readString(),
                buf.readString()
        );
    }

    public static void handle(final MessageMoreModeSync message, Supplier<NetworkEvent.Context> ctx){
        ctx.get().enqueueWork(() -> {
           // update client capability data
            ClientWorld world = Minecraft.getInstance().world;
            world.getCapability(MoreModeProvider.MORE_MODE_CAPABILITY).ifPresent((IMoreMode capMoreMode) -> {
                // silver fish update
                ModeBlockBreakSilverFish m1 = capMoreMode.getMode(MoreMode.MODE_BLOCK_BREAK_SILVER_FISH);
                capMoreMode.setState(MoreMode.MODE_BLOCK_BREAK_SILVER_FISH, message.modeBlockBreakSilverFishState);
                m1.setCountSilverSilverFish(message.blockBreakSilverFishCount);

                // rain lava update
                capMoreMode.setState(MoreMode.MODE_RAIN_LAVA, message.modeRainLavaState);

                // scale mob random update
                capMoreMode.setState(MoreMode.MODE_SCALE_MOB_RANDOM, message.modeScaleMobRandomState);
            });
        });
        ctx.get().setPacketHandled(true);
    }
}
