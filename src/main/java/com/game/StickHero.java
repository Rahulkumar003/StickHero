package com.game;

import com.game.Components.Cherry;
import com.game.Components.GameObject;
import com.game.Components.Platform;
import com.game.Components.Stick;
import com.game.features.Movable;

import java.util.List;

public class StickHero extends GameObject implements Movable {
    private Platform currentPlatform;
    private Platform nextPlatform;
    private int score;
    private boolean isRevived;
    private Stick stick;
    private List<Cherry> cherries;

    public StickHero() {
        super();
    }

    public StickHero(double x, double y, Platform currentPlatform, Platform nextPlatform, int score, boolean isRevived, Stick stick, List<Cherry> cherries) {
        super(x, y);
        this.currentPlatform = currentPlatform;
        this.nextPlatform = nextPlatform;
        this.score = score;
        this.isRevived = isRevived;
        this.stick = stick;
        this.cherries = cherries;
    }

    public void update(List<Platform> platforms, List<Cherry> cherries) {
        // Update stick hero state based on input, collisions, etc.
        // Check for successful landing on a platform.
        checkLanding(platforms);
        // Handle other game logic...
        // Simulate stick extension (you can replace this with actual user input handling)
        extendStick(0.1);
    }

    public void collectCherry(Cherry cherry) {
        // Handle cherry collection.
        score += cherry.getValue();
        cherries.remove(cherry);
    }

    public void revive() {
        // Handle revival logic.
        if (isRevived) {
            // Implement revival logic
            isRevived = false;
        }
    }

    public void extendStick(double amount) {
        // Extend the stick by a certain amount
        stick.extend(amount);
    }

    private void checkLanding(List<Platform> platforms) {
        // Check for successful landing on a platform
        for (Platform platform : platforms) {
            if (platform.checkLanding(this)) {
                // Successful landing, update current and next platforms
                currentPlatform = platform;
                nextPlatform = findNextPlatform(platforms, platform);
                // Additional scoring or other logic can be added here
                break;
            }
        }
    }

    private Platform findNextPlatform(List<Platform> platforms, Platform currentPlatform) {

        return null;
    }


    public Platform getCurrentPlatform() {
        return currentPlatform;
    }

    public Platform getNextPlatform() {
        return nextPlatform;
    }

    public int getScore() {
        return score;
    }

    public boolean isRevived() {
        return isRevived;
    }

    public Stick getStick() {
        return stick;
    }

    public void setCurrentPlatform(Platform currentPlatform) {
        this.currentPlatform = currentPlatform;
    }

    public void setNextPlatform(Platform nextPlatform) {
        this.nextPlatform = nextPlatform;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setRevived(boolean revived) {
        isRevived = revived;
    }

    public void setStick(Stick stick) {
        this.stick = stick;
    }

    public List<Cherry> getCherries() {
        return cherries;
    }

    public void setCherries(List<Cherry> cherries) {
        this.cherries = cherries;
    }

    @Override
    public void move() {

    }
}
