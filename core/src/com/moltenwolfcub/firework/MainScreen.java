package com.moltenwolfcub.firework;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.moltenwolfcub.firework.util.Config;

public class MainScreen extends InputAdapter implements Screen {
    private final FireworkGame game;
    private OrthographicCamera camera;
    private Viewport view;
    private Pool<Particle> particlePool;
    private List<Particle> activeParticles;

    //TODO this probably needs to change to a better system in the future. especially when there are different kinds of fireworks
    private Float previousHue = 0f;

    public MainScreen(FireworkGame game) {
		this.game = game;

		camera = new OrthographicCamera();
		camera.setToOrtho(false);
		view = new FitViewport(Config.WINDOW_WIDTH, Config.WINDOW_HEIGHT, camera);

        particlePool = new Pool<Particle>() {
            @Override
            protected Particle newObject() {
                return new Particle();
            }
        };

        activeParticles = new ArrayList<Particle>();

		Gdx.input.setInputProcessor(this);
    }

    @Override
    public void render(float delta) {
        update();
        draw();
    }

    public void draw() {
        ScreenUtils.clear(0, 0, 0, 0);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        for (Particle particle : activeParticles) {
            particle.paint();
        }
        game.batch.end();

    }

    public void update() {
        handleInput();
        freeDeadParticles();

        for (Particle particle : activeParticles) {
            particle.tick();
        }
    }
    private void handleInput() {
        if (Gdx.input.isButtonPressed(Buttons.LEFT)) {
            Vector3 mousePos = view.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
            spawnParticles(mousePos.x, mousePos.y, Config.PARTICLE_SPAWN_COUNT);
        }
    }
    private void spawnParticles(Float xPos, Float yPos, Integer amount) {

        for (int i = 0; i < amount; i++) {
            previousHue+=Config.HUE_CHANGE_SPEED;
            if (previousHue>=360) {
                previousHue=0f;
            }

            Double dir = this.game.random.nextDouble(-180, 180);        //TODO make particle launch angle configureable
            Double power = this.game.random.nextDouble(0, 10);  //TODO make particle launch power configureable

            float dx = (float)(power*Math.sin(dir))+0;
            float dy = (float)(power*Math.cos(dir))+6;		//TODO configurable force applied to particles in x and y

            activeParticles.add(particlePool.obtain().init(game, xPos, yPos, dx, dy, 4, previousHue));	//TODO configure particle radius
        }
    }
    private void freeDeadParticles() {
        List<Particle> dead = new ArrayList<>();
        for (Particle particle : activeParticles) {
            if (particle.used == false) {
                dead.add(particle);
                particlePool.free(particle);
                continue;
            }
        }
        activeParticles.removeAll(dead);
        
    }

    @Override
    public void dispose() {
        
    }

    @Override
    public void resize(int width, int height) {
        view.update(width, height);
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
