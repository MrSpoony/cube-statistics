package ch.bbcag.cubeapi.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;


@Entity
@Table(name = "cuber_has_cube")
public class CuberCubes {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "cuber_id")
    private Cuber cuber;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "cube_id")
    private Cube cube;

    @NotNull
    private boolean maincube;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CuberCubes that = (CuberCubes) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Cuber getCuber() {
        return cuber;
    }

    public void setCuber(Cuber cuber) {
        this.cuber = cuber;
    }

    public Cube getCube() {
        return cube;
    }

    public void setCube(Cube cube) {
        this.cube = cube;
    }

    public boolean isMaincube() {
        return maincube;
    }

    public void setMaincube(boolean maincube) {
        this.maincube = maincube;
    }
}