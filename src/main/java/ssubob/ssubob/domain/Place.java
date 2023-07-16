package ssubob.ssubob.domain;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

	private String category;

	private Long locationX;

	private Long locationY;

	@OneToMany(mappedBy = "place")
	private List<Comment> comments = new ArrayList<>();

	@Builder
	public Place(String title, String image, String category, Long locationX, Long locationY) {
		this.title = title;
		this.image = image;
		this.category = category;
		this.locationX = locationX;
		this.locationY = locationY;
	}
}
