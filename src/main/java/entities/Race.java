package entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@NamedQuery(name = "Race.deleteAllRows", query = "DELETE from Race r")
@Table(name = "Race")
public class Race {
    @Id
    @NotNull
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotNull
    @Column(name = "name")
    private String name;

    @NotNull
    @Column(name = "location")
    private String location;

    @NotNull
    @Column(name = "startDate")
    private String startDate;

    @NotNull
    @Column(name = "duration")
    private String duration;



    @JoinTable(name = "car_race", joinColumns = {
            @JoinColumn(name = "cars", referencedColumnName = "id")}, inverseJoinColumns = {
            @JoinColumn(name = "races", referencedColumnName = "id")})
    @ManyToMany(cascade = CascadeType.PERSIST)
    private List<Car> cars = new ArrayList<>();

    public Race() {
    }

    public Race(String name) {
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

    public List<Car> getCars() {
        return cars;
    }

    public void setCars(List<Car> cars) {
        this.cars = cars;
    }

    public void addToCars(Car car) {
        this.cars.add(car);
    }

    @Override
    public String toString() {
        return "OtherManySide{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
