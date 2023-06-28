package com.skydream.scoringserver.controller;

import com.skydream.scoringserver.model.SolutionDto;
import com.skydream.scoringserver.service.IScoringService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/score")
public class ScoringController {
    private final IScoringService scoringService;

    public ScoringController(IScoringService scoringService) {
        this.scoringService = scoringService;
    }

    @PostMapping("")
    public ResponseEntity<String> score(@RequestBody SolutionDto solutionDto) throws IOException, InterruptedException {
        System.out.println("[POST] score api ");
//        System.out.println(solutionDto);
        String result = scoringService.score(solutionDto);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
