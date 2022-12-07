package com.example.desktopapp.entity.serices;

public record Box(int boxId, String boxName, boolean ready) {

    public Box(String boxName) {
        this(0, boxName, false);
    }
}
