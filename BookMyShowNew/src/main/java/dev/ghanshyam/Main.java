package dev.ghanshyam;

import dev.ghanshyam.exception.SeatAlreadyBookedException;
import dev.ghanshyam.model.*;
import dev.ghanshyam.strategy.BasePricing;
import dev.ghanshyam.strategy.EveningShowPricing;
import dev.ghanshyam.strategy.WeekendShowPricing;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

public class Main {
    public static void main(String[] args) throws SeatAlreadyBookedException, InterruptedException {

        Show show1 = Show.builder()
                .show_id(1)
                .movie(new Movie(1,"Mogli"))
                .startTime(LocalDateTime.now())
                .endTime(LocalDateTime.now().plusHours(3))
                .lock(new ReentrantLock())
                .pricing(new EveningShowPricing(new BasePricing()))
                .seatMap(setSeatMap())
                .build();

        Show show2 = Show.builder()
                .show_id(2)
                .movie(new Movie(2,"Tarzan"))
                .startTime(LocalDateTime.now())
                .endTime(LocalDateTime.now().plusHours(3))
                .lock(new ReentrantLock())
                .pricing(new WeekendShowPricing(new EveningShowPricing(new BasePricing())))
                .seatMap(setSeatMap())
                .build();

        Screen screen1 = new Screen(1,List.of(show1));
        Screen screen2 = new Screen(1,List.of(show2));

        Theatre theatre1 = new Theatre(1,List.of(screen1,screen2));


        Thread t1 = new Thread(()->{
            User user1 = new User(1);
            Booking booking = bookSeat(user1,show1,List.of(1,2,3,4,5));
            show1.cancelBooking(booking);
        });

        Thread t2 = new Thread(()->{
            User user2 = new User(2);
            Booking booking = bookSeat(user2,show1,List.of(20,21,22,23,24));
        });

        Thread t3 = new Thread(()->{
            User user3 = new User(3);
            Booking booking = bookSeat(user3,show2,List.of(1,2,3,4,5));
        });

        Thread t4 = new Thread(()->{
            User user4 = new User(4);
            Booking booking = bookSeat(user4,show2,List.of(21,22,23));
        });

        t1.start();
        t2.start();
        t3.start();
        t4.start();

        t1.join();
        t2.join();
        t3.join();
        t4.join();
    }

    public static Booking bookSeat(User user, Show show,List<Integer>list){
        try {
            Booking booking = show.bookSeats(user,list);
            Thread.sleep(1000);
            if(show.confirmBookingAndPay(booking)) {
                System.out.println("Payement is done by user "
                        + booking.getUser().getUser_id()
                        + " for bookingId " + booking.getBooking_id()
                        + " for show " + booking.getShow().getMovie().getMovieName() + " Time :" + booking.getShow().getStartTime().getHour() + "-" + booking.getShow().getEndTime().getHour()
                        + "for seats " + booking.getSeatIdList() + " "
                        + "total amount =" + booking.getTotalAmount()
                        + "payment done");
            }else{
                throw new RuntimeException("Payment could not complete for the user "+booking.getUser().getUser_id()+" booking id "+booking.getBooking_id());
            }
            System.out.println();
            return booking;
        } catch (SeatAlreadyBookedException e) {
            System.out.println("Sorry user "+user.getUser_id());
            System.out.println("These seats "+list+" are already booked"+" for this show "+", pls book other seats");
            System.out.println();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        throw new RuntimeException("Some unknown error");
    }

    public static Map<Integer, Seat> setSeatMap(){
        Map<Integer, Seat>map = new HashMap<>();
        int seat_id = 1;

        int normalSeatCount = 20;
        while(normalSeatCount>0){
            Seat seat1 = new NormalSeat(seat_id++);
            seat1.setBasePrice(100); // base price for normal seat
            seat1.setSeatStatus(SeatStatus.UNBOOKED);
            seat1.setSeatType(SeatType.NORMAL);
            map.put(seat1.getSeat_id(), seat1);
            normalSeatCount--;
        }

        int deluxeSeatCount = 20;
        while(deluxeSeatCount>0){
            Seat seat = new DeluxeSeat(seat_id++);
            seat.setBasePrice(200);// base price for deluxe seat
            seat.setSeatStatus(SeatStatus.UNBOOKED);
            seat.setSeatType(SeatType.DELUXE);
            map.put(seat.getSeat_id(), seat);
            deluxeSeatCount--;
        }
        return map;
    }
}