package dev.ghanshyam.strategy;

import dev.ghanshyam.model.Seat;

public class BasePricing implements Pricing{
    @Override
    public int getPrice(Seat seat) {
        return seat.getBasePrice();
    }
}
