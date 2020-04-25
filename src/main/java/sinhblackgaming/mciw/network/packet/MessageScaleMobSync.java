package sinhblackgaming.mciw.network.packet;

import net.minecraft.client.Minecraft;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.Entity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sinhblackgaming.mciw.capabilities.ScaleCapability;
import sinhblackgaming.mciw.capabilities.ScaleCapabilityProvider;

import java.util.function.Supplier;

public class MessageScaleMobSync {
    public static final Logger LOGGER = LogManager.getLogger();

    public int entityId;
    public boolean isActive;
    public double scaleFactor;

    public MessageScaleMobSync(int entityId, boolean isActive, double scaleFactor){
        this.entityId = entityId;
        this.isActive = isActive;
        this.scaleFactor = scaleFactor;
    }

    public static void encode(MessageScaleMobSync msg, PacketBuffer buf){
        buf.writeInt(msg.entityId);
        buf.writeBoolean(msg.isActive);
        buf.writeDouble(msg.scaleFactor);
    }

    public static MessageScaleMobSync decode(PacketBuffer buf){
        return new MessageScaleMobSync(
                buf.readInt(),
                buf.readBoolean(),
                buf.readDouble()
        );
    }

    public static void handle(final MessageScaleMobSync message, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            // update the scale factor for the corresponding mob with entityId
            ClientWorld world = Minecraft.getInstance().world;

            Entity entity = world.getEntityByID(message.entityId);

            if (entity instanceof CreatureEntity){
                CreatureEntity mob = (CreatureEntity) entity;

                mob.getCapability(ScaleCapabilityProvider.SCALE_CAPABILITY).ifPresent(cap -> {
                    cap.setActivate(message.isActive);
                    cap.setScaleFactor(message.scaleFactor);
                });
            }
        });

        ctx.get().setPacketHandled(true);
    }
}
