package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class ConcernTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Concern.class);
        Concern concern1 = new Concern();
        concern1.setId(1L);
        Concern concern2 = new Concern();
        concern2.setId(concern1.getId());
        assertThat(concern1).isEqualTo(concern2);
        concern2.setId(2L);
        assertThat(concern1).isNotEqualTo(concern2);
        concern1.setId(null);
        assertThat(concern1).isNotEqualTo(concern2);
    }
}
