package co.edu.eci.cvds.repository;

import co.edu.eci.cvds.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Integer> {

    @Query("SELECT DISTINCT v.brand FROM Vehicle v")
    List<String> findDistinctBrand();

    @Query("SELECT DISTINCT v.model FROM Vehicle v WHERE v.brand = :brand")
    List<String> findDistinctModelByBrand(String brand);

    @Query("SELECT DISTINCT v.year FROM Vehicle v WHERE v.brand = :brand AND v.model = :model")
    List<Integer> findDistinctYearByBrandAndModel(String brand, String model);

    @Query("SELECT DISTINCT v.cylinderCapacity FROM Vehicle v WHERE v.brand = :brand AND v.model = :model AND v.year = :year")
    List<Integer> findDistinctCylinderCapacityByBrandAndModelAndYear(String brand, String model, int year);

    @Query("SELECT v FROM Vehicle v WHERE v.brand = :brand AND v.model = :model AND v.year = :year AND v.cylinderCapacity = :cylinderCapacity")
    Vehicle findByBrandAndModelAndYearAndCylinderCapacity(String brand, String model, int year, int cylinderCapacity);
}
