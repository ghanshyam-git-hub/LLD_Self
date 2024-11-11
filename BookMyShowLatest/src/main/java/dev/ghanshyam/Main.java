package dev.ghanshyam;

import dev.ghanshyam.enums.SeatStatus;
import dev.ghanshyam.enums.SeatType;
import dev.ghanshyam.exception.SeatAlreadyBookedException;
import dev.ghanshyam.model.*;
import dev.ghanshyam.strategy.BasePricing;
import dev.ghanshyam.strategy.EveningShowPricing;
import dev.ghanshyam.strategy.WeekendShowPricing;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;

public class Main {
    public static void main(String[] args) throws InterruptedException {

        Show show1 = Show.builder()
                .show_id(1)
                .movie(new Movie("Mogli"))
                .showType(ShowType.EVENING)
                .startTime(LocalDateTime.now())
                .endTime(LocalDateTime.now().plusHours(3))
                .pricing(new WeekendShowPricing(new EveningShowPricing(new BasePricing())))
                .lock(new ReentrantLock())
                .seatMap(setSeatMap())
                .build();

        ExecutorService executorService = Executors.newCachedThreadPool();
        int n=100;
        int userid = 1;
        int i=0;
        int max = 40;
        while(n>0){
            Runnable r = ()->{
                Random random = new Random();
                User user = new User(random.nextInt(100));
                List<Integer>listOfSeat = generateSeats(random.nextInt(6),max);
                Booking booking = null;
                if(listOfSeat.size()!=0) // imp becoz sometimes it generates list with 0 seats also
                booking = bookSeats(user,show1,listOfSeat);

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

            };
            executorService.submit(r);
            n--;
        }

        Thread.sleep(10000); // waiting for all the threads to end
        System.out.println(show1.getSeatMap());
        List<Integer> bookedSeats = show1.getSeatMap().entrySet().stream().filter(entry->entry.getValue().getSeatStatus()==SeatStatus.BOOKED).map(entry->entry.getKey()).toList();

        System.out.println();
        System.out.println("booked seats ="+bookedSeats);
        System.out.println("count of booked seats ="+bookedSeats.size());

    }

    private static Booking bookSeats(User user,Show show, List<Integer> seatIdList) {
        try {
            Booking booking = show.bookSeats(user, seatIdList);
            Thread.sleep(1000);
            if(show.confirmAndPay(booking) && !seatIdList.contains(1)) {
                System.out.println("Payement is done by user "
                        + user.getUser_id()
                        + " for bookingId " + booking.getBooking_id()
                        + " for show " + booking.getShow().getMovie().getMovieName() + " Time :" + booking.getShow().getStartTime().getHour() + "-" + booking.getShow().getEndTime().getHour()
                        + "for seats " + booking.getSeatIdList() + " "
                        + "total amount =" + booking.getAmount()
                        + "payment done");

                return booking;
            }else if(seatIdList.contains(1)){
                show.cancelBooking(booking);
                System.out.println("Cancel completed for booking id="+booking.getBooking_id()+" user "+booking.getUser().getUser_id()+" seats - "+booking.getSeatIdList());
            }else{
                throw new RuntimeException("Payment could not be completed");
            }
        } catch (SeatAlreadyBookedException e) {
            System.out.println("Sorry User "+user.getUser_id()+". These seats are already Booked");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    private static Map<Integer, Seat> setSeatMap() {
        Map<Integer, Seat> seatMap = new HashMap<>();
        int seatid = 1;
        int normalSeat = 20;
        while(normalSeat>0){
            Seat seat = new NormalSeat(seatid);
            seat.setSeatStatus(SeatStatus.UNBOOKED);
            seat.setSeatType(SeatType.NORMAL);
            seat.setSeat_id(seatid);
            seat.setBasePrice(100);

            seatMap.put(seatid,seat);
            seatid++;
            normalSeat--;
        }


        int deluxeSeat = 20;
        while(deluxeSeat>0){
            Seat seat = new DeluxeSeat(seatid);
            seat.setSeatStatus(SeatStatus.UNBOOKED);
            seat.setSeatType(SeatType.DELUXE);
            seat.setSeat_id(seatid);
            seat.setBasePrice(200);

            seatMap.put(seatid,seat);
            seatid++;
            deluxeSeat--;
        }
        return seatMap;
    }

    private static List<Integer> generateSeats(int count,int max){
        Random random = new Random();
        List<Integer> seatsList = new ArrayList<>();
        return random.ints(1,max+1)
                .distinct()
                .limit(count)
                .boxed()
                .toList();

    }
}