package com.example.desktopapp.entity;

public record Users(int userId, String firstName, String lastName, String phone, String gender,
                    String shift, String username, String password, String image, String role) {

    public Users(String firstName, String lastName, String phone, String gender,
                 String shift, String username, String password, String image, String role) {
        this(0, firstName, lastName, phone, gender, shift, username, password, image, role);
    }
}
