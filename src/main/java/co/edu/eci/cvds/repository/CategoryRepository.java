package co.edu.eci.cvds.repository;

import co.edu.eci.cvds.model.Category;
import co.edu.eci.cvds.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
