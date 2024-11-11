package dev.ghanshyam.model;

public class NormalSeat extends  Seat{

    public NormalSeat(int seat_id) {
        this.seat_id = seat_id;
    }

    @Override
    public void setBasePrice(int price) {
        basePrice = price;
    }
}
