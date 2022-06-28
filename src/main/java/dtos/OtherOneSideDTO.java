package dtos;
import entities.OtherOneSide;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class OtherOneSideDTO {
    private int id;
    private String name;

    public OtherOneSideDTO(String name) {
        this.name = name;
    }

    public static List<OtherOneSideDTO> getDtos(List<OtherOneSide> otherOneSides){
        List<OtherOneSideDTO> oosdtos = new ArrayList<>();
        otherOneSides.forEach(otherOneSide -> oosdtos.add(new OtherOneSideDTO(otherOneSide)));
        return oosdtos;
    }

    public OtherOneSideDTO(OtherOneSide oos){
        if(oos != null){
            this.id = oos.getId();
            this.name = oos.getName();
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
        OtherOneSideDTO that = (OtherOneSideDTO) o;
        return id == that.id && name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "OtherOneSide{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
