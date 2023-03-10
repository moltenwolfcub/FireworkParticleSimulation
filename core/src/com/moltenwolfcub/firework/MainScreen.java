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
import com.moltenwolfcub.firework.emmiters.Emmiter;
import com.moltenwolfcub.firework.emmiters.SquareEmmiter;
import com.moltenwolfcub.firework.emmiters.spawnColor.RgbCycleSpawnColor;
import com.moltenwolfcub.firework.particle.Particle;
import com.moltenwolfcub.firework.util.Config;

public class MainScreen implements Screen {
    private final FireworkGame game;
    private OrthographicCamera camera;
    private Viewport view;
    private Pool<Particle> particlePool;
    private List<Particle> activeParticles;

    Emmiter emmiter;

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

        // emmiter = new SimpleEmmiter(
        //     Config.PARTICLE_SPAWN_COUNT,
        //     new RgbCycleSpawnColor(),
        //     CachedSprites.getSprite(this.game.spriteTextureAtlas, "particle"),
        //     this.game.random,
        //     particlePool
        // );
        // emmiter = new SimpleEmmiter(
        //     new StaticSpawnColor(Color.SKY),
        //     Config.PARTICLE_SPAWN_COUNT,
        //     CachedSprites.getSprite(this.game.spriteTextureAtlas, "particle"),
        //     this.game.random,
        //     particlePool
        // );
        // emmiter = new SimpleEmmiter(
        //     new ShadedColorSpawn(
        //         Color.SKY,
        //         this.game.random,
        //         0.5f, 1f,
        //         0.8f, 1f
        //     ),
        //     Config.PARTICLE_SPAWN_COUNT,
        //     CachedSprites.getSprite(this.game.spriteTextureAtlas, "particle"),
        //     this.game.random,
        //     particlePool
        // );
        // emmiter = new SimpleEmmiter(
        //     new ListedSpawnColor(this.game.random, Color.SKY, Color.PURPLE, Color.GOLDENROD, Color.CORAL),
        //     Config.PARTICLE_SPAWN_COUNT,
        //     CachedSprites.getSprite(this.game.spriteTextureAtlas, "particle"),
        //     this.game.random,
        //     particlePool
        // );
        // emmiter = new SimpleEmmiter(
        //     new RandomSpawnColor(
        //         this.game.random,
        //         new StaticSpawnColor(Color.OLIVE),
        //         new ListedSpawnColor(this.game.random, Color.RED, Color.GOLD),
        //         new RgbCycleSpawnColor(),
        //         new ShadedColorSpawn(
        //             Color.BLUE,
        //             this.game.random,
        //             0.5f, 1f,
        //             0.8f, 1f
        //         )
        //     ),
        //     Config.PARTICLE_SPAWN_COUNT,
        //     CachedSprites.getSprite(this.game.spriteTextureAtlas, "particle"),
        //     this.game.random,
        //     particlePool
        // );
        // emmiter = new SimpleEmmiter(
        //     Config.PARTICLE_SPAWN_COUNT,
        //     new RandomSpawnColor(
        //         this.game.random,
        //         new ShadedColorSpawn(
        //             Color.RED,
        //             this.game.random,
        //             0.6f, 1f,
        //             0.5f, 1f
        //         ),
        //         new ShadedColorSpawn(
        //             Color.ROYAL,
        //             this.game.random,
        //             0.6f, 1f,
        //             0.5f, 1f
        //         ),
        //         new ShadedColorSpawn(
        //             Color.GOLD,
        //             this.game.random,
        //             0.6f, 1f,
        //             0.5f, 1f
        //         )
        //     ),
        //     CachedSprites.getSprite(this.game.spriteTextureAtlas, "particle"),
        //     this.game.random,
        //     particlePool
        // );
        this.emmiter = new SquareEmmiter(
            200,
            new RgbCycleSpawnColor(),
            this.game.random,
            particlePool
        );
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
            this.activeParticles.addAll(emmiter.createParticles(mousePos.x, mousePos.y));
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
