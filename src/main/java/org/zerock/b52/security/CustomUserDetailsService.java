package org.zerock.b52.security;

import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.zerock.b52.dto.MemberDTO;
import org.zerock.b52.dto.MemberReadDTO;
import org.zerock.b52.mappers.MemberMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberMapper memberMapper;
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        log.info("------------------------------------");
        log.info("loaduser... " + username);
        log.info("====================================");

        MemberReadDTO readDTO = memberMapper.selectOne(username);

        // 실제로 DB에들어간 데이터 쓸예정
        //    MemberDTO memberDTO = new MemberDTO(
        //         username,
        //         passwordEncoder.encode("1111"),
        //         "키보드 워리어",
        //         List.of("USER"));

        // 실제 DB에 들어간 값을 user로 설정
        MemberDTO memberDTO = new MemberDTO(
                username,
                readDTO.getMpw(),
                readDTO.getMname(),
                readDTO.getRolenames()
                );

        // 더미 유저
        // UserDetails user = User.builder()
        //         .username(username)
        //         .password(passwordEncoder.encode("1111"))
        //         .authorities("ROLE_USER", "ROLE_G1")
        //         .build();

        return memberDTO;
    }

}
