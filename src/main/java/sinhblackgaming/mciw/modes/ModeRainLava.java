package sinhblackgaming.mciw.modes;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FireBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.Heightmap;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.TickEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sinhblackgaming.mciw.init.ModParticleTypes;

import java.util.stream.Stream;


public class ModeRainLava extends Mode {

    public static final Logger LOGGER = LogManager.getLogger();

    public int yRootRain = 110;
    public int width = 30;
    public int front = 20;
    public int rate = 25;
    public int rateCount = 0;
    public double ranYSpawnRange = 10;

    public float fireBlockChance = 0.00001f;

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
        BlockPos rootGen = new BlockPos(playerPos.getX(), yRootRain, playerPos.getZ());
        rootGen = rootGen.offset(facingDir, front);
        Direction rootGenDir = facingDir.rotateY().getOpposite();
        rootGen = rootGen.offset(rootGenDir, width/2);

        // loop for each position
        Direction widthLoopDir = facingDir.rotateY();
        Direction frontLoopDir = facingDir.getOpposite();
        for (int i=0; i<width; i++){
            for (int j=0; j<front*2; j++) {
                BlockPos p = rootGen.offset(widthLoopDir, i);
                p = p.offset(frontLoopDir, j);

                double deltaY = -ranYSpawnRange + Math.random()*ranYSpawnRange*2;

                mc.particles.addParticle(ModParticleTypes.RAIN_LAVA,
                        p.getX() + 0.5, p.getY() + deltaY, p.getZ() + 0.5,
                        0.0, 0.0, 0.0);
            }
        }
    }

    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        PlayerEntity player = event.player;
        if (player.world.isRemote || !this.isRunning()) return;

        BlockPos playerPos = player.getPosition();
        World world = player.world;
        // check if there is any block above the player
        boolean flag = world.getHeight(Heightmap.Type.MOTION_BLOCKING, playerPos).getY() > playerPos.getY();
        if (!flag && !player.isInLava()) {
            player.setInLava();
        }

        // spawn fireblocks on top of leaf and wood blocks
        Stream<BlockPos> positions = playerPos.getAllInBox(playerPos.add(-width, -width, -width), playerPos.add(width, width, width));

        positions.forEach((BlockPos pos) -> {
            this.spawnFireBlockOrNot(world, pos);
        });
    }

    public void spawnFireBlockOrNot(World world, BlockPos pos){
        BlockState blockState = world.getBlockState(pos);
        String n = blockState.getBlock().getRegistryName().toString();
        if (n.endsWith("_wood") || n.endsWith("_leaves") || n.equals("_log")){
            // == 1 is important here
            boolean flag = world.getHeight(Heightmap.Type.MOTION_BLOCKING, pos).getY() - pos.getY() == 1;
            if (flag){
                BlockPos abovePos = pos.up();
                BlockState blockStateAbove = world.getBlockState(abovePos);
                if (blockStateAbove.getBlock().getRegistryName().toString().equals("minecraft:air")){
                    if (Math.random() <= fireBlockChance){
                        world.setBlockState(abovePos, Blocks.FIRE.getDefaultState());
                    }
                }
            }
        }
    }
}
