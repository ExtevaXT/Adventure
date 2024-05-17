package su.external.adventure.entity.tasks;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.tslat.smartbrainlib.api.core.behaviour.custom.attack.ConditionlessAttack;
import net.tslat.smartbrainlib.object.SquareRadius;
import net.tslat.smartbrainlib.util.EntityRetrievalUtil;

import java.util.Random;

/**
 * Special attack that performs a ground-slam attack that damages nearby standing targets and throws them back.
 * @param <E> The entity
 */
public class GroundSlamAttack<E extends LivingEntity> extends ConditionlessAttack<E> {
    protected SquareRadius radius = new SquareRadius(10, 10);
    protected boolean atTarget = false;

    public GroundSlamAttack(int delayTicks) {
        super(delayTicks);

        attack(this::doSlam);
    }

    /**
     * Set the radius for the ground slam's effect
     * @param radius The coordinate radius, in blocks
     * @return this
     */
    public GroundSlamAttack<E> radius(int radius) {
        return radius(radius, radius);
    }

    /**
     * Set the radius for the ground slam's effect
     * @param xz The X/Z coordinate radius, in blocks
     * @param y The Y coordinate radius, in blocks
     * @return this
     */
    public GroundSlamAttack<E> radius(double xz, double y) {
        this.radius = new SquareRadius(xz, y);

        return this;
    }

    public GroundSlamAttack<E> slamAtTarget() {
        requiresTarget();

        this.atTarget = true;

        return this;
    }

    protected void doSlam(E entity) {
        Level level = entity.level;
        Random rand = entity.getRandom();
        Entity originEntity = this.atTarget ? this.target : entity;
        level.explode(entity,
                originEntity.getX(),
                originEntity.getY(),
                originEntity.getZ(),
                2f, Explosion.BlockInteraction.NONE);
        for (LivingEntity target : EntityRetrievalUtil.<LivingEntity>getEntities(entity.level, new AABB(originEntity.position(), originEntity.position()).inflate(radius.xzRadius(), radius.yRadius(), radius.xzRadius()), target -> target.isAlive() && target.isOnGround() && target != entity && target instanceof LivingEntity && (!(target instanceof Player pl) || !pl.getAbilities().invulnerable))) {
            entity.doHurtTarget(target);
        }
    }
}
