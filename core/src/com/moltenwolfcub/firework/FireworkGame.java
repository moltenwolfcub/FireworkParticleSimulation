package com.moltenwolfcub.firework;

import java.util.Random;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class FireworkGame extends Game {
	public SpriteBatch batch;
    public TextureAtlas spriteTextureAtlas;
	public Random random;
	
	@Override
	public void create() {
		batch = new SpriteBatch();
        spriteTextureAtlas = new TextureAtlas("main/atlases/spriteTextureMap.atlas");
		random = new Random();

		this.setScreen(new MainScreen(this));
	}

	@Override
	public void render() {
		super.render();
	}
	
	@Override
	public void dispose() {
		batch.dispose();
	}
}
