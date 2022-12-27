package com.moltenwolfcub.firework;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool.Poolable;
import com.moltenwolfcub.firework.util.Config;

public class Particle implements Poolable {
    public Sprite sprite;
    public Vector2 pos;
    public Vector2 delta;
    public boolean used;
    public List<Vector2> positionsToDraw;

    public Particle() {
		this.sprite = null;
        this.pos = new Vector2();
        this.delta = new Vector2();
        this.used = false;
        this.positionsToDraw = new ArrayList<>();
    }

    public Particle init(Sprite texture, Float x, Float y, Float dx, Float dy, Integer r, Float hue) {
        if (sprite != null) {
            System.out.println("init has been called multiple times in one run");
        }

        this.sprite = texture;
        this.sprite.setBounds(0, 0, r, r);
        this.sprite.setColor(Color.WHITE.fromHsv(hue,1f,1f));

        this.pos = new Vector2(x, y);
        this.delta = new Vector2(dx, dy);
        this.positionsToDraw.add(this.pos);

        this.used = true;

        return this;
    }

    @Override
    public void reset() {
        this.sprite = null;
        this.pos.set(0, 0);
        this.delta.set(0, 0);
        this.positionsToDraw.clear();

        this.used = false;
    }


	public void tick() {
        this.delta.set(this.delta.x*Config.AIR_RESISTANCE, this.delta.y*Config.AIR_RESISTANCE -Config.GRAVITY);

        Vector2 deltaNudge = new Vector2(this.delta);
        deltaNudge.scl(Config.PARTICLE_SPACING);
        for (float i = 0; i < 1/Config.PARTICLE_SPACING; i++) {
            this.pos.add(deltaNudge);
            this.positionsToDraw.add(new Vector2(this.pos));
        }
        
    	if (this.pos.x < 0 || this.pos.x > Config.WINDOW_WIDTH) {
            this.used = false;
        }
    	if (this.pos.y < 0 || this.pos.y > Config.WINDOW_HEIGHT) {
            this.used = false;
        }


	}

	public void paint(Batch batch) {
        if (this.used) {
            for (int i = 0; i < this.positionsToDraw.size(); i++) {
                Vector2 drawPos = this.positionsToDraw.get(i);
                this.sprite.setCenter(drawPos.x, drawPos.y);

                Integer size = this.positionsToDraw.size();
                Integer inverseId = size-i;
                Integer alphaId = Config.PARTICLE_TRAIL_LENGTH-inverseId;

                if (alphaId < 0) {
                    this.positionsToDraw.remove(i);
                    continue;
                }
                Float alpha = 1.0f/Config.PARTICLE_TRAIL_LENGTH*alphaId;

                this.sprite.setAlpha(alpha);
                this.sprite.draw(batch);
            }
        }
	}
    
}
