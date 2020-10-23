package com.example.javasnakegame.Models;

import android.util.Log;

import static com.example.javasnakegame.MainActivity.screenHeight;
import static com.example.javasnakegame.MainActivity.screenWidth;
import static com.example.javasnakegame.MySurfaceView.oneFieldSize;
import static com.example.javasnakegame.MySurfaceView.snakeLength;

public class PositionHandler {

    private int leftDistance;
    private int topDistance;

    public PositionHandler() {
    }

    public PositionHandler(int leftDistance, int topDistance) {
        this.leftDistance = leftDistance;
        this.topDistance = topDistance;
    }

    public boolean outOfGameField() {
        return leftDistance < 0
                || leftDistance >= screenWidth
                || topDistance >= screenHeight
                || topDistance < 0;
    }

    public int getLeftDistance() {
        return leftDistance;
    }

    public void setLeftDistance(int leftDistance) {
        this.leftDistance = leftDistance;
    }

    public int getTopDistance() {
        return topDistance;
    }

    public void setTopDistance(int topDistance) {
        this.topDistance = topDistance;
    }

    @Override
    public String toString() {
        return "PositionHandler{" +
                "leftDistance=" + leftDistance +
                ", topDistance=" + topDistance +
                '}';
    }

    public boolean stoppedByHimself() {
        for (int i = 1; i < snakeLength.size(); i += 1) {
            if (snakeLength.get(0).getLeftDistance() == snakeLength.get(i).getLeftDistance() &&
                    snakeLength.get(0).getTopDistance() == snakeLength.get(i).getTopDistance()) {
                return true;
            }
        }
        return false;
    }
}
