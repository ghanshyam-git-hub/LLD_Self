package dev.ghanshyam.model;

import dev.ghanshyam.model.enums.VehicleType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class Vehicle {
    private VehicleType vehicleType;
    private String vehicle_no;
}
