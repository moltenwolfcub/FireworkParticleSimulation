package com.moltenwolfcub.firework;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
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


        for (Particle particle : activeParticles) {
            particle.tick();
            if (particle.used == false) {
                activeParticles.remove(particle);
                particlePool.free(particle);
            }
        }
    }

    @Override
    public void dispose() {
        
    }

    @Override
    public void resize(int width, int height) {
        view.update(width, height);
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {



        Vector3 mousePos = view.unproject(new Vector3(screenX, screenY, 0));
        Integer mouseX = (Integer)(int)mousePos.x;
        Integer mouseY = (Integer)(int)mousePos.y;

        activeParticles.add(particlePool.obtain().init(game, mouseX, mouseY, 0, 0));

        return true;
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
