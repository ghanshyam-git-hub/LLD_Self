package dev.ghanshyam.strategy;

import dev.ghanshyam.model.Room;

public class RoomPricing implements Pricing{
    @Override
    public int getPrice(Room room) {
        return room.getBasePrice();
    }
}
