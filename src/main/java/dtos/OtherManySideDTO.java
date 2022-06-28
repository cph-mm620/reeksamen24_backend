package dtos;
import entities.OtherManySide;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class OtherManySideDTO {
    private int id;
    private String name;

    public OtherManySideDTO(String name) {
        this.name = name;
    }

    public static List<OtherManySideDTO> getDtos(List<OtherManySide> manySides){
        List<OtherManySideDTO> omsdtos = new ArrayList<>();
        manySides.forEach(manySide -> omsdtos.add(new OtherManySideDTO(manySide)));
        return omsdtos;
    }

    public OtherManySideDTO(OtherManySide oms){
        if(oms != null){
            this.id = oms.getId();
            this.name = oms.getName();
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
        OtherManySideDTO that = (OtherManySideDTO) o;
        return id == that.id && name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "OtherManySide{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
