package ssubob.ssubob.comment.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class CommentEdit {

    @NotBlank(message = "내용을 입력해주세요.")
    private String content;

    @Builder
    public CommentEdit(String content) {
        this.content = content;
    }
}
