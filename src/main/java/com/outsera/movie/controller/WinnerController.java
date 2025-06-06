package com.outsera.movie.controller;

import com.outsera.movie.data.WinnerWrapper;
import com.outsera.movie.service.WinnerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<WinnerWrapper> getMinMaxIntervalWinners(){

        return ResponseEntity.ok(winnerService.findMinMaxIntervalWinners());

    }

}
