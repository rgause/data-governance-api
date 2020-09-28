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
 * A Concern.
 */
@Entity
@Table(name = "concern")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Concern implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    
    @Column(name = "concern_description", unique = true)
    private String concernDescription;

    @ManyToOne
    @JsonIgnoreProperties(value = "concerns", allowSetters = true)
    private ConcernType concernType;

    @ManyToMany(mappedBy = "concerns")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnore
    private Set<DBColumn> columns = new HashSet<>();

    @ManyToMany(mappedBy = "concerns")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnore
    private Set<DBTable> tables = new HashSet<>();

    @ManyToMany(mappedBy = "concerns")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnore
    private Set<DBDatabase> databases = new HashSet<>();

    @ManyToMany(mappedBy = "concerns")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnore
    private Set<DBSource> sources = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getConcernDescription() {
        return concernDescription;
    }

    public Concern concernDescription(String concernDescription) {
        this.concernDescription = concernDescription;
        return this;
    }

    public void setConcernDescription(String concernDescription) {
        this.concernDescription = concernDescription;
    }

    public ConcernType getConcernType() {
        return concernType;
    }

    public Concern concernType(ConcernType concernType) {
        this.concernType = concernType;
        return this;
    }

    public void setConcernType(ConcernType concernType) {
        this.concernType = concernType;
    }

    public Set<DBColumn> getColumns() {
        return columns;
    }

    public Concern columns(Set<DBColumn> dBColumns) {
        this.columns = dBColumns;
        return this;
    }

    public Concern addColumn(DBColumn dBColumn) {
        this.columns.add(dBColumn);
        dBColumn.getConcerns().add(this);
        return this;
    }

    public Concern removeColumn(DBColumn dBColumn) {
        this.columns.remove(dBColumn);
        dBColumn.getConcerns().remove(this);
        return this;
    }

    public void setColumns(Set<DBColumn> dBColumns) {
        this.columns = dBColumns;
    }

    public Set<DBTable> getTables() {
        return tables;
    }

    public Concern tables(Set<DBTable> dBTables) {
        this.tables = dBTables;
        return this;
    }

    public Concern addTable(DBTable dBTable) {
        this.tables.add(dBTable);
        dBTable.getConcerns().add(this);
        return this;
    }

    public Concern removeTable(DBTable dBTable) {
        this.tables.remove(dBTable);
        dBTable.getConcerns().remove(this);
        return this;
    }

    public void setTables(Set<DBTable> dBTables) {
        this.tables = dBTables;
    }

    public Set<DBDatabase> getDatabases() {
        return databases;
    }

    public Concern databases(Set<DBDatabase> dBDatabases) {
        this.databases = dBDatabases;
        return this;
    }

    public Concern addDatabase(DBDatabase dBDatabase) {
        this.databases.add(dBDatabase);
        dBDatabase.getConcerns().add(this);
        return this;
    }

    public Concern removeDatabase(DBDatabase dBDatabase) {
        this.databases.remove(dBDatabase);
        dBDatabase.getConcerns().remove(this);
        return this;
    }

    public void setDatabases(Set<DBDatabase> dBDatabases) {
        this.databases = dBDatabases;
    }

    public Set<DBSource> getSources() {
        return sources;
    }

    public Concern sources(Set<DBSource> dBSources) {
        this.sources = dBSources;
        return this;
    }

    public Concern addSource(DBSource dBSource) {
        this.sources.add(dBSource);
        dBSource.getConcerns().add(this);
        return this;
    }

    public Concern removeSource(DBSource dBSource) {
        this.sources.remove(dBSource);
        dBSource.getConcerns().remove(this);
        return this;
    }

    public void setSources(Set<DBSource> dBSources) {
        this.sources = dBSources;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Concern)) {
            return false;
        }
        return id != null && id.equals(((Concern) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Concern{" +
            "id=" + getId() +
            ", concernDescription='" + getConcernDescription() + "'" +
            "}";
    }
}
