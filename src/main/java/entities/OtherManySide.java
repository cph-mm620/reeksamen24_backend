package entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@NamedQuery(name = "OtherManySide.deleteAllRows", query = "DELETE from OtherManySide oms")
@Table(name = "OtherManySide")
public class OtherManySide {
    @Id
    @NotNull
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotNull
    @Column(name = "name")
    private String name;

    @JoinTable(name = "manyToMany", joinColumns = {
            @JoinColumn(name = "manySides", referencedColumnName = "id")}, inverseJoinColumns = {
            @JoinColumn(name = "otherManySides", referencedColumnName = "id")})
    @ManyToMany(cascade = CascadeType.PERSIST)
    private List<ManySide> manySides = new ArrayList<>();

    public OtherManySide() {
    }

    public OtherManySide(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ManySide> getManySides() {
        return manySides;
    }

    public void setManySides(List<ManySide> manySides) {
        this.manySides = manySides;
    }

    public void addToManySides(ManySide manySide) {
        this.manySides.add(manySide);
    }

    @Override
    public String toString() {
        return "OtherManySide{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
