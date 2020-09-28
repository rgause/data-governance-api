package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class ConcernTypeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ConcernType.class);
        ConcernType concernType1 = new ConcernType();
        concernType1.setId(1L);
        ConcernType concernType2 = new ConcernType();
        concernType2.setId(concernType1.getId());
        assertThat(concernType1).isEqualTo(concernType2);
        concernType2.setId(2L);
        assertThat(concernType1).isNotEqualTo(concernType2);
        concernType1.setId(null);
        assertThat(concernType1).isNotEqualTo(concernType2);
    }
}
