package ee.taltech.heroesbackend.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Entry {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private Long cost;
    private Long receiptId;
    private String category;
    private Long quantity;

    public Entry() {
        
    }

    public Long getId() {
        return id;
    }

    public Entry setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Entry setName(String name) {
        this.name = name;
        return this;
    }

    public Long getCost() {
        return cost;
    }

    public Entry setCost(Long cost) {
        this.cost = cost;
        return this;
    }

    public Long getReceiptId() {
        return receiptId;
    }

    public Entry setReceiptId(Long receiptId) {
        this.receiptId = receiptId;
        return this;
    }

    public String getCategory() {
        return category;
    }

    public Entry setCategory(String category) {
        this.category = category;
        return this;
    }

    public Long getQuantity() {
        return quantity;
    }

    public Entry setQuantity(Long quantity) {
        this.quantity = quantity;
        return this;
    }
}
