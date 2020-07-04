package com.healthteat.common.domain.slack;

import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@NoArgsConstructor
public class SlackDto {

    @Autowired
    private RestTemplate restTemplate;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class SlackMessageAttachment
    {
        private String color;
        private String pretext;
        private String title;
        private String title_link;
        private String text;
    }
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class SlackMessage
    {
        private String text;
        private String channel;
        private List<SlackMessageAttachment> attachments;

        void addAttachment(SlackMessageAttachment attachment)
        {
            if (this.attachments == null) {
                this.attachments = Lists.newArrayList();
            }
            this.attachments.add(attachment);
        }
    }

    public boolean notify(SlackMessageAttachment message)
    {
        SlackMessage slackMessage =
                SlackMessage.builder()
                            .channel(SlackTarget.CH_BOT.getChannel())
                            .attachments(Lists.newArrayList(message))
                            .build();
        try {
            restTemplate.postForEntity(SlackTarget.CH_BOT.getWebHookUrl(), slackMessage, String.class);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
