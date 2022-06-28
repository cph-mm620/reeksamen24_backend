package dtos;
import entities.Car;
import entities.Driver;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CarDTO {
    private int id;
    private String name;
    private String brand;
    //private DriverDTO driver;
    private List<RaceDTO> races = new ArrayList<>();
    private List<DriverDTO> drivers = new ArrayList<>();

    public CarDTO(String name, String brand) {
        this.name = name;
        this.brand = brand;
    }



    public static List<CarDTO> getDtos(List<Car> cars){
        List<CarDTO> cdtos = new ArrayList<>();
        cars.forEach(car -> cdtos.add(new CarDTO(car)));
        return cdtos;
    }

    public CarDTO(Car c){
        if(c != null){
            this.id = c.getId();
            this.name = c.getName();
            this.brand = c.getBrand();
            this.races = RaceDTO.getDtos(c.getRaces());
            this.drivers = DriverDTO.getDtos(c.getDriver());
        }
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

    public List<DriverDTO> getDriver() {
        return drivers;
    }

    public void setDriver(List<DriverDTO> drivers) {
        this.drivers = drivers;
    }

    public List<RaceDTO> getRaces() {
        return races;
    }

    public void setRaces(List<RaceDTO> races) {
        this.races = races;
    }

    public void addToRaces(RaceDTO race) {
        this.races.add(race);
    }

    public void addToDrivers(DriverDTO driver) {
        this.drivers.add(driver);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CarDTO that = (CarDTO) o;
        return id == that.id && name.equals(that.name) && drivers.equals(that.drivers) && races.equals(that.races);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, drivers, races);
    }

    @Override
    public String toString() {
        return "Car{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", drivers=" + drivers +
                ", races=" + races +
                '}';
    }
}
