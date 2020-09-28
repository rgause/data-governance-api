package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class DBSourceTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DBSource.class);
        DBSource dBSource1 = new DBSource();
        dBSource1.setId(1L);
        DBSource dBSource2 = new DBSource();
        dBSource2.setId(dBSource1.getId());
        assertThat(dBSource1).isEqualTo(dBSource2);
        dBSource2.setId(2L);
        assertThat(dBSource1).isNotEqualTo(dBSource2);
        dBSource1.setId(null);
        assertThat(dBSource1).isNotEqualTo(dBSource2);
    }
}
