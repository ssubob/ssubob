package ssubob.ssubob.place.domain;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ssubob.ssubob.comment.domain.Comment;
import ssubob.ssubob.like.domain.Like;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Place {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "place_id")
	private Long id;

	private String title;

	private String image;

	private String url;

	private String category;

	private Double locationX;

	private Double locationY;

	private String phone;

	private String address_name;

	private Long distance;

	private Long apiId;

	@OneToMany(mappedBy = "place", cascade = CascadeType.ALL)
	private List<Comment> comments = new ArrayList<>();

	@OneToMany(mappedBy = "place" ,cascade = CascadeType.ALL)
	private List<Like> likes = new ArrayList<>();

	@Builder
	public Place(String title, String image, String url, String category, Double locationX, Double locationY, String phone, String address_name, Long distance, Long apiId) {
		this.title = title;
		this.image = image;
		this.url = url;
		this.category = category;
		this.locationX = locationX;
		this.locationY = locationY;
		this.phone = phone;
		this.address_name = address_name;
		this.distance = distance;
		this.apiId = apiId;
	}
}
