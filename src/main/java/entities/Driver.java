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




    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "car_id", referencedColumnName = "id", nullable = false)
    private Car car;

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


    public int getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(int birthYear) {
        this.birthYear = birthYear;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setCar(Car car) {
        this.car = car;
    }



    @Override
    public String toString() {
        return "Driver{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
