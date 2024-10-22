package co.edu.eci.cvds.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "CATEGORY")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CATEGORY_ID")
    private Integer categoryId;

    @Column(name = "CATEGORY_NAME", nullable = false)
    private String name;

    @OneToMany(mappedBy = "category")
    private List<Item> items;

    public Category() {
    }

    public Category(String name) {
        this.categoryId = 0;
        this.name = name;
        this.items = new ArrayList<Item>();
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
