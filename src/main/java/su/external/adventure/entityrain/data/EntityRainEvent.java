package su.external.adventure.entityrain.data;

import java.util.List;

public class EntityRainEvent {
    public String[] message;
    public int chance, spawnRate, radius, height;
    public List<EntitySpawnOption> spawn;
    public EventConditions when;
    public boolean slow_falling, lifetimed;
    public String boss;
    public int bossCounterThreshold;
}
