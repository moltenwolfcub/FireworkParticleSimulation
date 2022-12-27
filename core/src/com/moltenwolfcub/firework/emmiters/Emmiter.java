package com.moltenwolfcub.firework.emmiters;

import java.util.List;

import com.moltenwolfcub.firework.Particle;

public interface Emmiter {

    /**
     * Should return a list of newly generated particles
     * 
     * @param xPos      the spawn orgin X position
     * @param yPos      the spawn origin Y position
     * @return          new particle list
     */
    public List<Particle> createParticles(Float xPos, Float yPos);
}
