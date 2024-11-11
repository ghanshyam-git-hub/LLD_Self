package dev.ghanshyam.model;

import dev.ghanshyam.enums.BookingStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Getter
public class Booking {
    static int globalId = 1;
    int booking_id;
    User user;
    Show show;
    List<Integer>seatIdList;
    int amount;
    BookingStatus bookingStatus;
}
