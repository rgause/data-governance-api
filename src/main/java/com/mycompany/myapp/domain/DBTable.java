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
 * A DBTable.
 */
@Entity
@Table(name = "db_table")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class DBTable implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    
    @Column(name = "table_name", unique = true)
    private String tableName;

    @ManyToOne
    @JsonIgnoreProperties(value = "dBTables", allowSetters = true)
    private DBDatabase database;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(name = "db_table_concern",
               joinColumns = @JoinColumn(name = "dbtable_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "concern_id", referencedColumnName = "id"))
    private Set<Concern> concerns = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTableName() {
        return tableName;
    }

    public DBTable tableName(String tableName) {
        this.tableName = tableName;
        return this;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public DBDatabase getDatabase() {
        return database;
    }

    public DBTable database(DBDatabase dBDatabase) {
        this.database = dBDatabase;
        return this;
    }

    public void setDatabase(DBDatabase dBDatabase) {
        this.database = dBDatabase;
    }

    public Set<Concern> getConcerns() {
        return concerns;
    }

    public DBTable concerns(Set<Concern> concerns) {
        this.concerns = concerns;
        return this;
    }

    public DBTable addConcern(Concern concern) {
        this.concerns.add(concern);
        concern.getTables().add(this);
        return this;
    }

    public DBTable removeConcern(Concern concern) {
        this.concerns.remove(concern);
        concern.getTables().remove(this);
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
        if (!(o instanceof DBTable)) {
            return false;
        }
        return id != null && id.equals(((DBTable) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DBTable{" +
            "id=" + getId() +
            ", tableName='" + getTableName() + "'" +
            "}";
    }
}
