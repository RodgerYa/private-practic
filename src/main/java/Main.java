import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.collect.Lists;
import org.apache.commons.codec.digest.DigestUtils;
import org.joda.time.DateTime;
import sun.security.provider.MD5;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {

        HashMap<String, Integer> prices = new HashMap<>();

        // 往HashMap中添加映射关系
        prices.put("Shoes", 200);
        prices.put("Bag", 300);
        prices.put("Bag", prices.getOrDefault("Bag", 0) + 100);

        System.out.println("HashMap: " + prices);
//        System.out.println(String.join(",", Lists.newArrayList("s", "t")));
//        LinkedList l = new LinkedList<Integer>();
//        l.addLast(1);
//        l.addLast(2);
//        System.out.println(l);
//
//        System.out.println("remove first" + l.removeFirst());
//
//        System.out.println(l);
//
//        l.addLast(4);
//
//
//        System.out.println(OffsetDateTime.now().toInstant().getEpochSecond());
//        testRx();
//        System.out.println(2 * 4990 * 35 / 1000D);
//        String[] arr = {"hhh", "ssfsd", "quewor"};
//        redu(Arrays.asList(arr));
//        birthToAge();
//        try {
//            jacksonTest();
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//        }
//        timeFormat();
//        birth(20);
//        jsonJava8();
//        System.out.println("一个月前: " + new DateTime().minusMonths(1));
//        System.out.println("一年前: " + new DateTime().minusYears(1));
//        System.out.println("当前: " + new DateTime());

//
//        int i = BigDecimal.valueOf(10).compareTo(BigDecimal.ZERO);
//        System.out.println(i + "");
//        OffsetDateTime d = getOffsetDateTime(1555991737565L);
//        d.toString();

//        Double price = 23.20;
//        System.out.println(new BigDecimal(String.valueOf(price)));
//        System.out.println(yuanToCent(price));

//        UserT t = new UserT();
//        t.id = null;
//        System.out.println(t);
//        String match = "(?:[\uD83C\uDF00-\uD83D\uDDFF]|[\uD83E\uDD00-\uD83E\uDDFF]|[\uD83D\uDE00-\uD83D\uDE4F]|[\uD83D\uDE80-\uD83D\uDEFF]|[\u2600-\u26FF]\uFE0F?|[\u2700-\u27BF]\uFE0F?|\u24C2\uFE0F?|[\uD83C\uDDE6-\uD83C\uDDFF]{1,2}|[\uD83C\uDD70\uD83C\uDD71\uD83C\uDD7E\uD83C\uDD7F\uD83C\uDD8E\uD83C\uDD91-\uD83C\uDD9A]\uFE0F?|[\u0023\u002A\u0030-\u0039]\uFE0F?\u20E3|[\u2194-\u2199\u21A9-\u21AA]\uFE0F?|[\u2B05-\u2B07\u2B1B\u2B1C\u2B50\u2B55]\uFE0F?|[\u2934\u2935]\uFE0F?|[\u3030\u303D]\uFE0F?|[\u3297\u3299]\uFE0F?|[\uD83C\uDE01\uD83C\uDE02\uD83C\uDE1A\uD83C\uDE2F\uD83C\uDE32-\uD83C\uDE3A\uD83C\uDE50\uD83C\uDE51]\uFE0F?|[\u203C\u2049]\uFE0F?|[\u25AA\u25AB\u25B6\u25C0\u25FB-\u25FE]\uFE0F?|[\u00A9\u00AE]\uFE0F?|[\u2122\u2139]\uFE0F?|\uD83C\uDC04\uFE0F?|\uD83C\uDCCF\uFE0F?|[\u231A\u231B\u2328\u23CF\u23E9-\u23F3\u23F8-\u23FA]\uFE0F?)";
//        System.out.print("每次1片 饭后用药 \uD83D\uDC49查看".replace("[^\\\\p{L}\\\\p{M}\\\\p{N}\\\\p{P}\\\\p{Z}\\\\p{Cf}\\\\p{Cs}\\\\s]", ""));

//        System.out.printf(EmojiParser.removeAllEmojis("每次1片 饭后用药 \uD83D\uDC49查看"));

//        System.out.println(toOffsetDateTime(1576079565L).toString());
//        System.out.println(now.plusDays(2));

//        System.out.println(ZoneId.systemDefault());

//        System.out.println(OffsetDateTime.ofInstant(Instant.ofEpochMilli(1585735985672L), ZoneId.of("UTC-0")).toString());
//        System.out.println(OffsetDateTime.ofInstant(Instant.ofEpochMilli(1585735985672L), ZoneId.of("UTC+8")).toString());
//        System.out.println(OffsetDateTime.ofInstant(Instant.ofEpochMilli(1585735985672L), ZoneId.systemDefault()).toString());
//        System.out.println(DigestUtils.md5Hex("1587643017599"));

//        System.out.println(OffsetDateTime.now());
//        System.out.println(OffsetDateTime.now().toInstant().getEpochSecond());
//        download(1042303);
    }

    private static void testRx() {
//        Pattern pattern = Pattern.compile("/api/prescription/\\d+/invalid");
//        Matcher matcher = pattern.matcher("/api/prescription/122222/invalid");
//        System.out.println(matcher.group());

//        System.out.println(Pattern.matches("/api/prescription/\\d+/invalid", "/api/prescription/sssss/invalid"));
        Optional a = Optional.of(133);
        Optional b = Optional.of(133);
        System.out.println(a.equals(b));
    }

    private static void download(Integer goodsCode) {
        int i = 1;
        URL url = null;
        try {
            url = new URL("https://img.yifengx.com/product/"
                    + goodsCode + "/" + goodsCode + "a" + i + ".jpg");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        try {
            ReadableByteChannel rbc = Channels.newChannel(url.openStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        File file = new File(goodsCode + "-" + i + ".jpg");
    }

    private static OffsetDateTime toOffsetDateTime(Long second) {
        return OffsetDateTime.ofInstant(Instant.ofEpochMilli(second * 1000), ZoneId.systemDefault());
    }

    private static String encode() {
        String url = "/page/drug/order/request?recommendId=1ab75969d0d54ebbb8733cf2f9e76c5f";
        String path = "";
        try {
            path = "pages/splash/index?p=web&url=" + URLEncoder.encode(url, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "https://yisheng.aihaisi.com/" + path;
    }

    public static Long yuanToCent(Double yuan) {
        return new BigDecimal(String.valueOf(yuan)).multiply(BigDecimal.valueOf(100)).longValue();
    }

    public static OffsetDateTime getOffsetDateTime(Long millSeconds) {
        return OffsetDateTime.ofInstant(Instant.ofEpochMilli(millSeconds), ZoneId.systemDefault());
    }

    private static void add(List<String> l) {
        l.add("added!");
    }

    private static void redu(List<String> l) {
//        System.out.println(l.stream().reduce(i -> i.trim()).orElse(""));
    }

    private void sort(List<Integer> list) {
        list.stream().sorted(Comparator.comparingInt(Integer::intValue));
    }

    private static void stringFormat() {
        String s = String.format("<a href=\"%s\"> >> 点击重新推荐药品＞</a>", "www.baidu.com");
        System.out.println(s);
    }

    private static void birthToAge() {
        OffsetDateTime time = OffsetDateTime.of(1995, 11, 5, 12, 30, 30, 0, ZoneOffset.UTC);
        int s = OffsetDateTime.now().getYear() - time.getYear();
        System.out.println(s);
    }

    private static void jacksonTest() throws JsonProcessingException {
//        User user = new User("yy", 2, 20);
//        ObjectMapper om = new ObjectMapper().setVisibility(com.fasterxml.jackson.annotation.PropertyAccessor.FIELD,
//                com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY);
//        String s = om.writeValueAsString(user);
//        System.out.println(s);
    }

    private static void timeFormat() {
        String s = OffsetDateTime.now().format(DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH:mm:ss"));
        System.out.println(s);
    }

    private static void time(Long second) {
        OffsetDateTime time = OffsetDateTime.ofInstant(Instant.ofEpochMilli(second * 1000), ZoneId.systemDefault());
        time.toString();
    }


    private static void birth(Integer age) {
        OffsetDateTime now = OffsetDateTime.now();
        OffsetDateTime birth = OffsetDateTime.of(now.getYear() - age, now.getMonthValue(), now.getDayOfMonth(), now.getHour(),
                now.getMinute(), now.getSecond(), 0, ZoneOffset.UTC);
        birth.toString();
    }

    private static void concat() {
        List<String> list = new ArrayList<>();
        list.add("h");
        list.add("3");
        list.add("e");
        list.add("l");
        list.add("p");
        String r = list.stream().reduce((i, j) -> i.concat("、 " + j)).orElse("");
        r.toLowerCase();
    }

    private static void jsonJava8() {
//        String userJson = "{\"id\": 1, \"name\": \"xx\"}";
//        User u = new User("hh", 1);
//        ObjectMapper mapper = new ObjectMapper().registerModule(new Jdk8Module())
//                .setVisibility(com.fasterxml.jackson.annotation.PropertyAccessor.FIELD,
//                        com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY);
//        try {
//            User user = mapper.readValue(userJson, User.class);
//            user.toString();
//            String userStr = mapper.writeValueAsString(u);
//            userStr.toString();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    private static void testSub() {
        List<String> list = new ArrayList<>();
        list.add("h");
        list.add("3");
        list.add("e");
        list.add("l");
        list.add("p");

//        list.stream().collect(Collectors.toMap(l -> l.))


    }

    private static void testEq() {
        // todo test equals
    }

    static class User{
        private String name;
        private Integer id;

        public User() {

        }

        public User(String name, Integer id) {
            this.name = name;
            this.id = id;
        }

        @Override
        public boolean equals(Object obj) {
            User u = (User) obj;
            return this.name.equals(u.name) && this.id.equals(u.id);
        }
    }

    private static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Set<Object> seen = ConcurrentHashMap.newKeySet();
        return t -> seen.add(keyExtractor.apply(t));
    }

    public static void testDistinct() {
        User a = new User("a", 1);
        User b = new User("a", 2);
        User c = new User("b", 1);
        User d = new User("b", 1);
        List<User> users = Lists.newArrayList(a, b, c, d);
        List<User> dUsers = users.stream().distinct().collect(Collectors.toList());
        dUsers.toString();
    }



}
