package ee.taltech.heroesbackend.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Entry {

    private long id;
    private String name;
    private long cost;
    private long receiptId;
    private String category;
    private long quantity;

    public Entry() {
        
    }

    @Id
    public long getId() {
        return id;
    }

    public Entry setId(long id) {
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

    public long getCost() {
        return cost;
    }

    public Entry setCost(long cost) {
        this.cost = cost;
        return this;
    }

    public long getReceiptId() {
        return receiptId;
    }

    public Entry setReceiptId(long receiptId) {
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

    public long getQuantity() {
        return quantity;
    }

    public Entry setQuantity(long quantity) {
        this.quantity = quantity;
        return this;
    }
}
