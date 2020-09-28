package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class DBColumnTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DBColumn.class);
        DBColumn dBColumn1 = new DBColumn();
        dBColumn1.setId(1L);
        DBColumn dBColumn2 = new DBColumn();
        dBColumn2.setId(dBColumn1.getId());
        assertThat(dBColumn1).isEqualTo(dBColumn2);
        dBColumn2.setId(2L);
        assertThat(dBColumn1).isNotEqualTo(dBColumn2);
        dBColumn1.setId(null);
        assertThat(dBColumn1).isNotEqualTo(dBColumn2);
    }
}
