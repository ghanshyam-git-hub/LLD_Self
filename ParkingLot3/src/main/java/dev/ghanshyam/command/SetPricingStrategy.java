package dev.ghanshyam.command;

import dev.ghanshyam.model.ParkingLot;
import dev.ghanshyam.strategy.pricing.NormalDay;
import dev.ghanshyam.strategy.pricing.PricingStrategy;

public class SetPricingStrategy implements Command{
    ParkingLot parkingLot;

    public SetPricingStrategy(ParkingLot parkingLot) {
        this.parkingLot = parkingLot;
    }

    @Override
    public PricingStrategy execute(Object o) {
        PricingStrategy pricingStrategy = (PricingStrategy)o;
        parkingLot.setPricingStrategy(pricingStrategy);
        return pricingStrategy;
    }
}
