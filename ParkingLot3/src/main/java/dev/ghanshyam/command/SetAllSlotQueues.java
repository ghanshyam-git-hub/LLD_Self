package dev.ghanshyam.command;

import dev.ghanshyam.model.ParkingLot;

public class SetAllSlotQueues implements Command{
    private ParkingLot parkingLot;

    public SetAllSlotQueues(ParkingLot parkingLot) {
        this.parkingLot = parkingLot;
    }

    @Override
    public Object execute(Object o) {
           parkingLot.makeSlotQueues();
           return true;
    }
}
