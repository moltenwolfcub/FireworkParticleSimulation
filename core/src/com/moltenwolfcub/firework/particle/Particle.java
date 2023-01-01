package com.moltenwolfcub.firework.particle;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool.Poolable;
import com.moltenwolfcub.firework.FireworkGame;
import com.moltenwolfcub.firework.util.CachedSprites;
import com.moltenwolfcub.firework.util.ColoredPoint;
import com.moltenwolfcub.firework.util.Config;

public class Particle implements Poolable {
    public Sprite sprite;
    public ColoredPoint pos;
    public Vector2 delta;
    public boolean used;
    // public List<ColoredPoint> positionsToDraw;

    public Particle() {
		this.sprite = null;
        this.pos = new ColoredPoint();
        this.delta = new Vector2();
        this.used = false;
        // this.positionsToDraw = new ArrayList<>();
    }

    public Particle init(Float x, Float y, Float dx, Float dy) {
        this.sprite = CachedSprites.getSprite(FireworkGame.spriteTextureAtlas, "particle");
        this.pos.set(x, y);
        this.delta.set(dx, dy);

        this.used = true;

        return this;
    }

    @Override
    public void reset() {
        this.sprite = null;
        this.pos.set(0, 0);
        this.delta.set(0, 0);
        // this.positionsToDraw.clear();

        this.used = false;
    }

    public Boolean outOfBounds() {
        return this.pos.x < 0 || this.pos.x > Config.WINDOW_WIDTH || this.pos.y < 0 || this.pos.y > Config.WINDOW_HEIGHT;
    }


	public void tick() {
        this.delta.set(this.delta.x*Config.AIR_RESISTANCE, this.delta.y*Config.AIR_RESISTANCE -Config.GRAVITY);

        Vector2 deltaNudge = new Vector2(this.delta);
        deltaNudge.scl(Config.PARTICLE_SPACING);
        for (float i = 0; i < 1/Config.PARTICLE_SPACING; i++) {
            this.pos.add(deltaNudge);
            // this.positionsToDraw.add(new Vector2(this.pos));
        }
        
    	if (outOfBounds()) {
            this.used = false;
        }
	}

	public void paint(Batch batch) {
        if (this.used) {
            // for (int i = 0; i < this.positionsToDraw.size(); i++) {
                // Vector2 drawPos = this.positionsToDraw.get(i);
                this.sprite.setCenter(pos.x, pos.y);
                this.sprite.setColor(pos.getColor());

                // Integer size = this.positionsToDraw.size();
                // Integer inverseId = size-i;
                // Integer alphaId = Config.PARTICLE_TRAIL_LENGTH-inverseId;

                // if (alphaId < 0) {
                //     this.positionsToDraw.remove(i);
                //     continue;
                // }
                // Float alpha = 1.0f/Config.PARTICLE_TRAIL_LENGTH*alphaId;

                // this.sprite.setAlpha(alpha);
                this.sprite.draw(batch);
            // }
        }
	}
    
}
