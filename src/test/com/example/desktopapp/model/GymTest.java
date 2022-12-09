package com.example.desktopapp.model;

import com.example.desktopapp.entity.serices.Box;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

public class GymTest {

    @Test
    public void itShouldLoadAllBoxes() throws SQLException {
        System.out.println(GymModel.fetchBoxes());
    }

    @Test
    public void itShouldMakeFalseBox() throws SQLException {

        Box box = new Box(1, "Box 1", true);
        GymModel.boxTook(box);
        System.out.println(GymModel.getCurrentGym());
    }


    @Test
    public void itShouldGymData() throws SQLException {
        System.out.println(GymModel.getCurrentGym());
    }
}
