package dev.ghanshyam.strategy;

import dev.ghanshyam.model.Room;

public class FestivalPricing extends RoomPricing {
    private RoomPricing roomPricing;

    public FestivalPricing(RoomPricing roomPricing) {
        this.roomPricing = roomPricing;
    }

    @Override
    public int getPrice(Room room) {

        return roomPricing.getPrice(room) + 100;
    }
}
