package yifeng;

import com.google.common.collect.Lists;

import java.io.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AnalysisLog {

    public static void main(String[] args) {
        new AnalysisLog().doAnalysis();
    }

    private void doAnalysis() {

        final Pattern pattern = Pattern.compile("患者(\\d+)小程序openId不存在");
        List<String> patientIds = Lists.newArrayList();

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File("logs.txt")),
                    "UTF-8"));
            String lineTxt = null;
            while ((lineTxt = br.readLine()) != null) {
                String[] logs = lineTxt.split("\n");

                Stream.of(logs).forEach(log -> {
                    Matcher m = pattern.matcher(log);
                    if (m.find()) {
                        String patientId = m.group(1);
                        System.out.println("患者openId不存在, patientId: " + patientId);
                        if (patientId != null) {
                            patientIds.add(patientId);
                        }
                    }
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        List<String> result = patientIds.stream().distinct().collect(Collectors.toList());

        System.out.println("total found " + result.size());
        System.out.println(result);

    }
}
