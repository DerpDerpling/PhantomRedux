package CyborgCabbage.phantomredux.mixin;

import CyborgCabbage.phantomredux.util.Util;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.Stats;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(targets = "net/minecraft/entity/mob/PhantomEntity$FindTargetGoal")
public class PhantomEntityFindTargetGoalMixin {
    @ModifyArgs(method= "canStart()Z", at=@At(value = "INVOKE", target = "Lnet/minecraft/world/World;getPlayers(Lnet/minecraft/entity/ai/TargetPredicate;Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/util/math/Box;)Ljava/util/List;"))
    private void inject(Args args){
        TargetPredicate tp = args.get(0);
        tp.setPredicate(Util::phantomCanInteract);
        args.set(0, tp);
    }
}
