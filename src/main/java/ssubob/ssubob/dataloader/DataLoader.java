package ssubob.ssubob.dataloader;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
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
public class DataLoader {
    private final ObjectMapper objectMapper;

    private final PlaceService placeService;

    @Value("${kakao-admin-key}")
    private String kakao_admin_key;

    @NoArgsConstructor
    @Getter
    static class APIResponse {
        private List<PlaceCreate> documents;

        public APIResponse(List<PlaceCreate> documents) {
            this.documents = documents;
        }
    }

    @Scheduled(fixedDelay = 86400000)
        //하루
    void loadData() throws IOException {
        OkHttpClient client = new OkHttpClient();
        String requestUrl = "https://dapi.kakao.com/v2/local/search/category.json?category_group_code=FD6&x=126.95781764313084&y=37.495853033944364&radius=1000&page=";

        for (int page = 1; page <= 45; page++) {
            Request request = new Request.Builder()
                    .url(requestUrl + page)
                    .header("Authorization", "KakaoAK 36b3539b5e74d8297a6237d3f70ebf38")
                    .build();

            Response response = client.newCall(request).execute();

            APIResponse apiResponse = objectMapper.readValue(response.body().string(), APIResponse.class);
            for (PlaceCreate placeCreate : apiResponse.getDocuments())
                placeService.create(placeCreate);
        }
    }
}
