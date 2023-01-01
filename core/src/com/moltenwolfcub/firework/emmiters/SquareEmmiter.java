package com.moltenwolfcub.firework.emmiters;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;
import com.moltenwolfcub.firework.emmiters.spawnColor.SpawnColor;
import com.moltenwolfcub.firework.particle.Particle;
import com.moltenwolfcub.firework.util.Config;

public class SquareEmmiter implements Emmiter {
    private Random random;
    private Pool<Particle> pool;
    private SpawnColor spawnColor;
    private Integer radius;

    public SquareEmmiter(Integer radius,SpawnColor SpawnColor, Random rand, Pool<Particle> particlePool) {
        this.random = rand;
        this.pool = particlePool;
        this.spawnColor = SpawnColor;
        this.radius = radius;
    }

    @Override
    public List<Particle> createParticles(Float xPos, Float yPos) {
        List<Particle> newParticles = new ArrayList<>();
        for (Vector2 point : generateShape(new Vector2(xPos, yPos), this.radius, this.radius/10, Config.EMMITER_RANDOMNESS)) {

            newParticles.add(pool.obtain().init(point.x, point.y, 0f, 0f));//new Sprite(spriteTemplate), point.x, point.y, 0f, 0f, 4, this.spawnColor.generateColor()));
        }
        return newParticles;
    }

    private List<Vector2> generateShape(Vector2 centre, Integer radius, Integer stepSize, Integer randomOffset) {
        List<Vector2> shape = new ArrayList<>();

        Integer diametre = radius*2;

        Vector2 point = centre.add(-radius, -radius);
        for (int i = 0; i < diametre/stepSize; i++) {
            for (int j = 0; j < 5; j++) {
                shape.add(new Vector2(point).add(random.nextInt(-randomOffset, randomOffset), random.nextInt(-randomOffset, randomOffset)));
            }
            point = new Vector2(point).add(stepSize, 0);
        }
        for (int i = 0; i < diametre/stepSize; i++) {
            for (int j = 0; j < 5; j++) {
                shape.add(new Vector2(point).add(random.nextInt(-randomOffset, randomOffset), random.nextInt(-randomOffset, randomOffset)));
            }
            point = new Vector2(point).add(0, stepSize);
        }
        for (int i = 0; i < diametre/stepSize; i++) {
            for (int j = 0; j < 5; j++) {
                shape.add(new Vector2(point).add(random.nextInt(-randomOffset, randomOffset), random.nextInt(-randomOffset, randomOffset)));
            }
            point = new Vector2(point).add(-stepSize, 0);
        }
        for (int i = 0; i < diametre/stepSize; i++) {
            for (int j = 0; j < 5; j++) {
                shape.add(new Vector2(point).add(random.nextInt(-randomOffset, randomOffset), random.nextInt(-randomOffset, randomOffset)));
            }
            point = new Vector2(point).add(0, -stepSize);
        }

        return shape;
    }
    
}
