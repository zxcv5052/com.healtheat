package com.healthteat.config.auth.dto;

import com.healthteat.domain.member.Member;
import com.healthteat.domain.member.Role;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
public class OAuthAttributes {
    private Map<String, Object> attributes;
    private String nameAttributesKey;
    private String member_name;
    private String member_id;

    @Builder
    public OAuthAttributes(Map<String, Object> attributes, String nameAttributesKey, String member_name, String member_id)
    {
        this.attributes = attributes;
        this.nameAttributesKey = nameAttributesKey;
        this.member_name = member_name;
        this.member_id = member_id;
    }

    public static OAuthAttributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes){
        if("naver".equals(registrationId)) return ofNaver("id",attributes);
        return ofGoogle(userNameAttributeName, attributes);
    }

    private static OAuthAttributes ofNaver(String userNameAttributeName, Map<String, Object> attributes) {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");

        return OAuthAttributes.builder()
                .member_name((String) response.get("name"))
                .member_id((String) response.get("email"))
                .attributes(response)
                .nameAttributesKey(userNameAttributeName)
                .build();
    }

    private static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                .member_name((String) attributes.get("name"))
                .member_id((String) attributes.get("email"))
                .attributes(attributes)
                .nameAttributesKey(userNameAttributeName)
                .build();
    }
    public Member toEntity(){
        return Member.builder()
                .member_name(member_name)
                .member_id(member_id)
                .role(Role.USER)
                .build();
    }

}
