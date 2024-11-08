package dev.ghanshyam.command;

import dev.ghanshyam.model.ParkingLot;
import dev.ghanshyam.model.Token;
import dev.ghanshyam.model.Vehicle;

public class ParkCommand implements Command{
    ParkingLot parkingLot;

    public ParkCommand(ParkingLot parkingLot) {
        this.parkingLot = parkingLot;
    }

    @Override
    public Token execute(Object o) {
        Vehicle vehicle = (Vehicle)o;
        try {
            return parkingLot.park(vehicle);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
