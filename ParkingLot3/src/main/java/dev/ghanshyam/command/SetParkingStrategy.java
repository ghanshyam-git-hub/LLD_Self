package dev.ghanshyam.command;

import dev.ghanshyam.model.ParkingLot;
import dev.ghanshyam.strategy.parking.ParkingStrategy;
import dev.ghanshyam.strategy.pricing.PricingStrategy;

public class SetParkingStrategy implements Command{
    ParkingLot parkingLot;

    public SetParkingStrategy(ParkingLot parkingLot) {
        this.parkingLot = parkingLot;
    }

    @Override
    public ParkingStrategy execute(Object o) {
        ParkingStrategy parkingStrategy = (ParkingStrategy)o;
        parkingLot.setParkingStrategy(parkingStrategy);
        return parkingStrategy;
    }
}
