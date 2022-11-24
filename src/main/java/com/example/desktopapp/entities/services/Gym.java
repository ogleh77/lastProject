package com.example.desktopapp.entities.services;


import com.example.desktopapp.entities.Box;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class Gym {
    private int gymId;
    private String gymName;
    private double fitnessCost;
    private double poxingCost;
    private double boxCost;
    private double maxDiscount;
    private ObservableList<Box> vipBoxes;


    public Gym(int gymId, String gymName, double fitnessCost, double poxingCost, double boxCost, double maxDiscount) {
        this.gymId = gymId;
        this.gymName = gymName;
        this.fitnessCost = fitnessCost;
        this.poxingCost = poxingCost;
        this.boxCost = boxCost;
        this.maxDiscount = maxDiscount;
       this.vipBoxes = FXCollections.observableArrayList();
    }

    public int getGymId() {
        return gymId;
    }

    public String getGymName() {
        return gymName;
    }

    public double getFitnessCost() {
        return fitnessCost;
    }

    public double getPoxingCost() {
        return poxingCost;
    }

    public double getBoxCost() {
        return boxCost;
    }

    public double getMaxDiscount() {
        return maxDiscount;
    }

    public ObservableList<Box> getVipBoxes() {
         return vipBoxes;
    }

    public void setVipBoxes(ObservableList<Box> vipBoxes) {
        this.vipBoxes = vipBoxes;
    }

    @Override
    public String toString() {
        return "Gym{" +
                "gymId=" + gymId +
                ", gymName='" + gymName + '\'' +
                ", fitnessCost=" + fitnessCost +
                ", poxingCost=" + poxingCost +
                ", boxCost=" + boxCost +
                ", maxDiscount=" + maxDiscount +
                ", vipBoxes=" + vipBoxes +
                '}';
    }
}
