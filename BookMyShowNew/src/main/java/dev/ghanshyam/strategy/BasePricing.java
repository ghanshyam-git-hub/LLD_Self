package dev.ghanshyam.strategy;

import dev.ghanshyam.model.Seat;
/*
Decorator Pattern
 */
public class BasePricing extends Pricing {
    @Override
    public int getPrice(Seat seat) {
         return seat.getBasePrice();
    }
}
