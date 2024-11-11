package com.dwbh.backend.dto.chat;

import com.dwbh.backend.common.entity.YnType;
import com.dwbh.backend.dto.user.UserDTO;
import lombok.*;

import java.time.LocalDateTime;

@Data
public class ChatDTO {

    private Long chatSeq;
    private Long counselOfferSeq;
    private Long sendSeq;
    private Long receiveSeq;
    private LocalDateTime modDate;
    private LocalDateTime endDate;
    private YnType readYn;

    @Getter
    public static class Search {

        private Long userSeq;
        private YnType readYn;

    }

    @AllArgsConstructor
    @Getter
    @NoArgsConstructor
    public static class Create {

        private Long counselOfferSeq;
        private Long sendSeq;
        private Long receiveSeq;

    }

    @Getter @Builder
    public static class Update {

        private Long chatSeq;
        private YnType readYn = YnType.N;
        private LocalDateTime modDate;
        private LocalDateTime endDate;

    }

    @Getter
    public static class Response {

        private final Long chatSeq;
        private final Long sendUserSeq;
        private final String sendUserNickname;
        private final Long receiveUserSeq;
        private final String receiveUserNickname;

        private final LocalDateTime modDate;
        private final LocalDateTime endDate;

        @Setter
        private  String lastMessage;
        @Setter
        private boolean showEvaluation = false;
        @Setter
        private YnType readYn = YnType.N;

        public Response(Long chatSeq, Long sendUserSeq, String sendUserNickname, Long receiveUserSeq, String receiveUserNickname, LocalDateTime modDate, LocalDateTime endDate) {
            this.chatSeq = chatSeq;
            this.sendUserSeq = sendUserSeq;
            this.sendUserNickname = sendUserNickname;
            this.receiveUserSeq = receiveUserSeq;
            this.receiveUserNickname = receiveUserNickname;
            this.modDate = modDate;
            this.endDate = endDate;
        }
    }

}
