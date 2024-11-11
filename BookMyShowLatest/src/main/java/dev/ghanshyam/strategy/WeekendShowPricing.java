package dev.ghanshyam.strategy;

import dev.ghanshyam.model.Seat;

public class WeekendShowPricing extends BasePricing{
    private BasePricing basePricing;

    public WeekendShowPricing(BasePricing basePricing) {
        this.basePricing = basePricing;
    }

    @Override
    public int getPrice(Seat seat) {
        return basePricing.getPrice(seat)+200;
    }
}
