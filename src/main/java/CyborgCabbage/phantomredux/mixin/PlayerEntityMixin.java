package CyborgCabbage.phantomredux.mixin;

import CyborgCabbage.phantomredux.util.SyncedTimeSinceRest;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.Stats;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends Entity implements SyncedTimeSinceRest {
    private static final TrackedData<Integer> SYNCED_TIME_SINCE_REST = DataTracker.registerData(PlayerEntity.class, TrackedDataHandlerRegistry.INTEGER);

    public PlayerEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Override
    public int getSyncedTimeSinceRest() {
        return this.dataTracker.get(SYNCED_TIME_SINCE_REST);
    }

    @Inject(method="initDataTracker", at=@At("TAIL"))
    private void initTracker(CallbackInfo ci) {
        this.dataTracker.startTracking(SYNCED_TIME_SINCE_REST, 1);
    }

    @Inject(method="tick", at=@At("HEAD"))
    private void tickTime(CallbackInfo ci) {
        if((Object)this instanceof ServerPlayerEntity player) {
            int j = MathHelper.clamp(player.getStatHandler().getStat(Stats.CUSTOM.getOrCreateStat(Stats.TIME_SINCE_REST)), 1, Integer.MAX_VALUE);
            this.dataTracker.set(SYNCED_TIME_SINCE_REST, j);
        }
    }
}
