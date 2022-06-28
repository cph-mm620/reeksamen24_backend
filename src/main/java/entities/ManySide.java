package entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@NamedQuery(name = "ManySide.deleteAllRows", query = "DELETE from ManySide ms")
@Table(name = "ManySide")
public class ManySide {
    @Id
    @NotNull
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotNull
    @Column(name = "name")
    private String name;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "oneSide_id", referencedColumnName = "id", nullable = false)
    private OneSide oneSide;

    @ManyToMany(mappedBy = "manySides", cascade = CascadeType.PERSIST)
    private List<OtherManySide> otherManySides = new ArrayList<>();

    public ManySide() {
    }

    //Just for the test class
    public ManySide(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public ManySide(String name) {
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

    public OneSide getOneSide() {
        return oneSide;
    }

    public void setOneSide(OneSide oneSide) {
        this.oneSide = oneSide;
    }

    public List<OtherManySide> getOtherManySides() {
        return otherManySides;
    }

    public void setOtherManySides(List<OtherManySide> otherManySides) {
        this.otherManySides = otherManySides;
    }

    public void addToOtherManySides(OtherManySide otherManySide) {
        this.otherManySides.add(otherManySide);
        otherManySide.addToManySides(this);
    }

    @Override
    public String toString() {
        return "ManySide{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", oneSide=" + oneSide +
                ", otherManySides=" + otherManySides +
                '}';
    }
}
