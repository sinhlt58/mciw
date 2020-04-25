package sinhblackgaming.mciw.client;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.*;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sinhblackgaming.mciw.MCIWMod;
import sinhblackgaming.mciw.capabilities.*;
import sinhblackgaming.mciw.modes.ModeBlockBreakSilverFish;
import sinhblackgaming.mciw.modes.ModeRainLava;
import sinhblackgaming.mciw.modes.ModeScaleMobRandom;

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
            ModeBlockBreakSilverFish mode = capMoreMode.getMode(MoreMode.MODE_BLOCK_BREAK_SILVER_FISH);
            if (mode.isRunning()) {
                int count = mode.getCountSilverSilverFish();
                // render and scale the count text
                RenderSystem.pushMatrix();
                RenderSystem.scalef(1.1f, 1.1f, 1.1f);
                Minecraft.getInstance().fontRenderer.drawString(Integer.toString(count), 0, 0, 0xFFFFFF);
                RenderSystem.popMatrix();
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
        ModeRainLava mode = capMoreMode.getMode(MoreMode.MODE_RAIN_LAVA);
        if (mode.isRunning()){
            mode.drawOnEvent(event);
        }
    }

    @SubscribeEvent
    static public void onRenderLivingPre(RenderLivingEvent.Pre<LivingEntity, EntityModel<LivingEntity>> event){
        LivingEntity entity = event.getEntity();

        // update scale mob random mode
        if (entity instanceof MobEntity){
            MobEntity mob = (MobEntity)entity;
            World world = entity.getEntityWorld();

            world.getCapability((MoreModeProvider.MORE_MODE_CAPABILITY)).ifPresent(cap -> {
                ModeScaleMobRandom mode = cap.getMode(MoreMode.MODE_SCALE_MOB_RANDOM);
                mob.getCapability(ScaleCapabilityProvider.SCALE_CAPABILITY).ifPresent(mobCap -> {
                    if (mode.isRunning() && mobCap.isActivate()){
                        float scaleFactor = (float)mobCap.getScaleFactor();
                        // UPDATING FORGE ATTENTION: we use the func_227862_a_ function
                        // because using RenderSystem not working
                        event.getMatrixStack().func_227862_a_(scaleFactor, scaleFactor, scaleFactor);
//                        RenderSystem.pushMatrix();
//                        RenderSystem.translatef(0f, 1.5f - 1.5f*2.5f, 0f);
//                        RenderSystem.scalef(2.5f, 2.5f, 2.5f);
                    }
                });
            });
        }
    }

    @SubscribeEvent
    static public void onRenderLivingPost(RenderLivingEvent.Post<LivingEntity, EntityModel<LivingEntity>> event){

    }


}
