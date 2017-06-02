package com.ti.control;

public class Reranger {
    private double koeff = 0;

    public Reranger(double koeff) {
        this.koeff = koeff;
    }

    int rerange(int source){
        return (int) (source*koeff);
    }
}
