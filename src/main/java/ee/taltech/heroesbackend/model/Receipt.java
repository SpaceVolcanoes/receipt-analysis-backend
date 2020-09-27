package ee.taltech.heroesbackend.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Receipt {


    private Long id;
    private long userId;
    private String modificationDate;
    private String creationData;
    private String issuer;

    public Receipt() {

    }

    public long getUserId() {
        return userId;
    }

    public Receipt setUserId(long userId) {
        this.userId = userId;
        return this;
    }

    public String getModificationDate() {
        return modificationDate;
    }

    public Receipt setModificationDate(String modificationDate) {
        this.modificationDate = modificationDate;
        return this;
    }

    public String getCreationData() {
        return creationData;
    }

    public Receipt setCreationData(String creationData) {
        this.creationData = creationData;
        return this;
    }

    public String getIssuer() {
        return issuer;
    }

    public Receipt setIssuer(String issuer) {
        this.issuer = issuer;
        return this;
    }

    public Receipt setId(Long id) {
        this.id = id;
        return this;
    }

    @Id
    public Long getId() {
        return id;
    }
}
