package sinhblackgaming.mciw.modes;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.Heightmap;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.TickEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sinhblackgaming.mciw.init.ModParticleTypes;


public class ModeRainLava extends Mode {

    public static final Logger LOGGER = LogManager.getLogger();

    public int yFromPlayer = 30;
    public int width = 30;
    public int front = 20;
    public int rate = 25;
    public int rateCount = 0;

    public ModeRainLava(String name) {
        super(name);
    }

    public void drawOnEvent(RenderWorldLastEvent event) {
        rateCount++;
        if (rateCount != rate){
            return;
        }
        rateCount = 0;
        Minecraft mc = Minecraft.getInstance();
        BlockPos playerPos =  mc.player.getPosition();

        // get facing direction
        Direction facingDir = mc.player.getHorizontalFacing();

        // calculate top-left gen direction
        BlockPos rootGen = playerPos.offset(facingDir, front);
        rootGen = rootGen.up(yFromPlayer);
        Direction rootGenDir = facingDir.rotateY().getOpposite();
        rootGen = rootGen.offset(rootGenDir, width/2);

        // loop for each position
        Direction widthLoopDir = facingDir.rotateY();
        Direction frontLoopDir = facingDir.getOpposite();
        for (int i=0; i<width; i++){
            for (int j=0; j<front*2; j++) {
                BlockPos p = rootGen.offset(widthLoopDir, i);
                p = p.offset(frontLoopDir, j);

                mc.particles.addParticle(ModParticleTypes.RAIN_LAVA,
                        p.getX() + 0.5, p.getY(), p.getZ() + 0.5,
                        0.0, 0.0, 0.0);
            }
        }
    }

    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        PlayerEntity player = event.player;
        if (player.world.isRemote || !this.isRunning()) return;

        BlockPos playerPos = player.getPosition();
        World world = player.world;
        boolean flag = world.getHeight(Heightmap.Type.MOTION_BLOCKING, playerPos).getY() > playerPos.getY();
        if (!flag && !player.isInLava()) {
            player.setInLava();
        }
    }
}
