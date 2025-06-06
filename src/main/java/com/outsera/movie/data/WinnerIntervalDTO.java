package com.outsera.movie.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WinnerIntervalDTO {

    private String producer;
    private Long interval;
    private Long previousWin;
    private Long followingWin;

}
