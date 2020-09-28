package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A DBDatabase.
 */
@Entity
@Table(name = "db_database")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class DBDatabase implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    
    @Column(name = "database_name", unique = true)
    private String databaseName;

    @ManyToOne
    @JsonIgnoreProperties(value = "dBDatabases", allowSetters = true)
    private DBSource source;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(name = "db_database_concern",
               joinColumns = @JoinColumn(name = "dbdatabase_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "concern_id", referencedColumnName = "id"))
    private Set<Concern> concerns = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public DBDatabase databaseName(String databaseName) {
        this.databaseName = databaseName;
        return this;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    public DBSource getSource() {
        return source;
    }

    public DBDatabase source(DBSource dBSource) {
        this.source = dBSource;
        return this;
    }

    public void setSource(DBSource dBSource) {
        this.source = dBSource;
    }

    public Set<Concern> getConcerns() {
        return concerns;
    }

    public DBDatabase concerns(Set<Concern> concerns) {
        this.concerns = concerns;
        return this;
    }

    public DBDatabase addConcern(Concern concern) {
        this.concerns.add(concern);
        concern.getDatabases().add(this);
        return this;
    }

    public DBDatabase removeConcern(Concern concern) {
        this.concerns.remove(concern);
        concern.getDatabases().remove(this);
        return this;
    }

    public void setConcerns(Set<Concern> concerns) {
        this.concerns = concerns;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DBDatabase)) {
            return false;
        }
        return id != null && id.equals(((DBDatabase) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DBDatabase{" +
            "id=" + getId() +
            ", databaseName='" + getDatabaseName() + "'" +
            "}";
    }
}
