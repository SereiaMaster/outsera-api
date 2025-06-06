package com.outsera.movie.service;

import com.outsera.movie.data.WinnerWrapper;
import com.outsera.movie.repository.CategoryRepository;
import com.outsera.movie.repository.MovieProducerRepository;
import com.outsera.movie.repository.MovieRepository;
import com.outsera.movie.repository.WinnerRepository;
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
        WinnerWrapper wrapper = winnerService.findMinMaxIntervalWinners();

        assertThat(wrapper).isNotNull();
        assertThat(wrapper.getMin()).hasSize(1);
        assertThat(wrapper.getMax()).hasSize(1);

        assertThat(wrapper.getMin().get(0).getInterval()).isEqualTo(1L);
        assertThat(wrapper.getMax().get(0).getInterval()).isEqualTo(13L);

        assertThat(wrapper.getMin().get(0).getProducer()).isEqualTo("Joel Silver");
        assertThat(wrapper.getMax().get(0).getProducer()).isEqualTo("Matthew Vaughn");
    }

}
