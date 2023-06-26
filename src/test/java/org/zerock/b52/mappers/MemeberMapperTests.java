package org.zerock.b52.mappers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.b52.dto.MemberReadDTO;

import lombok.extern.log4j.Log4j2;

@Log4j2
@SpringBootTest
public class MemeberMapperTests {
    
    @Autowired
    private MemberMapper memberMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

   

    @Test
    public void testGetTime(){

        System.out.println(memberMapper.getTime());
    }

    @Commit
    @Transactional
    @Test
    public void testInsert(){

        String email = "test4@gmail.com";
        String mpw = passwordEncoder.encode("1111");
        String mname = "사용자테스트";

        String rolename = "ADMIN";
        
        memberMapper.insertMember(email, mpw, mname);
        //memberMapper.insertMemeberRole(email, rolename);
         memberMapper.insertMemeberRole(email, "USER3");

    }

    @Test
    public void testSelectOne(){

        String email = "test1@gmail.com";

        MemberReadDTO dto = memberMapper.selectOne(email);

        System.out.println(dto);
    }




}
