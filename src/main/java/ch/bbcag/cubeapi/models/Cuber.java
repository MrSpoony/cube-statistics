package ch.bbcag.cubeapi.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


@Entity
public class Cuber {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Size(max = 100)
    private String firstname;

    @Size(max = 100)
    private String lastname;

    @NotNull
    private Date birthdate;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country country;


    @NotNull
    @ManyToMany
    @JoinTable(name = "cuber_has_mainevent",
            joinColumns = @JoinColumn(name = "cuber_id"),
            inverseJoinColumns = @JoinColumn(name = "cube_id"))
    private Set<Event> mainevents = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cuber cuber = (Cuber) o;
        return id == cuber.id;
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

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }
}