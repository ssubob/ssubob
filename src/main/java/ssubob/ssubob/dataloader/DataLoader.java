package ssubob.ssubob.dataloader;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ssubob.ssubob.place.repository.PlaceRepository;
import ssubob.ssubob.place.request.PlaceCreate;
import ssubob.ssubob.place.service.PlaceService;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataLoader {
    private final ObjectMapper objectMapper;

    private final PlaceService placeService;

    @Value("${api-key}")
    private String apiKey;

    @Value("${api-url}")
    private String apiUrl;

    private int requestCnt;

    @Getter
    private static class APIResponse {
        private List<PlaceCreate> documents;

        private Meta meta;

        @Getter
        private static class Meta {
            private Integer total_count;
        }
    }

    void loadData(String url, int dataCnt) throws IOException {
        int cnt = 0;
        OkHttpClient client = new OkHttpClient();
        for (int page = 1; page <= 3 && cnt < dataCnt; page++) {
            Request request = new Request.Builder()
                    .url(url + "&page=" + page)
                    .header("Authorization", apiKey)
                    .build();
            Response response = client.newCall(request).execute();
            APIResponse apiResponse = objectMapper.readValue(response.body().string(), APIResponse.class);
            for (PlaceCreate placeCreate : apiResponse.getDocuments()) {
                WebClient webClient = new WebClient();
                webClient.getOptions().setJavaScriptEnabled(true);
                HtmlPage htmlPage = webClient.getPage(placeCreate.getPlace_url());
                webClient.waitForBackgroundJavaScript(5000);
                Elements elements = Jsoup.parse(htmlPage.asXml()).select("span.bg_present");
                if(!elements.isEmpty()){
                    String style = elements.first().attr("style");
                    if(style.contains("(")&&style.contains(")"))
                        placeCreate.setImage("https:"+style.split("\\(\\'|\\'\\)")[1]);
                }
                placeService.create(placeCreate);
                cnt++;
            }
        }
    }

    void findRange(double x1, double y1, double x2, double y2) throws IOException {
        requestCnt++;
        if (requestCnt >= 1000) {
            log.error("too many findRange() call");
            return;
        }
        OkHttpClient client = new OkHttpClient();
        String url = apiUrl + "&rect=" + x1 + "," + y1 + "," + x2 + "," + y2;
        Request request = new Request.Builder()
                .url(url)
                .header("Authorization", apiKey)
                .build();
        Response response = client.newCall(request).execute();
        APIResponse apiResponse = objectMapper.readValue(response.body().string(), APIResponse.class);
        if (apiResponse.getMeta().getTotal_count() <= 45) {
            loadData(url, apiResponse.getMeta().getTotal_count());
            return;
        }
        double mx = (x1 + x2) / 2, my = (y1 + y2) / 2;
        findRange(x1, y1, mx, my);
        findRange(mx, y1, x2, my);
        findRange(x1, my, mx, y2);
        findRange(mx, my, x2, y2);
    }

    @Scheduled(fixedDelay = 86400000)
    void updateData() throws IOException {
        requestCnt = 0;
        findRange(126.95009, 37.49036, 126.96424, 37.50009);
    }
}
