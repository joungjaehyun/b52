package org.zerock.b52.security;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.zerock.b52.dto.MemberDTO;
import org.zerock.b52.dto.MemberReadDTO;
import org.zerock.b52.mappers.MemberMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final MemberMapper mapper;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        log.info("-----------loadUser------------");
        log.info(userRequest);
        log.info("============================================");

        ClientRegistration clientRegistration = userRequest.getClientRegistration();
        String clientName = clientRegistration.getClientName();

        OAuth2User oAuth2User = super.loadUser(userRequest);
        Map<String, Object> paramMap = oAuth2User.getAttributes();

        String email = null;
        // react에서는 직접 가공해서 함
        switch (clientName) {
            case "kakao":
                email = getKakaoEmail(paramMap);
                break;
        }

        log.info("===============================");
        log.info(email);
        log.info("===============================");
        // DB에 해당 이메일 사용자가 있다면
        MemberDTO memberDTO = null;
        MemberReadDTO readDTO = mapper.selectOne(email);
        if (readDTO == null) {
            memberDTO = new MemberDTO(email,
                    "",
                    "카카오사용자",
                    List.of("USER"));


        } 
         // 아니라면
        else {
            memberDTO = new MemberDTO(readDTO.getEmail(),
                    readDTO.getMpw(),
                    readDTO.getMname(),
                    readDTO.getRolenames());

        }
        return memberDTO;
       

        // 화면에서 사용자의 로그인한 정보를 찍는다 -> 타입이 달라지면 화면에서 처리할때 복잡해지므로
        // MemberDTO로 통일시켜주는것

    }

    // 카카오에서 정보를 추출하는 함수
    private String getKakaoEmail(Map<String, Object> paramMap) {

        log.info("KAKAO-----------------------------------------");

        Object value = paramMap.get("kakao_account");

        log.info(value);

        LinkedHashMap accountMap = (LinkedHashMap) value;

        String email = (String) accountMap.get("email");

        log.info("email..." + email);

        return email;
    }
}
