package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class DBFamilyTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DBFamily.class);
        DBFamily dBFamily1 = new DBFamily();
        dBFamily1.setId(1L);
        DBFamily dBFamily2 = new DBFamily();
        dBFamily2.setId(dBFamily1.getId());
        assertThat(dBFamily1).isEqualTo(dBFamily2);
        dBFamily2.setId(2L);
        assertThat(dBFamily1).isNotEqualTo(dBFamily2);
        dBFamily1.setId(null);
        assertThat(dBFamily1).isNotEqualTo(dBFamily2);
    }
}
