package ssubob.ssubob.comment.domain;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ssubob.ssubob.comment.request.CommentEdit;
import ssubob.ssubob.place.domain.Place;

@Entity
@Getter
@NoArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    private String name;

    @Lob
    private String content;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_id")
    private Place place;

    private LocalDateTime createdAt;

    @Builder
    public Comment(String name, String content, Place place) {
        this.name = name;
        this.content = content;
        this.place = place;
    }

    public void edit(CommentEdit commentEdit) {
        name = commentEdit.getName();
        content = commentEdit.getContent();
    }
}
