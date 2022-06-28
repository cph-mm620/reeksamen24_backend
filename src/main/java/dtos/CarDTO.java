package dtos;
import entities.Car;
import entities.OneSide;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CarDTO {
    private int id;
    private String name;
    private OneSideDTO oneSide;
    private List<RaceDTO> races = new ArrayList<>();

    public CarDTO(String name) {
        this.name = name;
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
            this.races = RaceDTO.getDtos(c.getRaces());
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

    public OneSideDTO getOneSide() {
        return oneSide;
    }

    public void setOneSide(OneSideDTO oneSide) {
        this.oneSide = oneSide;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CarDTO that = (CarDTO) o;
        return id == that.id && name.equals(that.name) && oneSide.equals(that.oneSide) && races.equals(that.races);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, oneSide, races);
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
