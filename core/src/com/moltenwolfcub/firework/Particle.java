package com.moltenwolfcub.firework;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool.Poolable;
import com.moltenwolfcub.firework.util.CachedSprites;
import com.moltenwolfcub.firework.util.Config;

public class Particle implements Poolable {

    public FireworkGame game;
    public Sprite sprite;
    public Vector2 pos;
    public Vector2 delta;
    public boolean used;

    public Particle() {
		this.sprite = null;
        this.game = null;
        this.pos = new Vector2();
        this.delta = new Vector2();
        this.used = false;
    }

    public Particle init(FireworkGame game, Float x, Float y, Float dx, Float dy, Integer r, Float hue) {
        this.game = game;
        this.sprite = CachedSprites.getSprite(this.game.spriteTextureAtlas, "particle");
        this.sprite.setBounds(0, 0, r, r);
        this.sprite.setColor(Color.WHITE.fromHsv(hue,1f,1f));

        this.pos = new Vector2(x, y);
        this.delta = new Vector2(dx, dy);

        this.used = true;

        return this;
    }

    @Override
    public void reset() {
        this.game = null;
        this.pos.set(0, 0);
        this.delta.set(0, 0);

        this.used = false;
    }


	public void tick() {
        this.delta.set(this.delta.x*Config.AIR_RESISTANCE, this.delta.y*Config.AIR_RESISTANCE -Config.GRAVITY);

        this.pos.add(delta);
        
    	if (this.pos.x < 0 || this.pos.x > Config.WINDOW_WIDTH) {
            this.used = false;
        }
    	if (this.pos.y < 0 || this.pos.y > Config.WINDOW_HEIGHT) {
            this.used = false;
        }
	}

	public void paint() {
        if (used) {
            sprite.setCenter(this.pos.x, this.pos.y);
            sprite.draw(game.batch);
        }
	}
    
}
