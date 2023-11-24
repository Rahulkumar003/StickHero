package com.game.Components;

public abstract class GameObject {
    protected double x; // x-coordinate
    protected double y; // y-coordinate

    public GameObject() {
        this.x = 0;
        this.y = 0;
    }

    public GameObject(double x, double y) {
        this.x = x;
        this.y = y;
    }
}