package com.moltenwolfcub.firework;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.moltenwolfcub.firework.util.CachedSprites;
import com.moltenwolfcub.firework.util.Config;

public class MainScreen implements Screen {
    private final FireworkGame game;
    private OrthographicCamera camera;
    private Viewport view;
    private Pool<Particle> particlePool;
    private List<Particle> activeParticles;

    //TODO this probably needs to change to a better system in the future. especially when there are different kinds of fireworks
    private Float previousHue = 0f;

    public MainScreen(FireworkGame game) {
		this.game = game;

		this.camera = new OrthographicCamera();
		this.camera.setToOrtho(false);
		this.view = new FitViewport(Config.WINDOW_WIDTH, Config.WINDOW_HEIGHT, this.camera);

        this.particlePool = new Pool<Particle>() {
            @Override
            protected Particle newObject() {
                return new Particle();
            }
        };

        this.activeParticles = new ArrayList<Particle>();
    }

    @Override
    public void render(float delta) {
        this.update();
        this.draw();
    }

    public void draw() {
        ScreenUtils.clear(0, 0, 0, 0);

        this.camera.update();
        this.game.batch.setProjectionMatrix(this.camera.combined);

        this.game.batch.begin();
        for (Particle particle : this.activeParticles) {
            particle.paint(this.game.batch);
        }
        this.game.batch.end();

    }

    public void update() {
        this.handleInput();
        this.freeDeadParticles();

        for (Particle particle : this.activeParticles) {
            particle.tick();
        }
    }
    private void handleInput() {
        if (Gdx.input.isButtonPressed(Buttons.LEFT)) {
            Vector3 mousePos = view.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
            this.spawnParticles(mousePos.x, mousePos.y, Config.PARTICLE_SPAWN_COUNT);
        }
    }
    private void spawnParticles(Float xPos, Float yPos, Integer amount) {

        for (int i = 0; i < amount; i++) {
            this.previousHue+=Config.HUE_CHANGE_SPEED;
            if (this.previousHue>=360) {
                this.previousHue=0f;
            }

            Double dir = this.game.random.nextDouble(-180, 180);        //TODO make particle launch angle configureable
            Double power = this.game.random.nextDouble(0, 10);  //TODO make particle launch power configureable

            float dx = (float)(power*Math.sin(dir))+0;
            float dy = (float)(power*Math.cos(dir))+6;		//TODO configurable force applied to particles in x and y

            this.activeParticles.add(this.particlePool.obtain().init(
                CachedSprites.getSprite(this.game.spriteTextureAtlas, "particle"),
                xPos, yPos, dx, dy, 4, this.previousHue
            ));	//TODO configure particle radius
        }
    }
    private void freeDeadParticles() {
        List<Particle> dead = new ArrayList<>();
        for (Particle particle : this.activeParticles) {
            if (particle.used == false) {
                dead.add(particle);
                this.particlePool.free(particle);
                continue;
            }
        }
        this.activeParticles.removeAll(dead);
        
    }

    @Override
    public void dispose() {
        
    }

    @Override
    public void resize(int width, int height) {
        this.view.update(width, height);
    }

    @Override
    public void pause() {
        
    }
    @Override
    public void resume() {
        
    }
    @Override
    public void hide() {
        
    }
    @Override
    public void show() {
        
    }
    
}
