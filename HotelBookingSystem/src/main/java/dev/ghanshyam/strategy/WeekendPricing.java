package dev.ghanshyam.strategy;

import dev.ghanshyam.model.Room;

public class WeekendPricing extends RoomPricing {
    private RoomPricing roomPricing;

    public WeekendPricing(RoomPricing roomPricing) {
        this.roomPricing = roomPricing;
    }

    @Override
    public int getPrice(Room room) {

        return roomPricing.getPrice(room) + 50;
    }
}
