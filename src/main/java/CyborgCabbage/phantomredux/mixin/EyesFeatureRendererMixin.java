package CyborgCabbage.phantomredux.mixin;

import CyborgCabbage.phantomredux.util.Util;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.EyesFeatureRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.PhantomEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EyesFeatureRenderer.class)
public abstract class EyesFeatureRendererMixin {
    @Inject(method="render", at = @At("HEAD"), cancellable = true)
    void a(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, Entity entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch, CallbackInfo ci){
        if(entity instanceof PhantomEntity phantom){
            ClientPlayerEntity player = MinecraftClient.getInstance().player;
            if(player != null && Util.phantomInteract(phantom, player) == Util.PhantomInteractState.NONE)
                ci.cancel();
        }
    }
}
