package sinhblackgaming.mciw.modes;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.monster.SilverfishEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ModeBlockBreakSilverFish extends Mode {
    public ModeBlockBreakSilverFish(String name){
        super(name);
    }

    public void onBlockBreak(BlockEvent.BreakEvent event) {
        if (!event.getPlayer().world.isRemote && this.isRunning()) {
            PlayerEntity player = event.getPlayer();
            BlockPos pos = event.getPos();

            SilverfishEntity silverFish = EntityType.SILVERFISH.create(player.world);
            silverFish.setPosition(pos.getX(), pos.getY(), pos.getZ());
            player.world.addEntity(silverFish);
        }
    }

    // client only
    public void onGuiScreen(GuiScreenEvent event){

    }

    public void onRenderWorldLastEvent(RenderWorldLastEvent event){
//        if (this.isRunning()){
//            System.out.println("onGuiScreen");
//            FontRenderer font = Minecraft.getInstance().fontRenderer;
//            font.drawString("Hello World", 0, 0, 4210752);
//        }
    }
}
