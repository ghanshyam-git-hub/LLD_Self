package dev.ghanshyam.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeluxeSeat extends Seat {

    public DeluxeSeat(int seat_id) {
        this.seat_id = seat_id;
        seatType = SeatType.DELUXE;
    }

    @Override
    public void setBasePrice(int price) {
        basePrice = price;
    }
}
