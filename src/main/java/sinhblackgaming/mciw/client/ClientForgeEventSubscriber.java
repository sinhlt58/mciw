package sinhblackgaming.mciw.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.world.ClientWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.GL11;
import sinhblackgaming.mciw.MCIWMod;
import sinhblackgaming.mciw.capabilities.IMoreMode;
import sinhblackgaming.mciw.capabilities.MoreModeProvider;
import sinhblackgaming.mciw.init.ModParticleTypes;
import sinhblackgaming.mciw.client.particles.RainLavaParticle;

// render client only
@Mod.EventBusSubscriber(modid = MCIWMod.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ClientForgeEventSubscriber {

    public static final Logger LOGGER = LogManager.getLogger();

    @SubscribeEvent
    static public void onGuiScreen(GuiScreenEvent event){

    }

    @SubscribeEvent
    static public void onRenderGameOverlayEvent(RenderGameOverlayEvent event){
        // update block break silver fish mode
        // render count silver fish for mode block break silver fish
        if(event.getType() == RenderGameOverlayEvent.ElementType.TEXT){
            ClientWorld world = Minecraft.getInstance().world;
            IMoreMode capMoreMode = world.getCapability(MoreModeProvider.MORE_MODE_CAPABILITY).orElseThrow(IllegalStateException::new);
            if (capMoreMode.getModeBlockBreakSilverFish().isRunning()) {
                int count = capMoreMode.getModeBlockBreakSilverFish().getCountSilverSilverFish();
                GL11.glPushMatrix();
                GL11.glScalef(1.1f, 1.1f, 1.1f);
                Minecraft.getInstance().fontRenderer.drawString(Integer.toString(count), 0, 0, 0xFFFFFF);
                GL11.glPopMatrix();
            }
        }
    }

    @SubscribeEvent
    static public void onRenderWorldLastEvent(RenderWorldLastEvent event){
        // update rain lava mode
        // do nothing if the game is paused
        if (Minecraft.getInstance().isGamePaused()) return;
        ClientWorld world = Minecraft.getInstance().world;
        IMoreMode capMoreMode = world.getCapability(MoreModeProvider.MORE_MODE_CAPABILITY).orElseThrow(IllegalAccessError::new);
        if (capMoreMode.getModeRainLava().isRunning()){
            capMoreMode.getModeRainLava().drawOnEvent(event);
        }
    }
}
