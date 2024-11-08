package dev.ghanshyam.strategy.pricing;

public class NormalDay implements PricingStrategy{

    public double addPrice(double price)
    {
        return price;
    }
}
