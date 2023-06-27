package com.skydream.scoringserver.service;

import com.skydream.scoringserver.model.SolutionDto;

import java.io.FileOutputStream;
import java.io.OutputStream;

@org.springframework.stereotype.Service
public class ScoringService implements IScoringService {
    private static final String USER_CODE_PATH ="C:\\Create\\development\\edu\\ssafy\\secondSem\\ScoringServer\\src\\main\\resources\\usercode\\usercode.java";


    @Override
    public String score(SolutionDto solutionDto) {
        saveUserCodeFile(solutionDto);
        return "SUCCESS";
    }

    private void saveUserCodeFile(SolutionDto solutionDto) {
        try {
            OutputStream output = new FileOutputStream(USER_CODE_PATH);
            String str = solutionDto.getCode();
            byte[] by = str.getBytes();
            output.write(by);

        } catch (Exception e) {
            e.getStackTrace();
        }
    }
}
