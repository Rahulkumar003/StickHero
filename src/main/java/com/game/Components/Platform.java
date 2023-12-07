package com.game.Components;

import com.game.StickHero;

import java.util.List;

import javafx.scene.shape.Rectangle;

public class Platform extends Rectangle {

    public Platform(double width, double height) {
        super(width, height);
    }

    private Platform nextPlatform;
    private List<Platform> connectedPlatforms;

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
}