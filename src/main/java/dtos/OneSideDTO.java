package dtos;
import entities.OneSide;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class OneSideDTO {
    private int id;
    private String name;
    private OtherOneSideDTO otherOneSide;

    public OneSideDTO(String name) {
        this.name = name;
    }

    public static List<OneSideDTO> getDtos(List<OneSide> oneSides){
        List<OneSideDTO> osdtos = new ArrayList<>();
        oneSides.forEach(oneSide -> osdtos.add(new OneSideDTO(oneSide)));
        return osdtos;
    }

    public OneSideDTO(OneSide o){
        if(o != null){
            this.id = o.getId();
            this.name = o.getName();
            this.otherOneSide = new OtherOneSideDTO(o.getOtherOneSide());
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

    public OtherOneSideDTO getOtherOneSide() {
        return otherOneSide;
    }

    public void setOtherOneSide(OtherOneSideDTO otherOneSide) {
        this.otherOneSide = otherOneSide;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OneSideDTO that = (OneSideDTO) o;
        return id == that.id && name.equals(that.name) && otherOneSide.equals(that.otherOneSide);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, otherOneSide);
    }

    @Override
    public String toString() {
        return "OneSide{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", otherOneSide=" + otherOneSide +
                '}';
    }
}
