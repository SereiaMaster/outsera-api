package com.outsera.movie.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WinnerWrapperDTO {

    private List<WinnerIntervalDTO> min = new ArrayList<>();
    private List<WinnerIntervalDTO> max = new ArrayList<>();

}
