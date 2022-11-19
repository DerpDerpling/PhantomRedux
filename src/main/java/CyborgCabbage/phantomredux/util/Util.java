package CyborgCabbage.phantomredux.util;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.Stats;
import net.minecraft.util.math.MathHelper;

public class Util {
    public static boolean phantomCanInteract(Entity entity){
        if(entity.world.isClient){
            if(entity instanceof PlayerEntity player) {
                return ((SyncedTimeSinceRest) player).getSyncedTimeSinceRest() > 30*20;//72000;
            }
        }else{
            if(entity instanceof ServerPlayerEntity player) {
                int ticksWithoutRest = player.getStatHandler().getStat(Stats.CUSTOM.getOrCreateStat(Stats.TIME_SINCE_REST));
                ticksWithoutRest = MathHelper.clamp(ticksWithoutRest, 1, Integer.MAX_VALUE);
                return ticksWithoutRest > 30*20;//72000;
            }
        }
        return true;
    }
}
