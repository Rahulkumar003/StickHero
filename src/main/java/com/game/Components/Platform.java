package com.game.Components;

import com.game.StickHero;
import javafx.scene.shape.Rectangle;

import java.util.List;

public class Platform extends GameObject {
    private Platform nextPlatform;
    private List<Platform> connectedPlatforms;
    private Rectangle rect;

    // Constructor and other methods...



    public boolean checkLanding(StickHero stickHero) {
        // Check if the stick hero successfully lands on this platform.
        return false;
    }

    public Platform(Platform nextPlatform, List<Platform> connectedPlatforms) {
        this.nextPlatform = nextPlatform;
        this.connectedPlatforms = connectedPlatforms;
    }

    public Platform(double x, double y, Platform nextPlatform, List<Platform> connectedPlatforms) {
        super(x, y);
        this.nextPlatform = nextPlatform;
        this.connectedPlatforms = connectedPlatforms;
    }
    public Rectangle getRect() {
        return rect;
    }
}