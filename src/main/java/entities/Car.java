package entities;

import dtos.RaceDTO;

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
    @Column(name = "year")
    private int year;



    @OneToMany(mappedBy = "car", cascade = CascadeType.PERSIST)
    @JoinColumn(name = "driver_id", referencedColumnName = "id", nullable = false)
    private List<Driver> driver = new ArrayList<>();


    @ManyToMany(mappedBy = "cars", cascade = CascadeType.PERSIST)
    private List<Race> races = new ArrayList<>();

    public Car() {
    }

    public Car(String name, String brand, String color, String sponsor, int year) {
        this.name = name;
        this.brand = brand;
        this.color = color;
        this.sponsor = sponsor;
        this.year = year;
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

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSponsor() {
        return sponsor;
    }

    public void setSponsor(String sponsor) {
        this.sponsor = sponsor;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public List<Driver> getDriver() {
        return driver;
    }

    public void setDriver(List<Driver> driver) {
        this.driver = driver;
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

    public void addToDriver(Driver driver) {
        this.driver.add(driver);
        driver.setCar(this);
    }

    @Override
    public String toString() {
        return "Car{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", driver=" + driver +
                ", races=" + races +
                '}';
    }
}
