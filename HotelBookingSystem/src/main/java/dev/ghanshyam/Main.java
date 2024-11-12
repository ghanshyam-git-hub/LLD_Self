package dev.ghanshyam;

import dev.ghanshyam.enums.BookingStatus;
import dev.ghanshyam.exception.RoomsNotAvailable;
import dev.ghanshyam.model.Booking;
import dev.ghanshyam.model.Hotel;
import dev.ghanshyam.model.User;
import dev.ghanshyam.strategy.FestivalPricing;
import dev.ghanshyam.strategy.RoomPricing;
import dev.ghanshyam.strategy.WeekendPricing;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) throws InterruptedException {

        Hotel hotel = new Hotel(1,
                "Hari Niwas",
                20,
                20,
                new FestivalPricing(new WeekendPricing(new RoomPricing())));

        int users = 1000;
        bookingViaExecutoService(users,hotel);

        int cancellingUsers = 10;
        cancelViaExecutorService(cancellingUsers,hotel);

        Thread.sleep(10000); // wait till all the threads complete their work
        System.out.println("\n Room map ->"+hotel.getRoomMap());

        List<Integer>bookedRooms = hotel.getRoomMap()
                .entrySet().stream()
                .filter(entry->entry.getValue().getBookingStatus()== BookingStatus.BOOKED)
                .map(entry->entry.getKey())
                .toList();

        List<Integer>cancelledBookings = hotel.getBookingMap()
                .entrySet().stream()
                .filter(entry->entry.getValue().getBookingStatus()== BookingStatus.CANCELLED)
                .map(entry->entry.getValue().getBooking_id())
                .toList();

        System.out.println();
        System.out.println("Booked rooms = "+bookedRooms);
        System.out.println("Total count of booked rooms = "+bookedRooms.size());
        System.out.println("Bookings cancelled = "+cancelledBookings);
        hotel.getBookingMap().entrySet().stream()
                .forEach(entry->
                        System.out.println(
                                "Booking id - "
                                +entry.getValue().getBooking_id()
                                + (entry.getValue().getBookingStatus() ==
                                        BookingStatus.BOOKED ? " Booked " :" Cancelled " )
                                +entry.getValue().getRoomIdList()));
    }

    private static void bookingViaExecutoService(int users,Hotel hotel){
        ExecutorService executorService = Executors.newCachedThreadPool();
        while(users>0){ // concurrent users trying
            Random random = new Random();
            User user = new User(random.nextInt(random.nextInt(1,users+1)));
            List<Integer>roomList = generateRandomList(1,41);
            if(roomList.size()==0) continue; // if random generation leads to list of size 0 continue

            Runnable r = ()->{
                try {
                    Booking booking = hotel.bookRoom(user,roomList, LocalDateTime.now(),LocalDateTime.now().plusDays(2));
                    if(booking!=null && hotel.confirmAndPay(booking)) // imp to place all the concurrent functions in the same try block, when i tried to place the confirmandPay after this try then it gave errors, becoz here we can be sure that yes rooms were blocked and now if the error is there in the payment then that error can be displayed properly
                        System.out.println("Booking successful for booking id "+booking.getBooking_id()+" for rooms "+booking.getRoomIdList()+" paid amount = "+booking.getAmount() );
                    else {
                        System.out.println("Some problem in the payment"+booking.getBooking_id()+" for rooms  "+booking.getRoomIdList()+", please try booking again");
                        hotel.cancelBooking(booking);
                    }
                } catch (RoomsNotAvailable e) {
                    System.out.println(e.getMessage());
                }

                // just to simulate the delay of booking we do sleep (but not compulsory)
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            };
            executorService.submit(r);
            users--;
        }
    }

    private static void cancelViaExecutorService(int countOfUsers,Hotel hotel){
        ExecutorService executorService = Executors.newCachedThreadPool();
        while(countOfUsers>0){ // concurrent users trying

            List<Integer>bookedOrBlockedRecords = hotel.getBookingMap().entrySet()
                    .stream().filter(booking->booking.getValue()
                            .getBookingStatus()!=BookingStatus.CANCELLED)
                            .map(entry->entry.getKey()).toList(); // making fresh list of bookings which are valid

            if(bookedOrBlockedRecords.size()==0) continue;

            Random random = new Random();
            int validBookingOrderId = bookedOrBlockedRecords.get(
                    random.nextInt(0,bookedOrBlockedRecords.size())
                    );

            Booking bookingToBeCancelled = hotel.getBookingMap().get(validBookingOrderId); // this booking selected randomly from the current valid list, can be same as in the pervious iteration, becoz that is cancelled in different thread and can be delayed when we are fetching this booking id. But this is only for testing purpose. In real life every user can apply their cancellation. This is only for test case
            System.out.println("Cancellation application for booking id="+bookingToBeCancelled.getBooking_id()+" for rooms "+bookingToBeCancelled.getRoomIdList());
            Runnable r = ()->{
                try {
                        hotel.cancelBooking(bookingToBeCancelled);
                } catch (RuntimeException e) {
                    System.out.println("Some problem in cancellation");
                }

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            };
            executorService.submit(r);
            countOfUsers--;
        }
    }

    private static List<Integer> generateRandomList(int start, int end) {
    Random random = new Random();
    return random.ints(1,41)
            .distinct()
            .limit(3)
            .boxed()
            .toList();
    }
}