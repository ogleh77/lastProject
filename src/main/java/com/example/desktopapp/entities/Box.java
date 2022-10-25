package com.example.desktopapp.entities;

public class Box {

    private int boxID;
    private String boxName;

    private boolean isReady;

    public Box(String boxName) {
        this.boxName = boxName;
    }

    public Box(int boxID, String boxName, boolean isReady) {
        this.boxID = boxID;
        this.boxName = boxName;
        this.isReady = isReady;
    }

    public int getBoxID() {
        return boxID;
    }

    public String getBoxName() {
        return boxName;
    }

    public boolean isReady() {
        return isReady;
    }

    public void setBoxID(int boxID) {
        this.boxID = boxID;
    }

    public void setBoxName(String boxName) {
        this.boxName = boxName;
    }

    public void setReady(boolean ready) {
        isReady = ready;
    }

    @Override
    public String toString() {
        return "Box{" +
                "boxID=" + boxID +
                ", boxName='" + boxName + '\'' +
                ", isReady=" + isReady +
                '}';
    }
}
