package dev.ghanshyam.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NormalSeat extends Seat{

    public NormalSeat(int seat_id) {
        this.seat_id = seat_id;
        seatType = SeatType.NORMAL;
    }

    @Override
    public void setBasePrice(int price) {
        basePrice = price;
    }
}
