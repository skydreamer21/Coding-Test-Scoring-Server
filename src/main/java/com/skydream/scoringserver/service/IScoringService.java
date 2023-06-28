package com.skydream.scoringserver.service;

import com.skydream.scoringserver.model.SolutionDto;

import java.io.IOException;

public interface IScoringService {
    public String score(SolutionDto solutionDto) throws IOException, InterruptedException;
}
