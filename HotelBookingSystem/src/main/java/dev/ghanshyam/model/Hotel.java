package dev.ghanshyam.model;

import dev.ghanshyam.enums.BookingStatus;
import dev.ghanshyam.enums.RoomType;
import dev.ghanshyam.exception.RoomsNotAvailable;
import dev.ghanshyam.strategy.FestivalPricing;
import dev.ghanshyam.strategy.Pricing;
import dev.ghanshyam.strategy.RoomPricing;
import dev.ghanshyam.strategy.WeekendPricing;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.sql.SQLOutput;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Getter
public class Hotel {
    private int hotel_id;
    private String name;
    private int normalRoomCapacity;
    private int deluxeRoomCapacity;
    private Map<Integer,Room>roomMap;
    private Map<Integer,Booking>bookingMap;
    private Pricing pricing;

    public Hotel(int hotel_id, String name, int normalRoomCapacity, int deluxeRoomCapacity, Pricing pricing) {
        this.hotel_id = hotel_id;
        this.name = name;
        this.normalRoomCapacity = normalRoomCapacity;
        this.deluxeRoomCapacity = deluxeRoomCapacity;
        this.pricing = pricing;
        this.bookingMap = new HashMap<>();
        setMap(normalRoomCapacity,deluxeRoomCapacity);
    }

    private void setMap(int normalRoomCapacity, int deluxeRoomCapacity) {
        roomMap = new HashMap<>();

        int room_id = 1;
        while(normalRoomCapacity>0){
            Room room = new NormalRoom(room_id++);
            room.setRoomType(RoomType.NORMAL);
            room.setBookingStatus(BookingStatus.UNBOOKED);
            room.setBasePrice(100);
            roomMap.put(room.getRoom_id(),room);
            normalRoomCapacity--;
        }

        while(deluxeRoomCapacity>0){
            Room room = new DeluxeRoom(room_id++);
            room.setRoomType(RoomType.DELUXE);
            room.setBookingStatus(BookingStatus.UNBOOKED);
            room.setBasePrice(200);
            roomMap.put(room.getRoom_id(),room);
            deluxeRoomCapacity--;
        }
    }

    public synchronized Booking bookRoom(User user, List<Integer>roomIdList, LocalDateTime checkInTime,LocalDateTime checkOutTime) throws RoomsNotAvailable {
        Booking booking = null;

        if(roomIdList.stream()
                .anyMatch(roomId->(roomMap.get(roomId).getBookingStatus()==BookingStatus.BOOKED))
        ) throw new RoomsNotAvailable("These rooms "+roomIdList+" are not available");

        // now check if the blocked rooms are holding for more than 5 min

        if(roomIdList.stream()
                .filter(roomId->(roomMap.get(roomId).getBookingStatus()==BookingStatus.BLOCKED))
                .anyMatch(roomId->
                        (LocalDateTime.now().minus(5, ChronoUnit.MINUTES)
                        .isBefore(roomMap.get(roomId).getBlockTime())
        ))) throw new RoomsNotAvailable("These rooms "+roomIdList+" are not available, They are blocked, try some other rooms");

        int amount = 0;
        for(Integer roomId : roomIdList){
            Room room = roomMap.get(roomId);
            room.setBookingStatus(BookingStatus.BLOCKED);
            room.setBlockTime(LocalDateTime.now());
            amount+=pricing.getPrice(room);
        }

        booking = Booking.builder()
                .booking_id(Booking.globalBookingId++)
                .user(user)
                .hotel(this)
                .bookingStatus(BookingStatus.BLOCKED)
                .roomIdList(roomIdList)
                .blockTime(LocalDateTime.now())
                .checkIn(checkInTime)
                .checkOut(checkOutTime)
                .amount(amount)
                .build();

        bookingMap.put(booking.getBooking_id(), booking);
        return booking;
    }

    public synchronized boolean confirmAndPay(Booking booking) {
        List<Integer>roomList = booking.getRoomIdList();
        for(Integer roomId : roomList){
            Room room = roomMap.get(roomId);
            room.setBookingStatus(BookingStatus.BOOKED);
        }
        // payment logic ....
        booking.setBookingStatus(BookingStatus.BOOKED);
        return true;
    }

    public synchronized void cancelBooking(Booking booking){
        List<Integer>roomList = booking.getRoomIdList();
        for(Integer roomId : roomList){
            Room room = roomMap.get(roomId);
            room.setBookingStatus(BookingStatus.UNBOOKED);
            room.setBlockTime(null);
        }
        booking.setBookingStatus(BookingStatus.CANCELLED);
        System.out.println("Booking cancelled for user "+booking.getUser().getUser_id() + " for booking Id="+ booking.getBooking_id() + " of amount ="+booking.getAmount() + " for rooms "+ booking.getRoomIdList());
    }

    public boolean checkout(Booking booking){
        List<Integer>roomList = booking.getRoomIdList();
        for(Integer roomId : roomList){
            Room room = roomMap.get(roomId);
            room.setBookingStatus(BookingStatus.UNBOOKED);
            room.setBlockTime(null);
        }
        // payment logic ....
        booking.setBookingStatus(BookingStatus.CHECKEDOUT);
        return true;
    }
}
