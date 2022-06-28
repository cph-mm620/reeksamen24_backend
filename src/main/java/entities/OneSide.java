package entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@NamedQuery(name = "OneSide.deleteAllRows", query = "DELETE from OneSide o")
@Table(name = "OneSide")
public class OneSide {
    @Id
    @NotNull
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotNull
    @Column(name = "name")
    private String name;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "otherOneSideID", referencedColumnName = "id")
    private OtherOneSide otherOneSide;

    @OneToMany(mappedBy = "oneSide", cascade = CascadeType.PERSIST)
    private List<ManySide> manySide = new ArrayList<>();

    public OneSide() {
    }

    public OneSide(String name) {
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

    public OtherOneSide getOtherOneSide() {
        return otherOneSide;
    }

    public void setOtherOneSide(OtherOneSide otherOneSide) {
        this.otherOneSide = otherOneSide;
    }

    public List<ManySide> getManySide() {
        return manySide;
    }

    public void setManySide(List<ManySide> manySide) {
        this.manySide = manySide;
    }

    public void addToManySide(ManySide manySide) {
        this.manySide.add(manySide);
        manySide.setOneSide(this);
    }

    @Override
    public String toString() {
        return "OneSide{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", otherOneSide=" + otherOneSide +
                '}';
    }
}
