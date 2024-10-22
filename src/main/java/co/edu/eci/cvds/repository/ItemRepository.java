package co.edu.eci.cvds.repository;

import co.edu.eci.cvds.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Integer> {

    @Query("SELECT DISTINCT i FROM Item i JOIN i.vehicles v INNER JOIN i.category c WHERE v.vehicleId = :vehicleId AND c.categoryId = :categoryId")
    List<Item> findItemsByVehicleIdAndCategoryId(Integer categoryId, Integer vehicleId);

}
