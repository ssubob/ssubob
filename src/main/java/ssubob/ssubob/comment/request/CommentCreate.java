package ssubob.ssubob.comment.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class CommentCreate {
    @NotBlank(message = "이름을 입력해주세요.")
    private String name;

    @NotBlank(message = "내용을 입력해주세요.")
    private String content;

    @Builder
    public CommentCreate(String name, String content) {
        this.name = name;
        this.content = content;
    }
}