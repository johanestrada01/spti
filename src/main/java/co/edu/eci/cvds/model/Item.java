package co.edu.eci.cvds.model;

import co.edu.eci.cvds.exceptions.ModelException;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ITEM")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ITEM_ID")
    private Integer itemId;

    @Column(name = "ITEM_NAME", nullable = false)
    private String name;

    @Column(name = "SHORT_DESCRIPTION", nullable = false)
    private String shortDescription;

    @Column(name = "TECHNICAL_DESCRIPTION", nullable = false)
    private String technicalDescription;

    @Column(name = "IMAGE", nullable = false)
    private String image;

    @Column(name = "ITEM_VALUE", nullable = false)
    private Double value;

    @Column(name = "CURRENCY", nullable = false)
    private Double currency;

    @Column(name = "DISCOUNT", nullable = false)
    private Double discount;

    @Column(name = "TAX", nullable = false)
    private Double tax;

    @Column(name = "AVAILABILITY", nullable = false)
    private Boolean availability;

    @ManyToOne
    @JoinColumn(name = "CATEGORY_ID")
    private Category category;

    @ManyToMany(mappedBy = "quotationItems", fetch = FetchType.EAGER)
    private List<Quotation> quotations;

    @ManyToMany
    @JoinTable(
            name = "ITEM_VEHICLE",
            joinColumns = @JoinColumn(name = "ITEM_ID"),
            inverseJoinColumns = @JoinColumn(name = "VEHICLE_ID")
    )
    private List<Vehicle> vehicles;

    public Item() {
    }

    public Item(String name, String shortDescription, String technicalDescription, String image, Double value, Double currency, Double discount, Boolean availability, Double tax, Category category) throws ModelException {
        if(value <= 0) throw new ModelException(ModelException.ITEM_INVALID_VALUE);
        if(currency <= 0) throw new ModelException(ModelException.ITEM_INVALID_CURRENCY);
        if(discount < 0 || discount > 100) throw new ModelException(ModelException.ITEM_INVALID_DISCOUNT);
        if(tax < 0) throw new ModelException(ModelException.ITEM_INVALID_TAX);
        this.name = name;
        this.shortDescription = shortDescription;
        this.technicalDescription = technicalDescription;
        this.image = image;
        this.value = value;
        this.currency = currency;
        this.discount = discount;
        this.availability = availability;
        this.tax = tax;
        this.category = category;
        this.quotations = new ArrayList<Quotation>();
        this.vehicles = new ArrayList<Vehicle>();
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCategory(Category category){this.category = category;}

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getTechnicalDescription() {
        return technicalDescription;
    }

    public void setTechnicalDescription(String technicalDescription) {
        this.technicalDescription = technicalDescription;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Double getCurrency() {
        return currency;
    }

    public void setCurrency(Double currency) {
        this.currency = currency;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Double getTax() {
        return tax;
    }

    public void setTax(Double tax) {
        this.tax = tax;
    }

    public Boolean getAvailability() {
        return availability;
    }

    public void setAvailability(Boolean availability) {
        this.availability = availability;
    }

    public Category getCategory() {
        return category;
    }

    public void addCategory(Category category){
        this.category = category;
    }

    public List<Quotation> getQuotations() {
        return quotations;
    }

    public void setQuotations(List<Quotation> quotations) {
        this.quotations = quotations;
    }

    public void addQuotation(Quotation quotation){
        quotations.add(quotation);
    }

    public List<Vehicle> getVehicles() {
        return vehicles;
    }

    public void setVehicles(List<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }

    public boolean isAvailable(){
        return availability;
    }

    public void addVehicle(Vehicle vehicle){
        vehicles.add(vehicle);
    }
}