package dev.ghanshyam.command;

import dev.ghanshyam.model.ParkingLot;

public class SetLargeSlot implements Command{
    ParkingLot parkingLot;

    public SetLargeSlot(ParkingLot parkingLot) {
        this.parkingLot = parkingLot;
    }

    @Override
    public Integer execute(Object o) {
        Integer capacity = (Integer)o;
        parkingLot.setLargeSlotcapacity(capacity);
        return capacity;
    }
}
