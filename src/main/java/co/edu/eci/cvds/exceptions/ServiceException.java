package co.edu.eci.cvds.exceptions;

public class ServiceException extends Exception{

    public final static String nonExistentCategory = "The category does not exist";
    public final static String nonExistentItem = "The item does not exist";
    public final static String nonExistentQuotation = "The quotation does not exist";
    public final static String nonExistentVehicle = "The vehicle does not exist";

    public ServiceException(String message){
        super(message);
    }

}
