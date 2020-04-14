package sinhblackgaming.mciw.client.particles;

import net.minecraft.client.particle.*;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@OnlyIn(Dist.CLIENT)
public class RainLavaParticle extends RainParticle {
    private RainLavaParticle(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn) {
        super(worldIn, xCoordIn, yCoordIn, zCoordIn);
        this.particleGravity = 0.1F;
        this.maxAge = (int) 60D;
    }
    public static final Logger LOGGER = LogManager.getLogger();

    private boolean onGroundFirstTime = true;

    @OnlyIn(Dist.CLIENT)
    public static class Factory implements IParticleFactory<BasicParticleType> {
        protected final IAnimatedSprite spriteSet;

        public Factory(IAnimatedSprite sp) {this.spriteSet = sp;}

        @Override
        public Particle makeParticle(BasicParticleType typeIn, World worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            RainLavaParticle rainLavalParticle = new RainLavaParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed);
            rainLavalParticle.selectSpriteRandomly(this.spriteSet);
            return rainLavalParticle;
        }
    }
}