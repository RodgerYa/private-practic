package fly;

import okhttp3.*;

import java.io.IOException;
import java.time.OffsetDateTime;
import java.util.List;

/**
 * @author yanwenbo
 * @date 2021/3/16
 */
class QueryT {

    private final String baseUrl = "http://www.ceair.com/otabooking/flight-search!doFlightSearch.shtml";
    private final OkHttpClient httpClient = new OkHttpClient();


    public static void main(String[] args) {
        try {
            new QueryT().doPost();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void doPost() throws Exception {
//        String sig = content.get(0);
//        String ts = content.get(1);
        String json = "_=69dfddc0864011ebbfc8d11a8d02b583&searchCond={\"adtCount\":1,\"chdCount\":0,\"infCount\":0,\"currency\":\"CNY\",\"tripType\":\"OW\",\"recommend\":false,\"reselect\":\"\",\"page\":\"0\",\"sortType\":\"a\",\"sortExec\":\"a\",\"seriesid\":\"69dfddc0864011ebbfc8d11a8d02b583\",\"segmentList\":[{\"deptCd\":\"SHA\",\"arrCd\":\"SZX\",\"deptDt\":\"2021-04-04\",\"deptAirport\":\"\",\"arrAirport\":\"\",\"deptCdTxt\":\"上海\",\"arrCdTxt\":\"深圳\",\"deptCityCode\":\"SHA\",\"arrCityCode\":\"SZX\"}],\"version\":\"A.1.0\"}";

        RequestBody body = RequestBody.create(
            json,
            MediaType.parse("application/x-www-form-urlencoded; charset=UTF-8"));

        Request request = new Request.Builder()
            .url(baseUrl)
            .addHeader("User-Agent", "okhttp/3.3.0")
//            .addHeader("ts", ts)
//            .addHeader("sig", sig)
            .addHeader("Cookie", "_ga=GA1.2.525776808.1599477269; gr_user_id=e53b29dd-5af0-4bf3-95f2-abba8346bede; grwng_uid=3ca6ceb1-de85-46f2-a90d-0bb8bc3c1b7b; Webtrends=e9e76de8.5bda37864e81e; language=zh_CN; ecrmWebtrends=116.232.34.214.1615884683031; _gid=GA1.2.837992550.1615884684; user_cookie=true; ffpno=421123199511055215; passportId=C5C6888316D50C24518BB0AAD80A3A74; 84bb15efa4e13721_gr_last_sent_cs1=C5C6888316D50C24518BB0AAD80A3A74; 84bb15efa4e13721_gr_session_id=5f5b41f0-abe3-4db7-a220-9206c2ffeb07; 84bb15efa4e13721_gr_last_sent_sid_with_cs1=5f5b41f0-abe3-4db7-a220-9206c2ffeb07; 84bb15efa4e13721_gr_session_id_5f5b41f0-abe3-4db7-a220-9206c2ffeb07=true; JSESSIONID=9qtGqxDVjJVJNJ-rWcbhfoVv.laputaServer5; _gat=1; _gat_UA-80008755-11=1; 84bb15efa4e13721_gr_cs1=C5C6888316D50C24518BB0AAD80A3A74")
            .post(body)
            .build();

        try (Response response = httpClient.newCall(request).execute()) {

            if (!response.isSuccessful()) {
                throw new IOException(OffsetDateTime.now().toString() + ": Unexpected code " + response);
            }

            // Get response body
            System.out.println(OffsetDateTime.now().toString() + ": " + response.body().string());
        }
    }
}
