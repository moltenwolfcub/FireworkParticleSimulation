package com.moltenwolfcub.firework;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool.Poolable;
import com.moltenwolfcub.firework.util.CachedSprites;

public class Particle implements Poolable {

    private FireworkGame game;
    public Sprite sprite;
    private Vector2 pos;
    // private Vector2 delta;
    public boolean used;

    public Particle() {
		this.sprite = null;
        this.game = null;
        this.pos = new Vector2();
        // this.delta = new Vector2();
        this.used = false;
    }

    public Particle init(FireworkGame game, Integer x, Integer y, Integer dx, Integer dy) {
        this.game = game;
        this.sprite = CachedSprites.getSprite(this.game.spriteTextureAtlas, "particle");

        this.pos = new Vector2(x, y);
        // this.delta = new Vector2(dx, dy);

        this.used = true;

        return this;
    }

    @Override
    public void reset() {
        this.game = null;
        this.pos.set(0, 0);
        // this.delta.set(0, 0);

        this.used = false;
    }


	public void tick() {
        
        sprite.setCenter(this.pos.x, this.pos.y);
	}

	public void paint() {
        if (used) {
            sprite.draw(game.batch);
        }
	}
    
}
