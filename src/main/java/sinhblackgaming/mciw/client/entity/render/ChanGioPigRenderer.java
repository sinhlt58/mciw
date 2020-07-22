package sinhblackgaming.mciw.client.entity.render;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import sinhblackgaming.mciw.MCIWMod;
import sinhblackgaming.mciw.client.entity.model.ChanGioPigModel;
import sinhblackgaming.mciw.entities.ChanGioPigEntity;

@OnlyIn(Dist.CLIENT)
public class ChanGioPigRenderer extends MobRenderer<ChanGioPigEntity, ChanGioPigModel<ChanGioPigEntity>> {
    private static final ResourceLocation TEXTURES = new ResourceLocation(MCIWMod.MODID, "textures/entity/chan_gio_pig.png");

    public ChanGioPigRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new ChanGioPigModel<>(), 0.7F);
    }

    @Override
    public ResourceLocation getEntityTexture(ChanGioPigEntity entity) {
        return TEXTURES;
    }
}
