package dtos;
import entities.Driver;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DriverDTO {
    private int id;
    private String name;


    public DriverDTO(String name) {
        this.name = name;
    }

    public static List<DriverDTO> getDtos(List<Driver> drivers){
        List<DriverDTO> ddtos = new ArrayList<>();
        drivers.forEach(driver -> ddtos.add(new DriverDTO(driver)));
        return ddtos;
    }

    public DriverDTO(Driver d){
        if(d != null){
            this.id = d.getId();
            this.name = d.getName();
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



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DriverDTO that = (DriverDTO) o;
        return id == that.id && name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "Driver{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
