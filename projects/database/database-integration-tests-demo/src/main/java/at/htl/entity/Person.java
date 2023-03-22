package at.htl.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.time.LocalDate;

@Entity
public class Person extends PanacheEntity {

    public String name;

    @Column(name = "DATE_OF_BIRTH")
    public LocalDate dob;

}
