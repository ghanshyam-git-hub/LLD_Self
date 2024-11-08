package dev.ghanshyam.command;

import dev.ghanshyam.model.ParkingLot;

public class SetSmallSlot implements Command{
    ParkingLot parkingLot;

    public SetSmallSlot(ParkingLot parkingLot) {
        this.parkingLot = parkingLot;
    }

    @Override
    public Integer execute(Object o) {
        Integer capacity = (Integer)o;
        parkingLot.setSmallSlotcapacity(capacity);
        return capacity;
    }
}
