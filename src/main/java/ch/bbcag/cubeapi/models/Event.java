package ch.bbcag.cubeapi.models;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


@Entity
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    @Size(min = 1, max = 45)
    private String name;

    @NotNull
    private boolean wcalegal;

    @NotNull
    @ManyToMany(mappedBy = "mainevents")
    @JsonBackReference
    private Set<Cuber> cubersWithThisMainevent = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return id == event.id;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isWcalegal() {
        return wcalegal;
    }

    public void setWcalegal(boolean wcalegal) {
        this.wcalegal = wcalegal;
    }

    public Set<Cuber> getCubersWithThisMainevent() {
        return cubersWithThisMainevent;
    }

    public void setCubersWithThisMainevent(Set<Cuber> cubersWithThisMainevent) {
        this.cubersWithThisMainevent = cubersWithThisMainevent;
    }
}