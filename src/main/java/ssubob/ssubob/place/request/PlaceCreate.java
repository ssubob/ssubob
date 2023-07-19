package ssubob.ssubob.place.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
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
    private String image;

    @Builder
    public PlaceCreate(String id, String place_name, String phone, String address_name, String x, String y, String place_url, String distance, String category_name, String image) {
        this.id = id;
        this.place_name = place_name;
        this.phone = phone;
        this.address_name = address_name;
        this.x = x;
        this.y = y;
        this.place_url = place_url;
        this.distance = distance;
        this.category_name = category_name;
        this.image = image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
