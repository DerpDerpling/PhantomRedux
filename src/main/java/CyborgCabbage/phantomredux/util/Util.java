package CyborgCabbage.phantomredux.util;

import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.PhantomEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.Stats;
import net.minecraft.util.math.MathHelper;
import org.jetbrains.annotations.Nullable;

public class Util {
    public enum PhantomInteractState {
        NONE,
        HALF,
        FULL
    }
    private static final int DAY = 24000;

    public static PhantomInteractState phantomInteract(@Nullable PhantomEntity phantom, Entity entity){
        if(phantom != null && phantom.hasCustomName()) return PhantomInteractState.FULL;
        if(entity.world.isClient){
            if(entity instanceof PlayerEntity player) {
                return fromTime(((SyncedTimeSinceRest) player).getSyncedTimeSinceRest());
            }
        }else{
            if(entity instanceof ServerPlayerEntity player) {
                int ticksWithoutRest = player.getStatHandler().getStat(Stats.CUSTOM.getOrCreateStat(Stats.TIME_SINCE_REST));
                ticksWithoutRest = MathHelper.clamp(ticksWithoutRest, 1, Integer.MAX_VALUE);
                return fromTime(ticksWithoutRest);
            }
        }
        return PhantomInteractState.FULL;
    }

    private static PhantomInteractState fromTime(int time){
        /*return switch (time/20 % 3){
            case 0 -> PhantomInteractState.NONE;
            case 1 -> PhantomInteractState.HALF;
            default -> PhantomInteractState.FULL;
        };*/
        if(time > DAY*4) return PhantomInteractState.FULL;
        if(time > DAY*3) return PhantomInteractState.HALF;
        return PhantomInteractState.NONE;
    }
}
