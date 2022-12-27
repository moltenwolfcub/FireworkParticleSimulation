package com.moltenwolfcub.firework.emmiters;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Pool;
import com.moltenwolfcub.firework.Particle;
import com.moltenwolfcub.firework.util.Config;

public class SimpleEmmiter {
    private Integer amount;
    private Random random;
    private Sprite spriteTemplate;
    private Float hue;      //TODO make a configurable colour system
    
    public SimpleEmmiter(Sprite texture, Integer spawnQuantity, Random rand) {
        this.amount = spawnQuantity;
        this.random = rand;
        this.spriteTemplate = texture;
        this.hue = 0f;
    }

    public List<Particle> createParticles(Pool<Particle> particlePool, Float xPos, Float yPos) {
        List<Particle> newParticles = new ArrayList<>();

        for (int i = 0; i < amount; i++) {
            hue+=Config.HUE_CHANGE_SPEED;
            hue = hue%360;

            Double dir = this.random.nextDouble(-180, 180);        //TODO make particle launch angle configureable
            Double power = this.random.nextDouble(0, 10);  //TODO make particle launch power configureable

            float dx = (float)(power*Math.sin(dir))+0;		//TODO configurable force applied to particles in x and y
            float dy = (float)(power*Math.cos(dir))+6;

            newParticles.add(particlePool.obtain().init(new Sprite(spriteTemplate), xPos, yPos, dx, dy, 4, hue));	//TODO configure particle radius
        }
        return newParticles;
    }
    
}
