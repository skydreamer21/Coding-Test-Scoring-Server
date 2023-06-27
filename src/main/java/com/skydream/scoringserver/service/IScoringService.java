package com.skydream.scoringserver.service;

import com.skydream.scoringserver.model.SolutionDto;

public interface IScoringService {
    public String score(SolutionDto solutionDto);
}
