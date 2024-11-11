package dev.ghanshyam.model;

public class DeluxeSeat extends  Seat{

    public DeluxeSeat(int seat_id) {
        this.seat_id = seat_id;
    }

    @Override
    public void setBasePrice(int price) {
        basePrice = price;
    }
}
