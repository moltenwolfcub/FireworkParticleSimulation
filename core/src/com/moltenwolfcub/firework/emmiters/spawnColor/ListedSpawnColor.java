package com.moltenwolfcub.firework.emmiters.spawnColor;

import java.util.List;
import java.util.Random;

import com.badlogic.gdx.graphics.Color;

public class ListedSpawnColor implements SpawnColor {
    Random random;
    Color[] colors;

    public ListedSpawnColor(Random random, List<Color> colors) {
        this.random = random;
        this.colors = (Color[])colors.toArray();
    }
    public ListedSpawnColor(Random random, Color... colors) {
        this.random = random;
        this.colors = colors;
    }

    @Override
    public Color generateColor() {
        return colors[random.nextInt(colors.length)];
    }
    
}
