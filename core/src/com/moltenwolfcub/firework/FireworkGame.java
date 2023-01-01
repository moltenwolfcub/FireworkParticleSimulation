package com.moltenwolfcub.firework;

import java.util.Random;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class FireworkGame extends Game {
    public static TextureAtlas spriteTextureAtlas;

	public SpriteBatch batch;
	public Random random;
	
	@Override
	public void create() {
		spriteTextureAtlas = new TextureAtlas("main/atlases/spriteTextureMap.atlas");
		
		this.batch = new SpriteBatch();
		this.random = new Random();

		this.setScreen(new MainScreen(this));
	}

	@Override
	public void render() {
		super.render();
	}
	
	@Override
	public void dispose() {
		this.batch.dispose();
	}
}
