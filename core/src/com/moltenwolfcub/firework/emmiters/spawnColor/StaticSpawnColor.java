package com.moltenwolfcub.firework.emmiters.spawnColor;

import com.badlogic.gdx.graphics.Color;

public class StaticSpawnColor implements SpawnColor {
    private Color color;

    public StaticSpawnColor(Color color) {
        this.color = color;
    }

    @Override
    public Color generateColor() {
        return color;
    }
    
}
