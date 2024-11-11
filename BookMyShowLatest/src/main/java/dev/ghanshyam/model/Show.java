package dev.ghanshyam.model;

import dev.ghanshyam.enums.BookingStatus;
import dev.ghanshyam.enums.SeatStatus;
import dev.ghanshyam.exception.SeatAlreadyBookedException;
import dev.ghanshyam.strategy.Pricing;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;

@Builder
@Getter
public class Show {
    int show_id;
    Movie movie;
    LocalDateTime startTime;
    LocalDateTime endTime;
    ShowType showType;
    Map<Integer,Seat>seatMap;
    Pricing pricing;
    Lock lock;

    public  Booking bookSeats(User user, List<Integer>seatIdList) throws SeatAlreadyBookedException {
        for(Integer id : seatIdList) {
            Seat seat = seatMap.get(id);
            if (seat.getSeatStatus() == SeatStatus.BLOCKED || seat.getSeatStatus() == SeatStatus.BOOKED)
                throw new SeatAlreadyBookedException("Seats " + seatIdList + " are not available, please try other seats");
        }

        lock.lock();
        try { // always do this way - after lock use try block and close the lock in finally block
            // double check imp other wise it will not be consistent, other way is to synchronize the whole method
            for (Integer id : seatIdList) {
                Seat seat = seatMap.get(id);
                if (seat.getSeatStatus() == SeatStatus.BLOCKED || seat.getSeatStatus() == SeatStatus.BOOKED)
                    throw new SeatAlreadyBookedException("Seats " + seatIdList + " are not available, please try other seats");
            }

            int totalAmount = 0;
            Booking booking = null;

            for (Integer id : seatIdList) {
                Seat seat = seatMap.get(id);
                seat.setSeatStatus(SeatStatus.BLOCKED);
                totalAmount += pricing.getPrice(seat);
            }

            booking = Booking.builder()
                    .show(this)
                    .booking_id(Booking.globalId++)
                    .user(user)
                    .bookingStatus(BookingStatus.BLOCKED)
                    .seatIdList(seatIdList)
                    .amount(totalAmount)
                    .build();
            return booking;
        }
        finally {
            lock.unlock();
        }
    }

    public synchronized Boolean confirmAndPay(Booking booking) {
        List<Integer> seatIdList = booking.getSeatIdList();

        for (Integer id : seatIdList) {
            Seat seat = seatMap.get(id);
            seat.setSeatStatus(SeatStatus.BOOKED);
        }
        // payment logic..
        return true;
    }

    public synchronized void cancelBooking(Booking booking){
        List<Integer> seatIdList = booking.getSeatIdList();

        for (Integer id : seatIdList) {
            Seat seat = seatMap.get(id);
            seat.setSeatStatus(SeatStatus.UNBOOKED);
        }
        // refund logic..
        System.out.println("Refund inititated for booking id "+booking.getBooking_id());
        System.out.println();
    }

}
