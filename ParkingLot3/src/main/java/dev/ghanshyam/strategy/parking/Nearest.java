package dev.ghanshyam.strategy.parking;

import dev.ghanshyam.model.Slot;

import java.util.PriorityQueue;
import java.util.Queue;

public class Nearest extends ParkingStrategy{

    @Override
    public Queue setSlotQueue() {
        slotQueue = new PriorityQueue<Slot>((a,b)->a.getSlotId() - b.getSlotId());
        return slotQueue;
    }
}
