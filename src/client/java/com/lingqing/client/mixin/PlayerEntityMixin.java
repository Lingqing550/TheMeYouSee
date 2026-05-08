package com.lingqing.client.mixin;

import com.lingqing.client.commands.SetRotational;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin {

    @Inject(method = "attack",at = @At("TAIL"))
    public void newAttack(Entity target, CallbackInfo ci){
        MinecraftClient client = MinecraftClient.getInstance();
        if(!(target instanceof PlayerEntity))return;
        if (((PlayerEntity) (Object) this) != client.player) return;
        Integer playerId = target.getId();
        SetRotational.playerSpeedMap.compute(playerId, (k, playerSpeed) -> playerSpeed == null ? SetRotational.attenuation : playerSpeed + SetRotational.attenuation);
//        EntityRenderManager entityRenderManager = client.getEntityRenderDispatcher();
//        PlayerEntityRenderer renderer = (PlayerEntityRenderer) entityRenderManager.getRenderer(target);
//        EntityModel model = renderer.getModel();
//        ((MixinPlayerEntityModel) model).the_me_you_see$addSpeed(36);
    }
}
