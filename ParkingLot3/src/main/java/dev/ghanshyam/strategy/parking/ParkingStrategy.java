package dev.ghanshyam.strategy.parking;

import dev.ghanshyam.model.Slot;
import lombok.Setter;

import java.util.Queue;

public abstract class ParkingStrategy {
     protected Queue<Slot>slotQueue;

     public abstract Queue setSlotQueue();
}
