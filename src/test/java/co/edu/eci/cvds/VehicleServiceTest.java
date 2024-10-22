package co.edu.eci.cvds;

import co.edu.eci.cvds.exceptions.ModelException;
import co.edu.eci.cvds.exceptions.ServiceException;
import co.edu.eci.cvds.model.Vehicle;
import co.edu.eci.cvds.service.VehicleService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/*import org.junit.jupiter.api.Test;*/

@SpringBootTest
class VehicleServiceTest {

    @Autowired
    private VehicleService vehicleService;

    @Test
    void contextLoads() {
    }

    @Test
    void shouldAddVehicle(){
        try{
            Vehicle vehicle = new Vehicle("AUDI", "A1", 2022, 200);
            vehicleService.addVehicle(vehicle);
            assertEquals("AUDI", vehicleService.getVehicle(vehicleService.getAllVehicles().get(0).getVehicleId()).getBrand());
        }
        catch(ModelException modelException){
            fail();
        }
        catch(ServiceException serviceException){
            fail();
        }
    }

    @Test
    void shouldGetVehicleById(){
        try{
            Vehicle vehicle = new Vehicle("AUDI", "A1", 2022, 200);
            vehicleService.addVehicle(vehicle);
            assertEquals("AUDI", vehicleService.getVehicle(vehicleService.getAllVehicles().get(0).getVehicleId()).getBrand());
        }
        catch(ModelException modelException){
            fail();
        }
        catch(ServiceException serviceException){
            fail();
        }
    }

    @Test
    void shouldNotGetVehicleById(){
        try{
            vehicleService.getVehicle(1);
        }
        catch(ServiceException serviceException){
            assertEquals(ServiceException.nonExistentVehicle, serviceException.getMessage());
        }
    }

    @Test
    void shouldGetBrands(){
        try{
            Vehicle vehicleAudi = new Vehicle("AUDI", "A1", 2022, 200);
            Vehicle vehicleFord = new Vehicle("Ford", "A1", 2022, 200);
            Vehicle vehicleFerrari = new Vehicle("Ferrari", "A1", 2022, 200);
            vehicleService.addVehicle(vehicleAudi);
            vehicleService.addVehicle(vehicleFord);
            vehicleService.addVehicle(vehicleFerrari);
            assertEquals(3, vehicleService.getBrands().size());
        }
        catch(ModelException modelException){
            fail();
        }
    }

    @Test
    void shouldGetModels(){
        try{
            Vehicle vehicleAudiA1 = new Vehicle("AUDI", "A1", 2022, 200);
            Vehicle vehicleAudiA2 = new Vehicle("AUDI", "A2", 2022, 200);
            Vehicle vehicleAudiA3 = new Vehicle("AUDI", "A3", 2022, 200);
            Vehicle vehicleAudiA4 = new Vehicle("AUDI", "A4", 2022, 200);
            vehicleService.addVehicle(vehicleAudiA1);
            vehicleService.addVehicle(vehicleAudiA2);
            vehicleService.addVehicle(vehicleAudiA3);
            vehicleService.addVehicle(vehicleAudiA4);
            assertEquals(4, vehicleService.getModels("AUDI").size());
        }
        catch(ModelException modelException){
            fail();
        }
    }

    @Test
    void shouldGetYears(){
        try{
            Vehicle vehicleAudiA1 = new Vehicle("AUDI", "A1", 2022, 200);
            Vehicle vehicleAudiA2 = new Vehicle("AUDI", "A1", 2023, 200);
            Vehicle vehicleAudiA3 = new Vehicle("AUDI", "A1", 2024, 200);
            Vehicle vehicleAudiA4 = new Vehicle("AUDI", "A1", 2021, 200);
            Vehicle vehicleAudiA5 = new Vehicle("AUDI", "A1", 2020, 200);
            vehicleService.addVehicle(vehicleAudiA1);
            vehicleService.addVehicle(vehicleAudiA2);
            vehicleService.addVehicle(vehicleAudiA3);
            vehicleService.addVehicle(vehicleAudiA4);
            vehicleService.addVehicle(vehicleAudiA5);
            assertEquals(5, vehicleService.getYears("AUDI", "A1").size());
        }
        catch(ModelException modelException){
            fail();
        }
    }

    @Test
    void shouldGetCylinderCapacity(){
        try{
            Vehicle vehicleAudi = new Vehicle("AUDI", "A1", 2022, 100);
            vehicleService.addVehicle(vehicleAudi);
            assertEquals(1, vehicleService.getCylinders("AUDI", "A1", 2022).size());
        }
        catch(ModelException modelException){
            fail();
        }
    }

    @Test
    void shouldUpdateVehicle(){
        try{
            Vehicle vehicleAudi = new Vehicle("AUDI", "A1", 2022, 100);
            vehicleService.addVehicle(vehicleAudi);
            vehicleAudi.setYear(2020);
            vehicleService.updateVehicle(vehicleAudi);
            assertEquals(2020, vehicleService.getVehicle(vehicleService.getAllVehicles().get(0).getVehicleId()).getYear());
        }
        catch(ModelException modelException){
            fail();
        }
        catch(ServiceException serviceException){
            fail();
        }
    }

    @Test
    void shouldNotUpdateVehicle(){
        try{
            Vehicle vehicleAudi = new Vehicle("AUDI", "A1", 2022, 100);
            vehicleService.updateVehicle(vehicleAudi);
            fail();
        }
        catch(ModelException modelException){
            fail();
        }
        catch(ServiceException serviceException){
            assertEquals(ServiceException.nonExistentVehicle, serviceException.getMessage());
        }
    }

    @Test
    void shouldDeleteVehicle(){
        try{
            Vehicle vehicleAudi = new Vehicle("AUDI", "A1", 2022, 100);
            vehicleService.addVehicle(vehicleAudi);
            vehicleService.deleteVehicle(vehicleAudi);
            assertEquals(0, vehicleService.getBrands().size());
        }
        catch(ModelException modelException){
            fail();
        }
    }

    @Test
    void shouldDeleteVehicleById(){
        try{
            Vehicle vehicleAudi = new Vehicle("AUDI", "A1", 2022, 100);
            vehicleService.addVehicle(vehicleAudi);
            vehicleService.deleteVehicle(vehicleService.getAllVehicles().get(0).getVehicleId());
            assertEquals(0, vehicleService.getBrands().size());
        }
        catch(ModelException modelException){
            fail();
        }
        catch(ServiceException serviceException){
            fail();
        }
    }

    @Test
    void shouldNotDeleteVehicleById(){
        try{
            vehicleService.deleteVehicle(1);
            fail();
        }
        catch(ServiceException serviceException){
            assertEquals(ServiceException.nonExistentVehicle, serviceException.getMessage());
        }
    }

    @AfterEach
    public void deleteValues(){
        List<Vehicle> vehicleList = vehicleService.getAllVehicles();
        for(Vehicle vehicle: vehicleList){
            vehicleService.deleteVehicle(vehicle);
        }
    }

}

