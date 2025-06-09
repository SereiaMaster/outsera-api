package com.outsera.movie.service;

import com.outsera.movie.data.WinnerWrapperDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase
@Transactional
public class WinnerServiceTest {

    @Autowired
    private WinnerService winnerService;

    @Test
    void testFindMinMaxIntervalWinners_shouldReturnCorrectInterval() {
        WinnerWrapperDTO wrapper = winnerService.findMinMaxIntervalWinners();

        assertThat(wrapper).isNotNull();
        assertThat(wrapper.getMin()).isNotEmpty();
        assertThat(wrapper.getMax()).isNotEmpty();

        assertThat(wrapper.getMin().getFirst().getInterval()).isNotNegative();
        assertThat(wrapper.getMax().getFirst().getInterval()).isNotNegative();

        assertThat(wrapper.getMin().getFirst().getProducer()).isNotEmpty();
        assertThat(wrapper.getMax().getFirst().getProducer()).isNotEmpty();
    }

}
