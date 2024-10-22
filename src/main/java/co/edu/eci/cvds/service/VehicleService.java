package co.edu.eci.cvds.service;

import co.edu.eci.cvds.exceptions.ServiceException;
import co.edu.eci.cvds.model.Item;
import co.edu.eci.cvds.model.Vehicle;
import co.edu.eci.cvds.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VehicleService {

    private final VehicleRepository vehicleRepository;

    @Autowired
    public VehicleService(VehicleRepository vehicleRepository){
        this.vehicleRepository = vehicleRepository;
    }

    public Vehicle addVehicle(Vehicle vehicle){
        return vehicleRepository.save(vehicle);
    }

    public List<String> getBrands(){
        return vehicleRepository.findDistinctBrand();
    }

    public List<String> getModels(String brand){
        return vehicleRepository.findDistinctModelByBrand(brand);
    }

    public List<Integer> getYears(String brand, String model){
        return vehicleRepository.findDistinctYearByBrandAndModel(brand, model);
    }

    public List<Integer> getCylinders(String brand, String model, int year){
        return vehicleRepository.findDistinctCylinderCapacityByBrandAndModelAndYear(brand, model, year);
    }

    public Vehicle getVehicle(int id) throws ServiceException {
        Optional<Vehicle> result = vehicleRepository.findById(id);
        if(result.isEmpty()){
            throw new ServiceException(ServiceException.nonExistentVehicle);
        }
        return result.get();
    }

    public List<Vehicle> getAllVehicles(){
        return vehicleRepository.findAll();
    }

    public void updateVehicle(Vehicle vehicle) throws ServiceException {
        Vehicle update = getVehicle(vehicle.getVehicleId());
        update.setBrand(vehicle.getBrand());
        update.setModel(vehicle.getModel());
        update.setYear(vehicle.getYear());
        update.setCylinderCapacity(vehicle.getCylinderCapacity());
        update.setItems(vehicle.getItems());
        vehicleRepository.save(update);
    }

    public void deleteVehicle(Vehicle vehicle){
        vehicleRepository.delete(vehicle);
    }

    public Vehicle getVehicleByParameters(String brand, String model, Integer year, Integer cylinderCapacity){
        return vehicleRepository.findByBrandAndModelAndYearAndCylinderCapacity(brand, model, year, cylinderCapacity);
    }

    public void deleteVehicle(int id) throws ServiceException {
        Vehicle vehicle = getVehicle(id);
        deleteVehicle(vehicle);
    }

    public void addItem(Vehicle vehicle, Item item){
        vehicle.addItem(item);
        try {
            updateVehicle(vehicle);
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
    }

}
