package entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@NamedQuery(name = "Car.deleteAllRows", query = "DELETE from Car c")
@Table(name = "Car")
public class Car {
    @Id
    @NotNull
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotNull
    @Column(name = "name")
    private String name;

    @NotNull
    @Column(name = "brand")
    private String brand;

    @NotNull
    @Column(name = "color")
    private String color;

    @NotNull
    @Column(name = "sponsor")
    private String sponsor;

    @NotNull
    @Column(name = "make")
    private String make;

    @NotNull
    @Column(name = "year")
    private int year;



    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "oneSide_id", referencedColumnName = "id", nullable = false)
    private OneSide oneSide;

    @ManyToMany(mappedBy = "cars", cascade = CascadeType.PERSIST)
    private List<Race> races = new ArrayList<>();

    public Car() {
    }

    //Just for the test class
    public Car(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Car(String name) {
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

    public List<Race> getRaces() {
        return races;
    }

    public void setRaces(List<Race> races) {
        this.races = races;
    }

    public void addToRaces(Race race) {
        this.races.add(race);
        race.addToCars(this);
    }

    @Override
    public String toString() {
        return "Car{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", oneSide=" + oneSide +
                ", races=" + races +
                '}';
    }
}
