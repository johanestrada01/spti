package co.edu.eci.cvds.model;

import jakarta.persistence.*;

import java.sql.Array;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "QUOTATION")
public class Quotation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "QUOTATION_ID")
    private int quotationId;

    @Column(name = "CREATION_DATE", nullable = false, columnDefinition = "DATE")
    private LocalDate creationDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS", nullable = false)
    private QuotationStatus status;

    @Column(name = "SUBTOTAL", nullable = false)
    private Double subtotal;

    @Column(name = "TAXES", nullable = false)
    private Double taxes;

    @Column(name = "TOTAL", nullable = false)
    private Double total;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "QUOTATION_ITEM",
            joinColumns = @JoinColumn(name = "QUOTATION_ID"),
            inverseJoinColumns = @JoinColumn(name = "ITEM_ID")
    )
    private List<Item> quotationItems;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    public Quotation() {
        this.creationDate = LocalDate.now();
        this.status = QuotationStatus.CREADO;
        this.total = 0.0;
        this.subtotal = 0.0;
        this.taxes = 0.0;
        this.quotationItems = new ArrayList<Item>();
    }

    public int getQuotationId() {
        return quotationId;
    }

    public void setQuotationId(int quotationId) {
        this.quotationId = quotationId;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public QuotationStatus getStatus() {
        return status;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }

    public Double getTaxes() {
        return taxes;
    }

    public void setTaxes(Double taxes) {
        this.taxes = taxes;
    }

    public void updateStatus(QuotationStatus status){
        this.status = status;
    }

    public void addItem(Item item){
        quotationItems.add(item);
    }

    public void deleteItem(int index){
        quotationItems.remove(index);
    }

    public List<Item> getItems(){
        return quotationItems;
    }

    public void setItems(List<Item> quotationItems){
        this.quotationItems = quotationItems;
    }

}
