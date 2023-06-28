package com.skydream.scoringserver.service;

import com.skydream.scoringserver.model.SolutionDto;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@org.springframework.stereotype.Service
public class ScoringService implements IScoringService {
    private static final String USER_CODE_PATH ="C:\\Create\\development\\edu\\ssafy\\secondSem\\ScoringServer\\src\\main\\resources\\usercode\\Main.java";


    @Override
    public String score(SolutionDto solutionDto) throws IOException, InterruptedException {
        saveUserCodeFile(solutionDto);

        System.out.println("컴파일 시작");
        compile();
        return "SUCCESS";
    }

    private void saveUserCodeFile(SolutionDto solutionDto) {
        try {
            OutputStream output = new FileOutputStream(USER_CODE_PATH);
            String code = solutionDto.getCode();
            System.out.println("input setting code 추가");
            code = modifyCode(code);
            byte[] by = code.getBytes();
            output.write(by);

        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    private String modifyCode(String code) {
        String inputSettingCode = "\nString curDirPath = System.getProperty(\"user.dir\");\n" +
                "        String filePath = \"\\\\..\\\\testcase\\\\input1.txt\";\n" +
                "        System.setIn(new FileInputStream(curDirPath + filePath));";

        String pattern = ".*public static void main.*\\{.*";
        Matcher matcher = Pattern.compile(pattern).matcher(code);

        StringBuilder sb = new StringBuilder(code);
        if (matcher.find()) {
            sb.insert(matcher.end(), inputSettingCode);
        }

        return sb.toString();
    }

    private void compile() throws IOException, InterruptedException {
        String cmd = "cmd /c cd src/main/resources/usercode && javac Main.java";
        Process p = Runtime.getRuntime().exec(cmd);
        int exitCode = p.waitFor();

        File file = new File("C:\\Create\\development\\edu\\ssafy\\secondSem\\ScoringServer\\src\\main\\resources\\usercode\\Main.class");
        if (!file.exists()) {
            throw new FileNotFoundException("컴파일이 정상적으로 실행되지 않았습니다.");
        }
    }

}
