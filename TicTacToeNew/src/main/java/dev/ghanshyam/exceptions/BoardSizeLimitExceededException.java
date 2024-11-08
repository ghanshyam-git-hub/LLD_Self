package dev.ghanshyam.exceptions;

public class BoardSizeLimitExceededException extends Exception{
    public BoardSizeLimitExceededException(String msg){
        super(msg);
    }
}
