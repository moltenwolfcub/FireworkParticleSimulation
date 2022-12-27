package com.moltenwolfcub.firework.emmiters.spawnColor;

import java.util.List;
import java.util.Random;

import com.badlogic.gdx.graphics.Color;

public class RandomSpawnColor implements SpawnColor {
    Random random;
    SpawnColor[] spawners;

    public RandomSpawnColor(Random random, List<SpawnColor> spawners) {
        this.random = random;
        this.spawners = (SpawnColor[])spawners.toArray();
    }
    public RandomSpawnColor(Random random, SpawnColor... spawners) {
        this.random = random;
        this.spawners = spawners;
    }

    @Override
    public Color generateColor() {
        return spawners[random.nextInt(spawners.length)].generateColor();
    }
    
}
