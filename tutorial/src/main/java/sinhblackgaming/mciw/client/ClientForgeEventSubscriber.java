package sinhblackgaming.mciw.client;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sinhblackgaming.mciw.MCIWMod;
import sinhblackgaming.mciw.modes.ModesManager;

// render client only
@Mod.EventBusSubscriber(modid = MCIWMod.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ClientForgeEventSubscriber {

    public static final Logger LOGGER = LogManager.getLogger();

    @SubscribeEvent
    static public void onGuiScreen(GuiScreenEvent event){

    }

    @SubscribeEvent
    static public void onRenderWorldLastEvent(RenderWorldLastEvent event){
        ModesManager.blockBreakSilverFish.onRenderWorldLastEvent(event);
    }
}
