package entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@NamedQuery(name = "Driver.deleteAllRows", query = "DELETE from Driver d")
@Table(name = "Driver")
public class Driver {
    @Id
    @NotNull
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotNull
    @Column(name = "name")
    private String name;

    @NotNull
    @Column(name = "birthYear")
    private int birthYear;

    @NotNull
    @Column(name = "experience")
    private String experience;

    @NotNull
    @Column(name = "gender")
    private String gender;



    @OneToMany(mappedBy = "driver", cascade = CascadeType.PERSIST)
    private List<Car> car = new ArrayList<>();

    public Driver() {
    }

    public Driver(String name, int birthYear, String experience, String gender) {
        this.name = name;
        this.birthYear = birthYear;
        this.experience = experience;
        this.gender = gender;
    }
    public Driver(String name) {
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


    public List<Car> getCar() {
        return car;
    }

    public void setCar(List<Car> car) {
        this.car = car;
    }

    public void addToCar(Car car) {
        this.car.add(car);
        car.setDriver(this);
    }

    @Override
    public String toString() {
        return "Driver{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
