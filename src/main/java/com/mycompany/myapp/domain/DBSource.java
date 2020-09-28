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
 * A DBSource.
 */
@Entity
@Table(name = "db_source")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class DBSource implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    
    @Column(name = "source_name", unique = true)
    private String sourceName;

    @ManyToOne
    @JsonIgnoreProperties(value = "dBSources", allowSetters = true)
    private DBFamily family;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(name = "db_source_concern",
               joinColumns = @JoinColumn(name = "dbsource_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "concern_id", referencedColumnName = "id"))
    private Set<Concern> concerns = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSourceName() {
        return sourceName;
    }

    public DBSource sourceName(String sourceName) {
        this.sourceName = sourceName;
        return this;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public DBFamily getFamily() {
        return family;
    }

    public DBSource family(DBFamily dBFamily) {
        this.family = dBFamily;
        return this;
    }

    public void setFamily(DBFamily dBFamily) {
        this.family = dBFamily;
    }

    public Set<Concern> getConcerns() {
        return concerns;
    }

    public DBSource concerns(Set<Concern> concerns) {
        this.concerns = concerns;
        return this;
    }

    public DBSource addConcern(Concern concern) {
        this.concerns.add(concern);
        concern.getSources().add(this);
        return this;
    }

    public DBSource removeConcern(Concern concern) {
        this.concerns.remove(concern);
        concern.getSources().remove(this);
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
        if (!(o instanceof DBSource)) {
            return false;
        }
        return id != null && id.equals(((DBSource) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DBSource{" +
            "id=" + getId() +
            ", sourceName='" + getSourceName() + "'" +
            "}";
    }
}
