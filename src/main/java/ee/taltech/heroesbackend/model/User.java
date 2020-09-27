package ee.taltech.heroesbackend.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class User {

    private long id;
    private String name;
    private String email;
    private String password;
    private String creationDate;
    private String modificationDate;

    public User() {
        
    }

    @Id
    public long getId() {
        return id;
    }

    public User setId(long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public User setName(String name) {
        this.name = name;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public User setCreationDate(String creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public String getModificationDate() {
        return modificationDate;
    }

    public User setModificationDate(String modificationDate) {
        this.modificationDate = modificationDate;
        return this;
    }
}
