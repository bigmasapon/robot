package com.example.robot.model.pojo;

public class Table {
    private int[][] tableCoordinator = new int[5][5];

    public Table(){
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                tableCoordinator[i][j] = 0;
            }
        }
    }

    public int getMaximumCoordinatorY() {
        return tableCoordinator.length;
    }
    public int getMaximumCoordinatorX() {
        return tableCoordinator[0].length;
    }

    public boolean isXOutOfRange(int curX) {
        return curX >= getMaximumCoordinatorX() || curX < 0;
    }

    public boolean isYOutOfRange(int curY) {
        return curY >= getMaximumCoordinatorY() || curY < 0;
    }

}
