package com.lingqing.client.mixin;


import com.lingqing.client.commands.MixinPlayerEntityModel;
import com.lingqing.client.commands.SetRotational;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.render.entity.state.PlayerEntityRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(PlayerEntityModel.class)
public class PlayerEntityModelMixin extends BipedEntityModel<PlayerEntityRenderState> implements MixinPlayerEntityModel {

    public PlayerEntityModelMixin(ModelPart modelPart) {
        super(modelPart);
    }

    @Inject(method = "setAngles", at = @At("TAIL"))
    public void newSetAngles(PlayerEntityRenderState playerEntityRenderState, CallbackInfo ci) {
        MinecraftClient client = MinecraftClient.getInstance();
        if(client == null || !SetRotational.rotationalSwitch)return;
        int playerId = playerEntityRenderState.id;
        int fps = client.getCurrentFps();
        Float playerSpeed = SetRotational.playerSpeedMap.get(playerId);
        Float playerAll = SetRotational.playerAllMap.get(playerId);
        if(playerSpeed == null) return;
        if(playerAll == null ||playerAll >= 360) playerAll = (float) 0;
        playerAll += (playerSpeed / fps) + SetRotational.speed;
        if(playerSpeed == 0) playerAll = (float) 0;
        this.root.yaw += (float) Math.toRadians(playerAll);
        float decay = SetRotational.attenuation / fps;
        playerSpeed = Math.max(0,playerSpeed - decay);
        SetRotational.playerSpeedMap.put(playerId,playerSpeed);
        SetRotational.playerAllMap.put(playerId,playerAll);
//        if(this.all >= 360)all = 0;
//        this.all += (this.speed / FPS) + SetRotational.speed;
//        this.root.yaw += (float) Math.toRadians(this.all);
//        float decay = SetRotational.attenuation / FPS;
//        this.speed = Math.max(0, this.speed - decay);
    }
//    public void the_me_you_see$setSpeed(int speed) {
//       this.speed = Math.min(SetRotational.maxSpeed,Math.max(0,speed));;
//   }
//    public void the_me_you_see$addSpeed(int speed) {
//        this.speed += speed;
//        this.speed = Math.min(SetRotational.maxSpeed,Math.max(0,this.speed));
//    }
}
