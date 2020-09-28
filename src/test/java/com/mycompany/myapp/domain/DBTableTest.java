package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class DBTableTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DBTable.class);
        DBTable dBTable1 = new DBTable();
        dBTable1.setId(1L);
        DBTable dBTable2 = new DBTable();
        dBTable2.setId(dBTable1.getId());
        assertThat(dBTable1).isEqualTo(dBTable2);
        dBTable2.setId(2L);
        assertThat(dBTable1).isNotEqualTo(dBTable2);
        dBTable1.setId(null);
        assertThat(dBTable1).isNotEqualTo(dBTable2);
    }
}
