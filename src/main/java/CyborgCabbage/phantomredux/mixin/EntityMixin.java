package CyborgCabbage.phantomredux.mixin;

import CyborgCabbage.phantomredux.util.Util;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.PhantomEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public class EntityMixin {
    @Inject(method="isInvisible", at = @At("HEAD"), cancellable = true)
    void a(CallbackInfoReturnable<Boolean> cir){
        Entity that = (Entity)(Object)this;
        if(that instanceof PhantomEntity phantom){
            ClientPlayerEntity player = MinecraftClient.getInstance().player;
            if(player != null && Util.phantomInteract(phantom, player) != Util.PhantomInteractState.FULL)
                cir.setReturnValue(true);
        }
    }
    @Inject(method="isInvisibleTo", at = @At("HEAD"), cancellable = true)
    void b(CallbackInfoReturnable<Boolean> cir){
        Entity that = (Entity)(Object)this;
        if(that instanceof PhantomEntity phantom){
            ClientPlayerEntity player = MinecraftClient.getInstance().player;
            if(player != null && Util.phantomInteract(phantom, player) == Util.PhantomInteractState.HALF)
                cir.setReturnValue(false);
        }
    }
}
