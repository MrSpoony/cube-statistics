package ch.bbcag.cubeapi.models;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;


@Entity
public class Time {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private Float time;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "cuber_id")
    @JsonBackReference
    private Cuber cuber;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "competition_id")
    private Competition competition;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "cube_id")
    private Cube cube;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Time time = (Time) o;
        return id == time.id;
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

    public Float getTime() {
        return time;
    }

    public void setTime(Float time) {
        this.time = time;
    }

    public Cuber getCuber() {
        return cuber;
    }

    public void setCuber(Cuber cuber) {
        this.cuber = cuber;
    }

    public Competition getCompetition() {
        return competition;
    }

    public void setCompetition(Competition competition) {
        this.competition = competition;
    }

    public Cube getCube() {
        return cube;
    }

    public void setCube(Cube cube) {
        this.cube = cube;
    }
}