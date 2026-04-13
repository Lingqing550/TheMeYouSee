package com.lingqing.client.mixin;

import com.lingqing.client.commands.SetRotational;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.player.PlayerModel;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerModel.class)
public class PlayerModelMixin extends HumanoidModel<AvatarRenderState> {

    @Unique
    private int all;

    public PlayerModelMixin(ModelPart modelPart) {
        super(modelPart);
    }
    @Inject(method = "setupAnim", at = @At("TAIL"))
    public void newSetAngles(AvatarRenderState avatarRenderState, CallbackInfo ci) {
        if(!SetRotational.rotationalSwitch) return;
        if(this.all >= 360)all = 0;
        this.all += SetRotational.speed;
        this.root.yRot = (float) Math.toRadians(this.all);
    }
}
