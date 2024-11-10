package dev.ghanshyam.model;

import dev.ghanshyam.exception.SeatAlreadyBookedException;
import dev.ghanshyam.strategy.BasePricing;
import dev.ghanshyam.strategy.Pricing;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
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
    Lock lock;
    Pricing pricing;
    Map<Integer,Seat>seatMap;

    public  Booking bookSeats(User user, List<Integer>seatIdtoBeBooked) throws SeatAlreadyBookedException {
        if(seatIdtoBeBooked.size()>5) throw new IllegalArgumentException("Only 5 rows are allowed at a time");
        for(Integer seatId:seatIdtoBeBooked){
            Seat seat = seatMap.get(seatId);
            if(seat.getSeatStatus()==SeatStatus.BOOKED || seat.getSeatStatus()==SeatStatus.PENDING)
                throw new SeatAlreadyBookedException("Seat already booked");
        }
        int amount = 0;
        var bookingBuilder = Booking.builder();
        Booking booking = null;

        lock.lock(); // even though we have to do double checking but this will be fast becoz in case of synchronized method invalid requests have to wait for the lock a long time only to discover that they are not valid
        // we have to do double checking here of the seats, else there can be double booking also, becoz in the first check it may not be booked but while it comes here it may get booked
        for(Integer seatId:seatIdtoBeBooked){
            Seat seat = seatMap.get(seatId);
            if(seat.getSeatStatus()==SeatStatus.BOOKED || seat.getSeatStatus()==SeatStatus.PENDING)
                throw new SeatAlreadyBookedException("Seat already booked");
        }

                for (Integer seatId : seatIdtoBeBooked) {
                    Seat seat = seatMap.get(seatId);
                    seat.setSeatStatus(SeatStatus.PENDING);
                    amount += pricing.getPrice(seat);
                }
                   booking = bookingBuilder.booking_id(Booking.globalbooking_id++)
                        .user(user)
                        .show(this)
                        .bookingTime(LocalDateTime.now())
                        .seatIdList(seatIdtoBeBooked)
                        .paymentStatus(PaymentStatus.PENDING)
                        .totalAmount(amount)
                        .build();

                lock.unlock();

        return booking;
    }

    public synchronized boolean confirmBookingAndPay(Booking booking){
        //collect payment
        List<Integer>bookedSeatIds = booking.getSeatIdList();
        List<Seat>bookedSeats = new ArrayList<>();
        for(Integer seatId : bookedSeatIds)
            bookedSeats.add(seatMap.get(seatId));

        bookedSeats.stream().forEach(seat->seat.setSeatStatus(SeatStatus.BOOKED));
        booking.setPayment_id(booking.getUser().user_id+"-"+new Date()+" "+booking.getTotalAmount());
        booking.setPaymentStatus(PaymentStatus.PAID);
        return true;
    }

    public synchronized boolean cancelBooking(Booking booking){
        List<Integer>bookedSeatIds = booking.getSeatIdList();
        List<Seat>bookedSeats = new ArrayList<>();
        for(Integer seatId : bookedSeatIds)
            bookedSeats.add(seatMap.get(seatId));

        bookedSeats.stream().forEach(seat->seat.setSeatStatus(SeatStatus.UNBOOKED));
        refund(booking);
        System.out.println("Booking for booking id "+booking.getBooking_id()+ " for user "+booking.getUser().getUser_id()+" is cancelled.");
        return true;
    }

    private void refund(Booking booking){
        System.out.println("Refund of amount "+booking.getTotalAmount() + " has been initiated");
    }

}
