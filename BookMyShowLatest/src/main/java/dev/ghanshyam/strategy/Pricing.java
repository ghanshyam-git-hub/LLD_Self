package dev.ghanshyam.strategy;

import dev.ghanshyam.model.Seat;
import dev.ghanshyam.model.Show;

public interface Pricing {
    int getPrice(Seat seat);
}
