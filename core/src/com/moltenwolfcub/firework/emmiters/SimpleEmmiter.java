package com.moltenwolfcub.firework.emmiters;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Pool;
import com.moltenwolfcub.firework.Particle;
import com.moltenwolfcub.firework.emmiters.spawnColor.SpawnColor;

public class SimpleEmmiter implements Emmiter {
    private Integer amount;
    private Random random;
    private Sprite spriteTemplate;
    private Pool<Particle> pool;
    private SpawnColor spawnColor;
    
    public SimpleEmmiter(SpawnColor SpawnColor, Integer spawnQuantity, Sprite texture, Random rand, Pool<Particle> particlePool) {
        this.amount = spawnQuantity;
        this.random = rand;
        this.spriteTemplate = texture;
        this.pool = particlePool;
        this.spawnColor = SpawnColor;
    }

    @Override
    public List<Particle> createParticles(Float xPos, Float yPos) {
        List<Particle> newParticles = new ArrayList<>();

        for (int i = 0; i < amount; i++) {

            Double dir = this.random.nextDouble(-180, 180);        //TODO make particle launch angle configureable
            Double power = this.random.nextDouble(0, 10);  //TODO make particle launch power configureable

            float dx = (float)(power*Math.sin(dir))+0;		//TODO configurable force applied to particles in x and y
            float dy = (float)(power*Math.cos(dir))+6;

            newParticles.add(pool.obtain().init(new Sprite(spriteTemplate), xPos, yPos, dx, dy, 4, this.spawnColor.generateColor()));	//TODO configure particle radius
        }
        return newParticles;
    }
    
}
