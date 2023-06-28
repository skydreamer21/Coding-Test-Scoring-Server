package com.skydream.scoringserver.service;

import com.skydream.scoringserver.model.SolutionDto;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@org.springframework.stereotype.Service
public class ScoringService implements IScoringService {
    private static final String PROJECT_PATH = System.getProperty("user.dir");
    private static final String USER_CODE_PATH = PROJECT_PATH + "\\src\\main\\resources\\usercode\\Main.java";


    @Override
    public String score(SolutionDto solutionDto) throws IOException, InterruptedException {
        saveUserCodeFile(solutionDto);

        System.out.println("컴파일 시작");
        compile();
        System.out.println("실행");
        String userResult = execute();
        String answer = readAnswer();
        System.out.printf("결과 : %s, 정답 : %s\n", userResult, answer);
        if (userResult.equals(answer)) {
            return "SUCCESS";
        } else {
            return "FAIL";
        }
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
                "        String filePath = \"\\\\..\\\\..\\\\..\\\\input\\\\input1.txt\";\n" +
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

        File file = new File(PROJECT_PATH + "\\src\\main\\resources\\usercode\\Main.class");
        if (!file.exists()) {
            throw new FileNotFoundException("컴파일이 정상적으로 실행되지 않았습니다.");
        }
    }

    private String execute() throws IOException, InterruptedException {
        String cmd = "cmd /c cd src/main/resources/usercode && java Main";
        Process p = Runtime.getRuntime().exec(cmd);
//        int exitCode = p.waitFor();

        BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String l = null;
        StringBuffer sb = new StringBuffer();
//        sb.append(cmd).append("\n");

//        System.out.println("결과 저장");
        int cnt = 0;
        while ((l = br.readLine()) != null && cnt++ < 30) {
//            System.out.println("한줄 저장됨");
            sb.append(l);
            sb.append("\n");
        }

        return sb.toString().trim();
    }

    private String readAnswer() throws IOException {
        BufferedReader br = new BufferedReader(
                new FileReader(PROJECT_PATH + "\\src\\output\\output1.txt")
        );
        return br.readLine().trim();
    }
}
