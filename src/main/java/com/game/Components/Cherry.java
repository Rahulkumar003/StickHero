package com.game.Components;

import com.game.StickHero;

public class Cherry extends GameObject {
    private int Count ;
    public boolean checkCollision(StickHero stickHero) {
        // Check for collision with the stick hero.
        return false;
    }

    public Cherry(double x, double y) {
        super(x, y);
    }

    public int getCount() {
        return Count;
    }

    public void setCount(int count) {
        Count = count;
    }

    public int getValue() {
        return 0;
    }
}