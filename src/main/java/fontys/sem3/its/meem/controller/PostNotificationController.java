package fontys.sem3.its.meem.controller;

import fontys.sem3.its.meem.business.usecase.UserRelation.GetUserRelationsUseCase;
import fontys.sem3.its.meem.domain.model.PostNotification;
import fontys.sem3.its.meem.domain.model.PostUploadMessage;
import fontys.sem3.its.meem.persistence.entity.UserRelationEntity;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@AllArgsConstructor
@Controller
public class PostNotificationController {
    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    private final GetUserRelationsUseCase getUserRelationsUseCase;

    @MessageMapping("/notify")
    public void recMessage(@Payload PostUploadMessage message) {
        PostNotification notification = PostNotification.builder()
                .postUrl(message.getPostUrl())
                .authorId(message.getAuthorId())
                .postTitle(message.getPostTitle())
                .authorPfpUrl(message.getAuthorPfpUrl())
                .authorUsername(message.getAuthorUsername())
                .build();

        for (UserRelationEntity relation : getUserRelationsUseCase.getUserFollowers(message.getAuthorId())) {
            messagingTemplate.convertAndSendToUser(relation.getPrimaryUser().getUsername(), "/notifications", notification);
        }
    }
}
