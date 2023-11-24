package com.game;
import com.game.Components.Cherry;
import com.game.Components.Platform;
import com.game.features.*;
import java.util.ArrayList;
import java.util.*;

public class Game {
    private StickHero stickHero;
    private List<Platform> platforms;
    private List<Cherry> cherries;
    private GameReviveFeature reviveFeature;
    private ScoreSystem scoreSystem;
    private Usermovement usermovement;
    private GameView gameView;
    private GameSound gameSound;

    public Game() {
        initializeGameComponents();
    }

    private void initializeGameComponents() {
        stickHero = new StickHero();
        platforms = new ArrayList<>();
        cherries = new ArrayList<>();
        reviveFeature = new GameReviveFeature();
        scoreSystem = new ScoreSystem();
        usermovement = new Usermovement(stickHero);
        gameView = new GameView();
        gameSound = new GameSound();
    }

    public void update() {
        stickHero.update(platforms, cherries);
        scoreSystem.update(stickHero, cherries);
        reviveFeature.update(stickHero, cherries);
    }

    public void render() {
        gameView.render(stickHero, platforms, cherries);
    }

}
