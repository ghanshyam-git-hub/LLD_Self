package dev.ghanshyam.strategy;

import dev.ghanshyam.model.Seat;

public class EveningShowPricing extends BasePricing{
    private BasePricing basePricing;

    public EveningShowPricing(BasePricing basePricing) {
        this.basePricing = basePricing;
    }

    @Override
    public int getPrice(Seat seat) {

        return basePricing.getPrice(seat)+100;
    }
}
