package dev.ghanshyam.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
public class Token {
    private static int globalTokenId = 1;
    private  int tokenId;

    private Date inTime;
    @Setter
    private Date outTime;
    private Vehicle vehicle;
    @Setter
    private Slot slot;
    @Setter
    private double amount;

    public Token(Date inTime, Vehicle vehicle) {
        tokenId = globalTokenId++;
        this.inTime = inTime;
        this.vehicle = vehicle;
    }
}
