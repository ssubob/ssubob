package ssubob.ssubob.like.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ssubob.ssubob.place.domain.Place;
import ssubob.ssubob.user.domain.User;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "_like")
public class Like {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "like_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_id")
    private Place place;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public Like(Place place, User user) {
        this.place = place;
        this.user = user;
    }
}
