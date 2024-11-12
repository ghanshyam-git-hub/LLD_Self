package dev.ghanshyam.model;

import dev.ghanshyam.enums.BookingStatus;
import dev.ghanshyam.enums.RoomType;
import lombok.Setter;

@Setter
public class DeluxeRoom extends Room{
    public DeluxeRoom(int room_id){
        super(room_id);
        roomType = RoomType.NORMAL;
        bookingStatus = BookingStatus.UNBOOKED;
    }

    @Override
    public int getRoomBasePrice() {
        return this.getBasePrice();
    }
}
