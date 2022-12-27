package com.moltenwolfcub.firework.emmiters.spawnColor;

import java.util.Random;

import com.badlogic.gdx.graphics.Color;

public class ShadedColorSpawn implements SpawnColor {
    private float[] color;
    private Random random;
    private Float saturationLower;
    private Float saturationUpper;
    private Float valueLower;
    private Float valueUpper;

    public ShadedColorSpawn(Color col, Random rand, Float saturationLower, Float saturationUpper, Float valueLower, Float valueUpper) {
        this.color = new float[3];
        this.color = col.toHsv(this.color);
        this.random = rand;

        if (saturationUpper < saturationLower) {
            throw new IllegalArgumentException("The lower bounds of the color's saturation cannot be bigger than upper bounds.");
        }
        if (valueUpper < valueLower) {
            throw new IllegalArgumentException("The lower bounds of the color's value cannot be bigger than upper bounds.");
        }
        this.saturationLower = saturationLower;
        this.saturationUpper = saturationUpper;
        this.valueLower = valueLower;
        this.valueUpper = valueUpper;
    }
    public ShadedColorSpawn(Color col, Random rand) {
        this(col, rand, 0.2f, 1.0f, 0.2f, 1.0f);
    }

    @Override
    public Color generateColor() {
        Float hue = color[0];
        Float saturation;
        if (saturationLower<0 || saturationUpper<0) {
            saturation = color[1];
        } else {
            saturation = random.nextFloat(saturationLower, saturationUpper);
        }
        Float value;
        if (valueLower<0 || valueUpper<0) {
            value = color[1];
        } else {
            value = random.nextFloat(valueLower, valueUpper);
        }

        return Color.WHITE.fromHsv(hue, saturation, value);
    }
}
