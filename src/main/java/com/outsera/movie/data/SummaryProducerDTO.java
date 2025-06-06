package com.outsera.movie.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SummaryProducerDTO {

    private Long movieProducerId;
    private Long interval;
    private Long previousWin;
    private Long followingWin;

}
