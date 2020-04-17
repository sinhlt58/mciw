package sinhblackgaming.mciw.modes;

import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.*;
import net.minecraft.world.World;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class ModeRandomBLockLook extends Mode {
    public static double LOOK_DISTANCE = 30D;

    public static Logger LOGGER = LogManager.getLogger();

    private ArrayList<BlockState> blockStates = new ArrayList<>();
    BlockPos previousLookPos = new BlockPos(0, 1000, 0);

    public ModeRandomBLockLook(String name) {
        super(name);
        Set<String> s = new HashSet<>();
        ForgeRegistries.BLOCKS.getValues().stream().forEach(block -> {
            BlockState bs = block.getDefaultState();
            if (bs.getMaterial() == Material.ROCK){
                blockStates.add(block.getDefaultState());
            }
        });
    }

    public void onPlayerTick(TickEvent.PlayerTickEvent event){
        World world = event.player.world;
        if (world.isRemote || !this.isRunning()) return;

        PlayerEntity player = event.player;
        Vec3d lookDir = player.getLookVec();
        Vec3d playerPos = player.getPositionVec();
        // calculate raytrace start position
        Vec3d start = new Vec3d(playerPos.getX(),
                 MathHelper.floor(playerPos.getY()) + player.getEyeHeight(),
                 playerPos.getZ());
        // calculate raytrack end position
        Vec3d end = start.add(lookDir.mul(LOOK_DISTANCE, LOOK_DISTANCE, LOOK_DISTANCE));
        // start raytrace
        RayTraceContext context = new RayTraceContext(start, end, RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.NONE, player);
        BlockRayTraceResult result = world.rayTraceBlocks(context);
        BlockPos hitPos = result.getPos();

        BlockState block = world.getBlockState(hitPos);

        if (!block.getBlock().getRegistryName().equals("minecraft:air")){
            int randI = (int)(Math.random()*blockStates.size() - 1);
            BlockState bs = blockStates.get(randI);
            world.setBlockState(hitPos, bs);
        }
    }
}
