package dev.ghanshyam.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public abstract class Seat {
    protected int seat_id;
    protected SeatType seatType;
    protected SeatStatus seatStatus = SeatStatus.UNBOOKED;
    protected int basePrice;

    public abstract void setBasePrice(int price);
}
