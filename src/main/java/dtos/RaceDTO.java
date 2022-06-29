package dtos;
import entities.Race;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RaceDTO {
    private int id;
    private String name;
    private String location;
    private String duration;

    public RaceDTO(String name) {
        this.name = name;
    }

    public static List<RaceDTO> getDtos(List<Race> cars){
        List<RaceDTO> rdtos = new ArrayList<>();
        cars.forEach(car -> rdtos.add(new RaceDTO(car)));
        return rdtos;
    }

    public RaceDTO(Race r){
        if(r != null){
            this.id = r.getId();
            this.name = r.getName();
            this.location = r.getLocation();
            this.duration = r.getDuration();

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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RaceDTO that = (RaceDTO) o;
        return id == that.id && name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "Race{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", duration='" + duration + '\'' +
                '}';
    }
}
