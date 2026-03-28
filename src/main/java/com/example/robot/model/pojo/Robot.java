package com.example.robot.model.pojo;

import com.example.robot.model.enums.Direction;


public class Robot {
    private boolean isOnTable=false;
    private int coordinatorX=0;
    private int coordinatorY=0;
    private Direction face;

    public boolean setIsOnTable(boolean isOnTable) {
        return this.isOnTable = isOnTable;
    }
    public boolean getIsOnTable() {
        return isOnTable;
    }

    public void setOnTable(boolean onTable) {
        isOnTable = onTable;
    }

    public int getCoordinatorX() {
        return coordinatorX;
    }

    public void setCoordinatorX(int coordinatorX) {
        this.coordinatorX = coordinatorX;
    }

    public int getCoordinatorY() {
        return coordinatorY;
    }

    public void setCoordinatorY(int coordinatorY) {
        this.coordinatorY = coordinatorY;
    }

    public Direction getFace() {
        return face;
    }

    public void setFace(Direction face) {
        this.face = face;
    }
}
