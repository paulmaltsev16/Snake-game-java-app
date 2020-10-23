package com.example.javasnakegame;

import android.util.Log;
import android.view.MotionEvent;

import com.example.javasnakegame.Models.PositionHandler;

import java.util.ArrayList;

import static com.example.javasnakegame.MySurfaceView.oneFieldSize;
import static com.example.javasnakegame.MySurfaceView.snakeLength;

public class Movements {

    private static final String TAG = "MySurface";
    private final String TOP = "TOP", RIGHT = "RIGHT", BOTTOM = "BOTTOM", LEFT = "LEFT";
    private String currentDirection = TOP;

    public Movements() {
    }

    public PositionHandler getCenterPosition() {
        int widthCenter = MainActivity.screenWidth / oneFieldSize / 2 * oneFieldSize;
        int heightCenter = MainActivity.screenHeight / oneFieldSize / 2 * oneFieldSize;
        return new PositionHandler(widthCenter, heightCenter);
    }

    public void getNewDirection(MotionEvent event) {
        if (isRightSideClicked(event)) {
            turnOnRight();
        } else {
            turnOnLeft();
        }
    }

    public boolean isRightSideClicked(MotionEvent event) {
        return event.getRawX() >= MainActivity.screenWidth / 2;
    }

    private void turnOnRight() {
        switch (currentDirection) {
            case TOP:
                currentDirection = RIGHT;
                break;
            case RIGHT:
                currentDirection = BOTTOM;
                break;
            case BOTTOM:
                currentDirection = LEFT;
                break;
            case LEFT:
                currentDirection = TOP;
                break;
        }
    }

    private void turnOnLeft() {
        switch (currentDirection) {
            case TOP:
                currentDirection = LEFT;
                break;
            case RIGHT:
                currentDirection = TOP;
                break;
            case BOTTOM:
                currentDirection = RIGHT;
                break;
            case LEFT:
                currentDirection = BOTTOM;
                break;
        }
    }

    public void updateBodyPositions() {
        for (int i = snakeLength.size() - 1; i > 0; i -= 1) {
            snakeLength.set(i, snakeLength.get(i - 1));
        }
    }

    public void updateHeadPosition() {
        switch (currentDirection) {
            case TOP:
                snakeLength.set(0, new PositionHandler(snakeLength.get(0).getLeftDistance(), snakeLength.get(0).getTopDistance() - 100));
                break;
            case RIGHT:
                snakeLength.set(0, new PositionHandler(snakeLength.get(0).getLeftDistance() + 100, snakeLength.get(0).getTopDistance()));
                break;
            case BOTTOM:
                snakeLength.set(0, new PositionHandler(snakeLength.get(0).getLeftDistance(), snakeLength.get(0).getTopDistance() + 100));
                break;
            case LEFT:
                snakeLength.set(0, new PositionHandler(snakeLength.get(0).getLeftDistance() - 100, snakeLength.get(0).getTopDistance()));
                break;
        }
    }

    public PositionHandler addNewSnakePart() {
        switch (currentDirection) {
            case TOP:
                return new PositionHandler(snakeLength.get(0).getLeftDistance(), snakeLength.get(0).getTopDistance() + oneFieldSize);
            case RIGHT:
                return new PositionHandler(snakeLength.get(0).getLeftDistance() - oneFieldSize, snakeLength.get(0).getTopDistance());
            case BOTTOM:
                return new PositionHandler(snakeLength.get(0).getLeftDistance(), snakeLength.get(0).getTopDistance() - oneFieldSize);
            case LEFT:
                return new PositionHandler(snakeLength.get(0).getLeftDistance() + oneFieldSize, snakeLength.get(0).getTopDistance());
        }
        return new PositionHandler();
    }
}
