package dev.ghanshyam;

import dev.ghanshyam.command.*;
import dev.ghanshyam.model.MediumSlot;
import dev.ghanshyam.model.ParkingLot;
import dev.ghanshyam.model.Token;
import dev.ghanshyam.model.Vehicle;
import dev.ghanshyam.model.enums.VehicleType;
import dev.ghanshyam.strategy.parking.Farthest;
import dev.ghanshyam.strategy.parking.Nearest;
import dev.ghanshyam.strategy.parking.ParkingStrategy;
import dev.ghanshyam.strategy.pricing.NormalDay;
import dev.ghanshyam.strategy.pricing.PricingStrategy;
import dev.ghanshyam.strategy.pricing.SpecialDay;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ParkingLot parkingLot = new ParkingLot();
        Scanner sc = new Scanner(System.in);
        Command command = null;

    while(true) {
        System.out.println("Lets make your parkingLot");

        command = new SetSmallSlot(parkingLot);
        System.out.println("How many slots you want for small cars");
        int smallCarSlot = sc.nextInt();

        command.execute(smallCarSlot);

        command = new SetMediumSlot(parkingLot);
        System.out.println("How many slots you want for medium cars");
        int mediumCarSlot = sc.nextInt();
        command.execute(mediumCarSlot);

        command = new SetLargeSlot(parkingLot);
        System.out.println("How many slots you want for large cars");
        int largeCarSlot = sc.nextInt();
        command.execute(largeCarSlot);

        command = new SetParkingStrategy(parkingLot);
        System.out.println("What parking strategy you want 1: Nearest, 2: Farthest");
        ParkingStrategy parkingstrategy = null;
        String s = sc.next();
        try {
            Integer choice = Integer.valueOf(s);
            if (choice == 1) {
                parkingstrategy = new Nearest();
            } else if (choice == 2) {
                parkingstrategy = new Farthest();
            } else {
                throw new NumberFormatException("Please enter a valid digit 1 or 2");
            }
            command.execute(parkingstrategy);
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid digit 1 or 2");
            continue;
        }


        PricingStrategy pricingStrategy = null;
        System.out.println("Select Pricing Startegy 1:NormalDay, 2:SpecialDay");
        command = new SetPricingStrategy(parkingLot);

        s = sc.next();
        try {
            Integer choice = Integer.valueOf(s);
            if (choice == 1) {
                pricingStrategy = new NormalDay();
            } else if (choice == 2) {
                pricingStrategy = new SpecialDay();
            } else {
                throw new NumberFormatException("Please enter a valid digit 1 or 2");
            }
            command.execute(pricingStrategy);
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid digit 1 or 2");
            continue;
        }

        command = new SetAllSlotQueues(parkingLot);
        command.execute(null);

        break;
    }

        while(true) {
            System.out.println("Choose your action 1:Parking" + " , 2: Exiting");
            int input = sc.nextInt();
            if (input == 1) {
                System.out.println("Enter Vehicle no and Vehicle Type(SMALL,MEDIUM,LARGE)");
                String vehicleno = sc.next();
                String type = sc.next();
                VehicleType vehicleType = null;
                switch (type.toUpperCase()) {
                    case "SMALL":
                        vehicleType = VehicleType.SMALL;
                        break;
                    case "MEDIUM":
                        vehicleType = VehicleType.MEDIUM;
                        break;
                    case "LARGE":
                        vehicleType = VehicleType.LARGE;
                }
                if (vehicleType == null || vehicleno == null)
                    try {
                        throw new IllegalArgumentException("VehicleType Not Valid");
                    } catch (IllegalArgumentException e) {
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
                } catch (Exception e) {
                    System.out.println("token could not be generated for some reason try again");
                }
            }

            if (input == 2) {
                command = new ExitCommand(parkingLot);
                System.out.println("Enter you token id");
                int tokenId = sc.nextInt();
                try {
                    Token token = parkingLot.getTokenRepo().get(tokenId);
                    double amount = (Double) command.execute(token);
                    System.out.println("Vehicle " + token.getVehicle().getVehicle_no() + "entered at " + token.getInTime() + " exited at " + token.getOutTime() + " and total charge = " + amount);
                    command.execute(token);
                    System.out.println("Slot " + token.getSlot().getSlotId() + " is vacated");
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    System.out.println("Please check the token id");
                }
            }
        }

    }
}