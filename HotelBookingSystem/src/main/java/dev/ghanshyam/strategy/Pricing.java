package dev.ghanshyam.strategy;

import dev.ghanshyam.model.Room;

public interface Pricing {
    int getPrice(Room room);
}
