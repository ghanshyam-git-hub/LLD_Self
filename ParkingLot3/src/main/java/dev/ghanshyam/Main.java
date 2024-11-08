package dev.ghanshyam;

import dev.ghanshyam.command.*;
import dev.ghanshyam.model.MediumSlot;
import dev.ghanshyam.model.ParkingLot;
import dev.ghanshyam.model.Token;
import dev.ghanshyam.model.Vehicle;
import dev.ghanshyam.model.enums.VehicleType;
import dev.ghanshyam.strategy.parking.Nearest;
import dev.ghanshyam.strategy.pricing.NormalDay;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ParkingLot parkingLot = new ParkingLot();
        Command command = new SetSmallSlot(parkingLot);
        command.execute(3);

        command = new SetMediumSlot(parkingLot);
        command.execute(3);

        command = new SetLargeSlot(parkingLot);
        command.execute(3);

        command = new SetParkingStrategy(parkingLot);
        command.execute(new Nearest());

        command = new SetPricingStrategy(parkingLot);
        command.execute(new NormalDay());

        command = new SetAllSlotQueues(parkingLot);
        command.execute(null);


        Scanner sc = new Scanner(System.in);
        while(true){
            System.out.println("Choose your action 1:Parking"+" , 2: Exiting");
            int input = sc.nextInt();
            if(input==1){
                System.out.println("Enter Vehicle no and Vehicle Type(SMALL,MEDIUM,LARGE)");
                String vehicleno = sc.next();
                String type = sc.next();
                VehicleType vehicleType = null;
                switch(type.toUpperCase()){
                    case "SMALL": vehicleType = VehicleType.SMALL; break;
                    case "MEDIUM": vehicleType = VehicleType.MEDIUM;break;
                    case "LARGE": vehicleType = VehicleType.LARGE;
                }
                if(vehicleType==null || vehicleno==null)
                    try {
                        throw new IllegalArgumentException("VehicleType Not Valid");
                    }catch(IllegalArgumentException e){
                        System.out.println(e.getMessage());
                    }

                command = new ParkCommand(parkingLot);
                Vehicle vehicle = Vehicle.builder()
                        .vehicleType(vehicleType)
                        .vehicle_no(vehicleno)
                        .build();

                try {
                    Token token = (Token) command.execute(vehicle);
                    System.out.println("Vehicle " + vehicle.getVehicle_no() + " of type " + vehicle.getVehicleType() + " is been alloted slot id = " + token.getSlot().getSlotId() + " and tokenid = " + token.getTokenId());
                }catch(Exception e){
                    System.out.println("token could not be generated for some reason try again");
                }
            }

            if(input==2){
                command = new ExitCommand(parkingLot);
                System.out.println("Enter you token id");
                int tokenId = sc.nextInt();
                try {
                    Token token = parkingLot.getTokenRepo().get(tokenId);
                    double amount = (Double) command.execute(token);
                    System.out.println("Vehicle " + token.getVehicle().getVehicle_no() + "entered at " + token.getInTime() + " exited at " + token.getOutTime() + " and total charge = " + amount);
                    command.execute(token);
                    System.out.println("Slot " + token.getSlot().getSlotId() + " is vacated");
                }catch(Exception e){
                    System.out.println(e.getMessage());
                    System.out.println("Please check the token id");
                }
            }
        }

    }
}