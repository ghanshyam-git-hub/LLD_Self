package dev.ghanshyam.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Builder
@Getter
@Setter
public class Booking {
    public static int globalbooking_id = 1;
    User user;
    int booking_id;
    LocalDateTime bookingTime;
    List<Integer>seatIdList;
    int totalAmount;
    PaymentStatus paymentStatus;
    String payment_id;
    Show show;
}
