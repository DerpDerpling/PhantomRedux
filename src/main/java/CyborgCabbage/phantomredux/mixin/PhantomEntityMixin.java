package CyborgCabbage.phantomredux.mixin;

import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.Stats;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(targets = "net/minecraft/entity/mob/PhantomEntity$FindTargetGoal")
public class PhantomEntityMixin {
    @ModifyArgs(method= "canStart()Z", at=@At(value = "INVOKE", target = "Lnet/minecraft/world/World;getPlayers(Lnet/minecraft/entity/ai/TargetPredicate;Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/util/math/Box;)Ljava/util/List;"))
    private void inject(Args args){
        TargetPredicate tp = args.get(0);
        tp.setPredicate(livingEntity->{
            if(livingEntity instanceof ServerPlayerEntity player) {
                int ticksWithoutRest = player.getStatHandler().getStat(Stats.CUSTOM.getOrCreateStat(Stats.TIME_SINCE_REST));
                ticksWithoutRest = MathHelper.clamp(ticksWithoutRest, 1, Integer.MAX_VALUE);
                return ticksWithoutRest > 72000;
            }
            return false;
        });
        args.set(0, tp);
    }
}
