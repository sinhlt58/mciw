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
        this.particleGravity = 0.2F;
        this.maxAge = (int) 80D;
        this.setSize(0.01f, 0.01f);
    }
    public static final Logger LOGGER = LogManager.getLogger();

    // UPDATE FORGE ATTENTION: code in this function is from the parent function
    public void tick() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        if (this.maxAge-- <= 0) {
            this.setExpired();
        } else {
            this.motionY -= (double) this.particleGravity;
            this.move(this.motionX, this.motionY, this.motionZ);
            this.motionX *= (double) 0.98F;
            this.motionY *= (double) 0.98F;
            this.motionZ *= (double) 0.98F;
            if (this.onGround) {
                this.motionY = 0.0;

                this.motionX *= (double) 0.7F;
                this.motionZ *= (double) 0.7F;
            }

            BlockPos blockpos = new BlockPos(this.posX, this.posY, this.posZ);
            double d0 = Math.max(this.world.getBlockState(blockpos).getCollisionShape(this.world, blockpos).max(Direction.Axis.Y, this.posX - (double) blockpos.getX(), this.posZ - (double) blockpos.getZ()), (double) this.world.getFluidState(blockpos).func_215679_a(this.world, blockpos));
            if (d0 > 0.0D && this.posY < (double) blockpos.getY() + d0) {
                this.setExpired();
            }
        }
    }

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
