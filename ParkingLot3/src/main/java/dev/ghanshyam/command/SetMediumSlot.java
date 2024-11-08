package dev.ghanshyam.command;

import dev.ghanshyam.model.ParkingLot;

public class SetMediumSlot implements Command{
    ParkingLot parkingLot;

    public SetMediumSlot(ParkingLot parkingLot) {
        this.parkingLot = parkingLot;
    }

    @Override
    public Integer execute(Object o) {
        Integer capacity = (Integer)o;
        parkingLot.setMediumSlotcapacity(capacity);
        return capacity;
    }
}
