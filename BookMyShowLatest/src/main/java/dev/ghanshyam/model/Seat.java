package dev.ghanshyam.model;

import dev.ghanshyam.enums.SeatStatus;
import dev.ghanshyam.enums.SeatType;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public abstract class Seat {
    int seat_id;
    SeatType seatType;
    SeatStatus seatStatus;
    int basePrice;

    public abstract void setBasePrice(int price);
}
