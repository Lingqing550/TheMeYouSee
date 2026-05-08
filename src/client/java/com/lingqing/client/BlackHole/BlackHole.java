package com.lingqing.client.BlackHole;

import com.mojang.blaze3d.buffers.GpuBuffer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.PostEffectPass;
import net.minecraft.client.gl.PostEffectProcessor;
import net.minecraft.client.render.Camera;
import net.minecraft.client.util.Window;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import java.util.Map;

public class BlackHole {
    public static PostEffectProcessor postEffectProcessor;
    public static Vec3d center = new Vec3d(0,0,0);
    public static Vec2f getUV(){
        return worldToScreen(center);
    };
    public static PostEffectPass postEffectPass;


     public static Vec2f worldToScreen(Vec3d coordinate){
        Camera camera = MinecraftClient.getInstance().gameRenderer.getCamera();
        Matrix4f projectionMatrix = MinecraftClient.getInstance().gameRenderer.getBasicProjectionMatrix(MinecraftClient.getInstance().options.getFov().getValue());

        Matrix4f viewMatrix = new Matrix4f();
        viewMatrix.identity();
        viewMatrix.rotate(camera.getRotation());
        Vec3d cameraPos = camera.getCameraPos();
        viewMatrix.setTranslation((float) -cameraPos.x,(float) -cameraPos.y,(float) -cameraPos.z);

        Matrix4f mvpMatrix = new Matrix4f();
        mvpMatrix.identity();
        mvpMatrix.mul(viewMatrix);
        mvpMatrix.mul(projectionMatrix);

        Vector4f clipPos = new Vector4f((float) coordinate.x,(float) coordinate.y,(float) coordinate.z,1.0f);
        clipPos = mvpMatrix.transform(clipPos);

        if(clipPos.w <= 0) return null;

        Vector3f ndcPos = new Vector3f(clipPos.x/clipPos.w,clipPos.y/clipPos.w,clipPos.z/clipPos.w);
        if(ndcPos.z <= -1 || ndcPos.z > 1) return null;
        Window window = MinecraftClient.getInstance().getWindow();
        float screenX = (ndcPos.x + 1) / 2 * window.getWidth();
        float screenY = (1 - ndcPos.y) / 2 * window.getHeight();

        if (screenX < 0 || screenX > window.getScaledWidth() ||
                screenY < 0 || screenY > window.getScaledHeight()) {
            return null;
        }
        return new Vec2f(screenX,screenY);
     };
     public static void setCenterUniform(){
         //Map<String, GpuBuffer> uniformBuffersMap = ((PostEffectPassMixin) postEffectPass).getUniformBuffers();
         //GpuBuffer buffer = uniformBuffersMap.get("center");



     }
}
