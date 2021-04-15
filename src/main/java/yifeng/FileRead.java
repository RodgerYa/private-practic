package yifeng;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class FileRead<T> {

    public static void main(String[] args) {

        read("/Users/yanwenbo/Desktop/workspace/test/src/main/resources/delivery_retry.txt");
    }

    public static List<String> read(String name) {
        List<String> result = Lists.newArrayList();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(name));
            String line = null;
            while((line=reader.readLine())!=null){
                if (StringUtils.isNotBlank(line)) {
                    result.add(line.trim());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
