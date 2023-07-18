package ssubob.ssubob.place.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@ToString
public class PlaceCreate {
    private String id;
    private String place_name;
    private String phone;
    private String address_name;
    private String x;
    private String y;
    private String place_url;
    private String distance;
    private String category_name;

    @Builder
    public PlaceCreate(String id, String place_name, String phone, String address_name, String x, String y, String place_url, String distance, String category_name) {
        this.id = id;
        this.place_name = place_name;
        this.phone = phone;
        this.address_name = address_name;
        this.x = x;
        this.y = y;
        this.place_url = place_url;
        this.distance = distance;
        this.category_name = category_name;
    }
}
