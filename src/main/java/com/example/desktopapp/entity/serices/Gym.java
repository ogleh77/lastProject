package com.example.desktopapp.entity.serices;

import javafx.collections.ObservableList;

public record Gym(int gymId, String gymName, double fitnessCost, double poxingCost, double boxCost,
                  double maxDiscount, String eDahabMargent, String zaadMerchant, ObservableList<Box> vipBoxes) {

    public Gym(String gymName, double fitnessCost, double poxingCost, double boxCost,
               double maxDiscount, String eDahabMargent, String zaadMerchant) {
        this(0, gymName, fitnessCost, poxingCost, boxCost, maxDiscount, eDahabMargent, zaadMerchant, null);
    }

}
