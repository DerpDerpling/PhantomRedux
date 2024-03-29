package CyborgCabbage.phantomredux.mixin;

import CyborgCabbage.phantomredux.util.Util;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.FlyingEntity;
import net.minecraft.entity.mob.PhantomEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PhantomEntity.class)
public abstract class PhantomEntityMixin extends FlyingEntity {
    protected PhantomEntityMixin(EntityType<? extends FlyingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;addParticle(Lnet/minecraft/particle/ParticleEffect;DDDDDD)V", shift = At.Shift.BEFORE), cancellable = true)
    private void disableParticles(CallbackInfo cir) {
        if(this.getWorld().isClient) {
            ClientPlayerEntity player = MinecraftClient.getInstance().player;
            if (player != null && Util.phantomInteract((PhantomEntity)(Object) this, player) == Util.PhantomInteractState.NONE) cir.cancel();
        }
    }
}
