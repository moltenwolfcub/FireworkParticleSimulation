package com.moltenwolfcub.firework.emmiters.spawnColor;

import com.badlogic.gdx.graphics.Color;
import com.moltenwolfcub.firework.util.Config;

public class RgbCycleSpawnColor {
    private Float hue;

    public RgbCycleSpawnColor() {
        this.hue = 0f;
    }

    public Color generateColor() {
        hue+=Config.HUE_CHANGE_SPEED;
        hue = hue%360;

        return Color.WHITE.fromHsv(hue,1f,1f);
    }
}
