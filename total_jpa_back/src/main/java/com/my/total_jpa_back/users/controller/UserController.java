package com.my.total_jpa_back.users.controller;
import com.my.total_jpa_back.common.entity.Gender;
import com.my.total_jpa_back.common.exception.UserNotFoundException;
import com.my.total_jpa_back.users.dto.*;
import com.my.total_jpa_back.users.entity.Users;
import com.my.total_jpa_back.users.repository.UserRepository;
import com.my.total_jpa_back.users.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//  Restful 한 API를 제공할 때 사용하는 어노테이션
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {
    private final UserRepository userRepository;
    private final UserService userService;

    //  User ID로 삭제 처리 API 만들기
    @DeleteMapping("/users/{id}")
    public String delete(@PathVariable Long id){
        userService.delete(id);
        return "회원삭제완료";
    }
    //  User 업데이트 API
    //  수정 대상은 PathVarialbe, 값은 RequestBody 받아서 수정
    @PutMapping("/users/{id}")
    public UserResponse update(@PathVariable Long id,
                               @RequestBody UserUpdateRequest request){
        return userService.update(id, request);
    }

    //  새로운 User 추가하기 API
    @PostMapping("/users")
    public UserResponse create(@RequestBody UserCreateRequest request){
        return userService.create(request);
    }

    //  예외처리 테스트
    @GetMapping("/users/{id}")
    public Users findById(@PathVariable Long id){
        return userRepository.findById(id)
                .orElseThrow(()-> new UserNotFoundException());
    }

    //  RequestBody 테스트
    //  RequestBody : Postman에서 json으로 보낸 데이터를 받는 아이
    @PostMapping("/test")
    public HelloResponse test(@RequestBody HelloRequest request){
        return HelloResponse.builder()
                .message("안녕하세요 " + request.getName())
                .age(request.getAge()).build();
    }

    //  전체 리스트를 요청
    @GetMapping("/users")
    public List<Users> findAll(){
        return userRepository.findAll();
    }
    @GetMapping("/gender/{gender}")
    public List<Users> findByGender(@PathVariable Gender gender){
        return userRepository.findByGender(gender);
    }
    @GetMapping("/name")
    public List<Users> findByName(@RequestParam String keyword){
        return userRepository.findByNameContaining(keyword);
    }
    @GetMapping("/color")
    public List<Users> findByLikeColor(@RequestParam String color){
        return userRepository.findByLikeColor(color);
    }
    @GetMapping("/gender-color")
    public List<Users> findByLikeColorAndGender(@RequestParam String color,
                                                @RequestParam Gender gender)
    {
        return userRepository.findByLikeColorAndGender(color, gender);
    }
    @GetMapping("/email")
    public List<Users> findByEmail(@RequestParam String keyword){
        return userRepository.findByEmailContaining(keyword);
    }

    //  이름 : 오름차순, 생성일에 내림차순
    @GetMapping("/sort")
    public List<Users> findBySort(){
        Sort sort = Sort.by("name").ascending()
                .and(
                  Sort.by("createdAt").descending()
                );
        return userRepository.findAll(sort);
    }

    //      Page
    @GetMapping("/page")
    public Page<Users> findAllPage(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(
                page, size,
                Sort.by("createdAt").descending());
        return userRepository.findAll(pageable);
    }

    // Slice
    @GetMapping("/slice")
    public Slice<Users> findAllSlice(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {

        Pageable pageable =
                PageRequest.of(
                        page,
                        size,
                        Sort.by("createdAt")
                                .descending()
                );

        return userRepository.findAllBy(pageable);
    }
}
