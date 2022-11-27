package com.example.desktopapp.entity.services;

public class Box {
    private int boxId;
    private String boxName;
    private boolean isReady;

    public Box(String boxName) {
        this.boxName = boxName;
    }

    public Box(int boxId, String boxName, boolean isReady) {
        this.boxId = boxId;
        this.boxName = boxName;
        this.isReady = isReady;
    }

    public int getBoxId() {
        return boxId;
    }

    public String getBoxName() {
        return boxName;
    }

    public boolean isReady() {
        return isReady;
    }

    @Override
    public String toString() {
        return boxName;
    }
}
