package dev.ghanshyam.exception;

public class SeatAlreadyBookedException extends Throwable {
    public SeatAlreadyBookedException(String msg) {
        super(msg);
    }
}
