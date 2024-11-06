package dev.ghanshyam.models;

import java.util.Random;

public class Dice {
    private int start;
    private int end;
    private Random random;


    public Dice(int start, int end) {
        this.start = start;
        this.end = end;
        this.random = new Random();
    }

    public int rollDice(){
        return random.nextInt(start,end+1);
    }
}
