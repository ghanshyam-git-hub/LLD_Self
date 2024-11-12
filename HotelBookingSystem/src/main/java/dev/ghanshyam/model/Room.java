package dev.ghanshyam.model;

import dev.ghanshyam.enums.BookingStatus;
import dev.ghanshyam.enums.RoomType;
import dev.ghanshyam.strategy.Pricing;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Data
public abstract class Room {
    protected int room_id;
    protected RoomType roomType;
    protected BookingStatus bookingStatus;
    protected int basePrice;
    protected LocalDateTime blockTime;

    public Room(int room_id){
        this.room_id = room_id;
    }

    public abstract int getRoomBasePrice();

}
