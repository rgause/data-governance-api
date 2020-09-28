package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A DBColumn.
 */
@Entity
@Table(name = "db_column")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class DBColumn implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    
    @Column(name = "column_name", unique = true)
    private String columnName;

    @ManyToOne
    @JsonIgnoreProperties(value = "dBColumns", allowSetters = true)
    private DBTable table;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(name = "db_column_concern",
               joinColumns = @JoinColumn(name = "dbcolumn_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "concern_id", referencedColumnName = "id"))
    private Set<Concern> concerns = new HashSet<>();

    @ManyToMany(mappedBy = "columns")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnore
    private Set<Persona> personas = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getColumnName() {
        return columnName;
    }

    public DBColumn columnName(String columnName) {
        this.columnName = columnName;
        return this;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public DBTable getTable() {
        return table;
    }

    public DBColumn table(DBTable dBTable) {
        this.table = dBTable;
        return this;
    }

    public void setTable(DBTable dBTable) {
        this.table = dBTable;
    }

    public Set<Concern> getConcerns() {
        return concerns;
    }

    public DBColumn concerns(Set<Concern> concerns) {
        this.concerns = concerns;
        return this;
    }

    public DBColumn addConcern(Concern concern) {
        this.concerns.add(concern);
        concern.getColumns().add(this);
        return this;
    }

    public DBColumn removeConcern(Concern concern) {
        this.concerns.remove(concern);
        concern.getColumns().remove(this);
        return this;
    }

    public void setConcerns(Set<Concern> concerns) {
        this.concerns = concerns;
    }

    public Set<Persona> getPersonas() {
        return personas;
    }

    public DBColumn personas(Set<Persona> personas) {
        this.personas = personas;
        return this;
    }

    public DBColumn addPersona(Persona persona) {
        this.personas.add(persona);
        persona.getColumns().add(this);
        return this;
    }

    public DBColumn removePersona(Persona persona) {
        this.personas.remove(persona);
        persona.getColumns().remove(this);
        return this;
    }

    public void setPersonas(Set<Persona> personas) {
        this.personas = personas;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DBColumn)) {
            return false;
        }
        return id != null && id.equals(((DBColumn) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DBColumn{" +
            "id=" + getId() +
            ", columnName='" + getColumnName() + "'" +
            "}";
    }
}
