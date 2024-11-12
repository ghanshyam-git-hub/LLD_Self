package dev.ghanshyam.model;

import dev.ghanshyam.enums.BookingStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
public class Booking {
    @Setter
    static int globalBookingId = 1;
    int booking_id;
    User user;
    Hotel hotel;
    List<Integer>roomIdList;
    @Setter
    BookingStatus bookingStatus;
    @Setter
    LocalDateTime blockTime;
    LocalDateTime checkIn;
    LocalDateTime checkOut;
    int amount;
}
