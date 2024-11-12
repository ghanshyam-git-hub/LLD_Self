package dev.ghanshyam.model;

import dev.ghanshyam.enums.BookingStatus;
import dev.ghanshyam.enums.RoomType;
import dev.ghanshyam.strategy.Pricing;
import lombok.Setter;

@Setter
public class NormalRoom extends Room{

    public NormalRoom(int room_id){
        super(room_id);
        roomType = RoomType.NORMAL;
        bookingStatus = BookingStatus.UNBOOKED;
    }
    @Override
    public int getRoomBasePrice() {
        return this.getBasePrice();
    }
}
