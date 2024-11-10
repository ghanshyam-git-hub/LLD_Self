package dev.ghanshyam.strategy;

import dev.ghanshyam.model.Seat;
import dev.ghanshyam.model.Show;
import dev.ghanshyam.model.ShowType;

public abstract class Pricing {
    public abstract int getPrice(Seat seat);
}
