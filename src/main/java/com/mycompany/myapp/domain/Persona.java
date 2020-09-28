package com.mycompany.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Persona.
 */
@Entity
@Table(name = "persona")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Persona implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    
    @Column(name = "persona_name", unique = true)
    private String personaName;

    @Column(name = "security_group_name")
    private String securityGroupName;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(name = "persona_column",
               joinColumns = @JoinColumn(name = "persona_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "column_id", referencedColumnName = "id"))
    private Set<DBColumn> columns = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPersonaName() {
        return personaName;
    }

    public Persona personaName(String personaName) {
        this.personaName = personaName;
        return this;
    }

    public void setPersonaName(String personaName) {
        this.personaName = personaName;
    }

    public String getSecurityGroupName() {
        return securityGroupName;
    }

    public Persona securityGroupName(String securityGroupName) {
        this.securityGroupName = securityGroupName;
        return this;
    }

    public void setSecurityGroupName(String securityGroupName) {
        this.securityGroupName = securityGroupName;
    }

    public Set<DBColumn> getColumns() {
        return columns;
    }

    public Persona columns(Set<DBColumn> dBColumns) {
        this.columns = dBColumns;
        return this;
    }

    public Persona addColumn(DBColumn dBColumn) {
        this.columns.add(dBColumn);
        dBColumn.getPersonas().add(this);
        return this;
    }

    public Persona removeColumn(DBColumn dBColumn) {
        this.columns.remove(dBColumn);
        dBColumn.getPersonas().remove(this);
        return this;
    }

    public void setColumns(Set<DBColumn> dBColumns) {
        this.columns = dBColumns;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Persona)) {
            return false;
        }
        return id != null && id.equals(((Persona) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Persona{" +
            "id=" + getId() +
            ", personaName='" + getPersonaName() + "'" +
            ", securityGroupName='" + getSecurityGroupName() + "'" +
            "}";
    }
}
