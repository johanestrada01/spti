package co.edu.eci.cvds.model;

import co.edu.eci.cvds.exceptions.ModelException;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "VEHICLE")
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "VEHICLE_ID")
    private int vehicleId;

    @Column(name = "BRAND", nullable = false)
    private String brand;

    @Column(name = "MODEL", nullable = false)
    private String model;

    @Column(name = "VEHICLE_YEAR", nullable = false)
    private Integer year;

    @Column(name = "CYLINDER_CAPACITY", nullable = false)
    private Integer cylinderCapacity;

    @ManyToMany(mappedBy = "vehicles", fetch = FetchType.EAGER)
    private List<Item> items;

    public Vehicle() {
    }

    public Vehicle(String brand, String model, Integer year, Integer cylinderCapacity) throws ModelException {
        if(year <= 0) throw new ModelException(ModelException.VEHICLE_INVALID_YEAR);
        if(cylinderCapacity <= 0) throw new ModelException(ModelException.VEHICLE_INVALID_CYLINDER_CAPACITY);
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.cylinderCapacity = cylinderCapacity;
        this.items = new ArrayList<Item>();
    }

    public int getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(int vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Integer getCylinderCapacity() {
        return cylinderCapacity;
    }

    public void setCylinderCapacity(int cylinderCapacity) {
        this.cylinderCapacity = cylinderCapacity;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public void addItem(Item item){
        items.add(item);
    }
}
