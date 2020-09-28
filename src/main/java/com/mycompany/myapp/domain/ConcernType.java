package com.mycompany.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A ConcernType.
 */
@Entity
@Table(name = "concern_type")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ConcernType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    
    @Column(name = "concern_type_name", unique = true)
    private String concernTypeName;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getConcernTypeName() {
        return concernTypeName;
    }

    public ConcernType concernTypeName(String concernTypeName) {
        this.concernTypeName = concernTypeName;
        return this;
    }

    public void setConcernTypeName(String concernTypeName) {
        this.concernTypeName = concernTypeName;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ConcernType)) {
            return false;
        }
        return id != null && id.equals(((ConcernType) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ConcernType{" +
            "id=" + getId() +
            ", concernTypeName='" + getConcernTypeName() + "'" +
            "}";
    }
}
