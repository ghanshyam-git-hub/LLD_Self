package dev.ghanshyam.strategy.pricing;

public class SpecialDay implements PricingStrategy{
    @Override
    public double addPrice(double price)
    {
        return price+(price*.25);
    }
}
