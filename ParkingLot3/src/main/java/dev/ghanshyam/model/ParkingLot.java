package dev.ghanshyam.model;

import dev.ghanshyam.strategy.parking.ParkingStrategy;
import dev.ghanshyam.strategy.pricing.PricingStrategy;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;


@Getter
public class ParkingLot {
    @Setter
    private int smallSlotcapacity;
    @Setter
    private int mediumSlotcapacity;
    @Setter
    private int largeSlotcapacity;

    private Queue<Slot>smallSlotQueue;
    private Queue<Slot>mediumSlotQueue;
    private Queue<Slot>largeSlotQueue;

    @Setter
    private PricingStrategy pricingStrategy;
    @Setter
    private ParkingStrategy parkingStrategy;

    private Map<Integer,Slot>tokenSlotMap = new HashMap<>(); // token id vs Slot
    private Map<Integer,Token>tokenRepo = new HashMap<>(); // Repo of tokens

    public ParkingLot(){} // we will have to make object first based on the input (smallslot etc,pricing strategy etc, parking strategy etc), then make a complete object of ParkingLot

    public void makeSlotQueues() {
        smallSlotQueue = parkingStrategy.setSlotQueue();
        while(smallSlotcapacity>0){
            Slot slot = new SmallSlot();
            smallSlotQueue.add(slot);
            smallSlotcapacity--;
        }

        mediumSlotQueue = parkingStrategy.setSlotQueue();
        while(mediumSlotcapacity>0){
            Slot slot = new MediumSlot();
            mediumSlotQueue.add(slot);
            mediumSlotcapacity--;
        }

        largeSlotQueue = parkingStrategy.setSlotQueue();
        while(largeSlotcapacity>0){
            Slot slot = new LargeSlot();
            largeSlotQueue.add(slot);
            largeSlotcapacity--;
        }
    }

    public Token park(Vehicle vehicle) throws Exception {
        Queue<Slot>relevantQueue = VehicleQueue.getVehicleQueue(this,vehicle);
        if(relevantQueue==null)
            throw new Exception("No relevant slot for the vehicle");

        if(relevantQueue.size()==0)
            throw new Exception("There is no vacancy yet for this type of vehicle");

        Date inTime = new Date();
        Token token = new Token(inTime,vehicle);
        Slot allotedSlot =  relevantQueue.remove();
        token.setSlot(allotedSlot);
        tokenRepo.put(token.getTokenId(),token);
        return token;
    }

    public double exit(Token token) throws Exception {
        Queue<Slot>relevantQueue = VehicleQueue.getVehicleQueue(this,token.getVehicle());
        if(relevantQueue==null)
            throw new Exception("No relevant slot for the vehicle");

        Date outTime = new Date();
        long timeDiff = outTime.getTime()-token.getInTime().getTime();
        Slot slotRecovered =  token.getSlot();
        int slotAmount =slotRecovered.getFixedPrice();
        double fixedAmount = (timeDiff*1.0/(1000*3600))*slotAmount;

        slotRecovered.setVehicle(null);
        slotRecovered.setToken(null);
        relevantQueue.add(slotRecovered);

        double totalamount = pricingStrategy.addPrice(fixedAmount);
        token.setOutTime(new Date());
        token.setAmount(totalamount);
        tokenRepo.remove(token.getTokenId());
        return totalamount;
    }


}
