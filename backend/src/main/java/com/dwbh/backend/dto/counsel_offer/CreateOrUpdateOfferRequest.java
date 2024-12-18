package com.dwbh.backend.dto.counsel_offer;

import com.dwbh.backend.common.entity.YnType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Schema(description = "댓글 작성 및 수정 DTO")
public class CreateOrUpdateOfferRequest {

    private long userSeq;   // 댓글 작성자
    @NotBlank(message = "댓글 내용은 필수입니다.")
    private String offerContent;    // 댓글 내용
    private YnType offerPrivateYn;  // 비밀 댓글 여부


}
