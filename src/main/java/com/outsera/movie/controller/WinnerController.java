package com.outsera.movie.controller;

import com.outsera.movie.data.WinnerIntervalDTO;
import com.outsera.movie.data.WinnerWrapperDTO;
import com.outsera.movie.service.WinnerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/winners")
public class WinnerController {

    private final WinnerService winnerService;

    public WinnerController(
            WinnerService winnerService
    ){
        this.winnerService = winnerService;
    }

    @GetMapping("/min-max-interval")
    public ResponseEntity<WinnerWrapperDTO> getMinMaxIntervalWinners(){

        return ResponseEntity.ok(winnerService.findMinMaxIntervalWinners());

    }

    @GetMapping("/max-interval")
    public ResponseEntity<List<WinnerIntervalDTO>> getMaxInterval(){

        return ResponseEntity.ok(winnerService.findMaxIntervalWinners());

    }

    @GetMapping("/min-interval")
    public ResponseEntity<List<WinnerIntervalDTO>> getMinInterval(){

        return ResponseEntity.ok(winnerService.findMinIntervalWinners());

    }

}
