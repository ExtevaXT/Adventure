package su.external.adventure.client;

import net.minecraft.world.entity.LivingEntity;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.builder.RawAnimation;
import software.bernie.geckolib3.core.controller.AnimationController;

public class AdventureAnimations {
    public static final AnimationBuilder WALK = new AnimationBuilder().addAnimation("move.walk");
    public static final AnimationBuilder IDLE = new AnimationBuilder().addAnimation("misc.idle");
    public static final AnimationBuilder ATTACK_BITE = new AnimationBuilder().addAnimation("attack.bite");

    public static <T extends IAnimatable> AnimationController<T> genericWalkIdleController(T animatable) {
        return new AnimationController<T>(animatable, "Walk/Idle", 0, state ->{
            if (state.isMoving()){
                state.getController().setAnimation(WALK);
                return PlayState.CONTINUE;
            }
            state.getController().setAnimation(IDLE);
            return PlayState.STOP;
            //state.setAndContinue(state.isMoving() ? WALK : IDLE);
        });
    }
    public static <T extends LivingEntity & IAnimatable> AnimationController<T> genericAttackAnimation(T animatable, AnimationBuilder attackAnimation) {
        return new AnimationController<>(animatable, "Attack", 0, state -> {
            if (animatable.swinging){
                state.getController().markNeedsReload();
                state.getController().setAnimation(attackAnimation);
                animatable.swinging = false;
            }
            return PlayState.CONTINUE;
        });
    }

}
