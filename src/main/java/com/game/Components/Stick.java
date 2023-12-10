package com.game.Components;

public class Stick extends GameObject {

    public Stick() {
    }

    public Stick(double x, double y) {
        super(x, y);
    }

    public void extend(double amount) {
    }
    public double getHeight() {
        return y;
    }

    public void setHeight(double v) {
        this.y=v;
    }
}
