package entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
@Entity
@NamedQuery(name = "OtherOneSide.deleteAllRows", query = "DELETE from OtherOneSide oos")
@Table(name = "OtherOneSide")
public class OtherOneSide {
        @Id
        @NotNull
        @Column(name = "id")
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int id;
        @NotNull
        @Column(name = "name")
        private String name;

        @OneToOne(mappedBy = "otherOneSide", cascade = CascadeType.PERSIST)
        private OneSide oneSide;

    public OtherOneSide() {
    }

    public OtherOneSide(String name) {
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

    public OneSide getOneSide() {
        return oneSide;
    }

    public void setOneSide(OneSide oneSide) {
        this.oneSide = oneSide;
    }

    @Override
    public String toString() {
        return "OtherOneSide{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
