package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class DBDatabaseTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DBDatabase.class);
        DBDatabase dBDatabase1 = new DBDatabase();
        dBDatabase1.setId(1L);
        DBDatabase dBDatabase2 = new DBDatabase();
        dBDatabase2.setId(dBDatabase1.getId());
        assertThat(dBDatabase1).isEqualTo(dBDatabase2);
        dBDatabase2.setId(2L);
        assertThat(dBDatabase1).isNotEqualTo(dBDatabase2);
        dBDatabase1.setId(null);
        assertThat(dBDatabase1).isNotEqualTo(dBDatabase2);
    }
}
