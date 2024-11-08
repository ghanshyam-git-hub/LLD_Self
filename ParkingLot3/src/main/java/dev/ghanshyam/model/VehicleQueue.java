package dev.ghanshyam.model;

import java.util.Queue;

public class VehicleQueue {
    public static Queue<Slot> getVehicleQueue(ParkingLot parkingLot, Vehicle vehicle){
        switch(vehicle.getVehicleType()){
            case SMALL : return parkingLot.getSmallSlotQueue();
            case MEDIUM : return parkingLot.getMediumSlotQueue();
            case LARGE : return parkingLot.getLargeSlotQueue();
            default: return null;
        }
    }
}
