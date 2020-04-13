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

import java.util.function.Supplier;

public class MessageMoreModeSync {
    public static final Logger LOGGER = LogManager.getLogger();

    public String modeBlockBreakSilverFishState;
    public int blockBreakSilverFishCount;
    public String modeRainLavaState;

    public MessageMoreModeSync(
            String modeBlockBreakSilverFishState,
            int blockBreakSilverFishCount,
            String modeRainLavaState
    ){
        this.modeBlockBreakSilverFishState = modeBlockBreakSilverFishState;
        this.blockBreakSilverFishCount = blockBreakSilverFishCount;
        this.modeRainLavaState = modeRainLavaState;
    }

    public static void encode(MessageMoreModeSync pkt, PacketBuffer buf){
        buf.writeString(pkt.modeBlockBreakSilverFishState);
        buf.writeInt(pkt.blockBreakSilverFishCount);
        buf.writeString(pkt.modeRainLavaState);
    }

    public static MessageMoreModeSync decode(PacketBuffer buf){
        return new MessageMoreModeSync(
                buf.readString(),
                buf.readInt(),
                buf.readString()
        );
    }

    public static void handle(final MessageMoreModeSync message, Supplier<NetworkEvent.Context> ctx){
        ctx.get().enqueueWork(() -> {
           // update client capability data
            ClientWorld world = Minecraft.getInstance().world;
            world.getCapability(MoreModeProvider.MORE_MODE_CAPABILITY).ifPresent((IMoreMode capMoreMode) -> {
                capMoreMode.setState(MoreMode.MODE_BLOCK_BREAK_SILVER_FISH, message.modeBlockBreakSilverFishState);
                capMoreMode.getModeBlockBreakSilverFish().setCountSilverSilverFish(message.blockBreakSilverFishCount);
                capMoreMode.setState(MoreMode.MODE_RAIN_LAVA, message.modeRainLavaState);
            });
        });
        ctx.get().setPacketHandled(true);
    }
}
