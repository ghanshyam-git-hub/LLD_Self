package dev.ghanshyam.strategy.parking;

import dev.ghanshyam.model.Slot;

import java.util.PriorityQueue;
import java.util.Queue;

public class Farthest extends ParkingStrategy{
    @Override
    public Queue setSlotQueue() {
        slotQueue = new PriorityQueue<Slot>((a, b)->b.getSlotId() - a.getSlotId());
        return slotQueue;
    }
}
