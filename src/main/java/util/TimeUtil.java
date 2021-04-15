package util;

import lombok.experimental.var;
import org.apache.commons.compress.utils.Lists;

import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.stream.Stream;

public class TimeUtil {

    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

    private static final String STANDARD_DATE_TIME_PATTERN = "yyyy-MM-dd'T'HH:mm:ss";

    public static Long getCurrentSeconds() {
        return System.currentTimeMillis() / 1000;
    }

    public static Long getMillSeconds(Long second) {
        return second * 1000;
    }

    public static OffsetDateTime getOffsetDateTimeBySecond(Integer seconds) {
        return OffsetDateTime.ofInstant(Instant.ofEpochMilli(getMillSeconds((long) seconds)), ZoneId.systemDefault());
    }

    public static OffsetDateTime toOffsetDateTime(String text) {
        LocalDateTime parse = LocalDateTime.parse(text, DateTimeFormatter.ofPattern(YYYY_MM_DD_HH_MM_SS));
        return OffsetDateTime.of(parse, OffsetDateTime.now().getOffset());
    }

    public static OffsetDateTime ageToBirth(String text) {
        String[] ageArray = text.split(",");
        if (ageArray.length != 2) {
            System.out.println("length error");
        }
        int year = OffsetDateTime.now().getYear() - Integer.parseInt(ageArray[0]);
        int month = Integer.parseInt(ageArray[1]);

        OffsetDateTime birth = OffsetDateTime.of(year, month, 1, 0, 0, 0, 0, ZoneOffset.UTC);
        return birth;
    }

    public static OffsetDateTime _ageToBirth(String ageStr) {
        String[] ageArray = ageStr.split(",");
        int year = Integer.parseInt(ageArray[0]);
        int month = Integer.parseInt(ageArray[1]);
        return OffsetDateTime.now().minusYears(year).minusMonths(month);
    }

    public static void main(String[] args) {
//        System.out.println(_ageToBirth("49,1"));
//        System.out.println(_ageToBirth("49,1").toInstant().getEpochSecond());
//        System.out.println(_ageToBirth("50,12"));
//        System.out.println(_ageToBirth("50,12").toInstant().getEpochSecond());
//        System.out.println(_ageToBirth("51,1"));
//        System.out.println(_ageToBirth("51,1").toInstant().getEpochSecond());
//        System.out.println(Period.between(_ageToBirth("54,6").toLocalDate(), LocalDate.now()).getYears());

        System.out.println(OffsetDateTime.now());
        System.out.println(OffsetDateTime.now());
        OffsetDateTime r = OffsetDateTime.now();
        System.out.println(String.format("%s%s%s", r.getYear(), r.getMonthValue(), r.getDayOfMonth()));
        System.out.println(OffsetDateTime.now().format(DateTimeFormatter.ofPattern("YYYYMMdd")));
        System.out.println(OffsetDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")));
    }

    public static void test() {
        int timestampBeginYear = 1970;
        OffsetDateTime birth = OffsetDateTime.of(1970, 1, 1, 0, 0, 0, 0, ZoneOffset.UTC);

        System.out.println("birth: " + birth);
        if (birth.getYear() <= timestampBeginYear) {
            // 1970年之前出生 使用年龄(时间戳为负数)
            System.out.println("age: " + Period.between(birth.toLocalDate(), LocalDate.now()).getYears());
        } else {
            // 1970年之后出生 使用时间戳(年龄为小数)
            System.out.println("timestamp: " + TimeUtil.getMillSeconds(birth));
        }
    }

    public static String dateFormat(OffsetDateTime dateTime, String pattern) {
        return dateTime.format(DateTimeFormatter.ofPattern(pattern));
    }

    public static Long getMillSeconds(OffsetDateTime dateTime) {
        return getMillSeconds(dateTime.toInstant().getEpochSecond());
    }
}
