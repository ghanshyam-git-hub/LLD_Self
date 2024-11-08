package dev.ghanshyam.command;

import dev.ghanshyam.model.ParkingLot;
import dev.ghanshyam.model.Slot;
import dev.ghanshyam.model.Token;

import java.util.Map;

public class ExitCommand implements Command{
    private ParkingLot parkingLot;

    public ExitCommand(ParkingLot parkingLot) {
        this.parkingLot = parkingLot;
    }

    @Override
    public Double execute(Object o) {
        Token token = (Token)o;
        try {
            return parkingLot.exit(token);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
