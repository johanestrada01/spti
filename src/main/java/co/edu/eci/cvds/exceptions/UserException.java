package co.edu.eci.cvds.exceptions;

public class UserException extends Exception{

    public static final String userNotFound = "The user do not exist";

    public UserException(String message){
        super(message);
    }
}
