package com.my.total_jpa_back.users.controller;
import com.my.total_jpa_back.users.entity.Users;
import com.my.total_jpa_back.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

//  Restful 한 API를 제공할 때 사용하는 어노테이션
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final UserRepository userRepository;
    //  전체 리스트를 요청
    @GetMapping
    public List<Users> findAll(){
        return userRepository.findAll();
    }
}
