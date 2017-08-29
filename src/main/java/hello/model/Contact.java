package hello.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="contacts")
public class Contact implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "name")
    private String name;

    protected Contact(){
    }

    public Contact(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "contact " + id + " " + name;
    }
}
