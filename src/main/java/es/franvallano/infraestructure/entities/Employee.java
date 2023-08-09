package es.franvallano.infraestructure.entities;

import io.quarkus.hibernate.reactive.panache.PanacheEntity;
import jakarta.persistence.Cacheable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
@Cacheable
public class Employee extends PanacheEntity {
    @Column(nullable = false)
    public String name;
    @Column(nullable = false)
    public String surname;
    @Column(nullable = false)
    public String email;
    @Column(nullable = false)
    public Integer age;
}
