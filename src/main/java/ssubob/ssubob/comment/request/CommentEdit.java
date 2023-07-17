package ssubob.ssubob.comment.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class CommentEdit {
    @NotBlank(message = "이름을 입력해주세요.")
    private String name;

    @NotBlank(message = "내용을 입력해주세요.")
    private String content;
}
