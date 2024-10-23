package co.edu.eci.cvds.exceptions;

public class ServiceException extends Exception{

    public static final String NON_EXISTENT_CATEGORY = "The category does not exist";
    public static final String NON_EXISTENT_ITEM = "The item does not exist";
    public static final String NON_EXISTENT_QUOTATION = "The quotation does not exist";
    public static final String NON_EXISTENT_VEHICLE = "The vehicle does not exist";

    public ServiceException(String message){
        super(message);
    }

}
