package com.my.total_jpa_back.users.service;

import com.my.total_jpa_back.common.dto.PageResponse;
import com.my.total_jpa_back.common.exception.UserNotFoundException;
import com.my.total_jpa_back.users.dto.UserCreateRequest;
import com.my.total_jpa_back.users.dto.UserResponse;
import com.my.total_jpa_back.users.dto.UserUpdateRequest;
import com.my.total_jpa_back.users.entity.Users;
import com.my.total_jpa_back.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    //  page 졍보를 가져올 서비스
    //  page -> DTO 변환
    @Transactional(readOnly = true)
    public PageResponse<UserResponse> findPage(Pageable pageable){
        // 페이징 정보를 이용해서 검색해 오기
        Page<Users> users = userRepository.findAll(pageable);
        //  ResponseUser DTO 변환
        Page<UserResponse>  response = users.map(user -> UserResponse.from(user));
        return new PageResponse<>(response);
    }
    @Transactional
    public UserResponse create(UserCreateRequest request) {
        Users user = new Users();
        user.setName(request.getName());
        user.setGender(request.getGender());
        user.setEmail(request.getEmail());
        user.setLikeColor(request.getLikeColor());
        //  리포에 저장 요청
        //  repo의 save() 메서드는 기본적으로 저장하고 난 다음
        //  엔티티를 반환해 준다.
        Users savedUser = userRepository.save(user);
        //  엔티티 -> DTO 로 변환해서 리턴
        return UserResponse.from(savedUser);
    }

    @Transactional
    public UserResponse update(Long id, UserUpdateRequest request) {
        //  먼저 수정할 id가 실제 존재하는지 찾아봐야 합니다.
        Users user = userRepository.findById(id)
                        .orElseThrow(()-> new UserNotFoundException());
        user.setName(request.getName());
        user.setGender(request.getGender());
        user.setEmail(request.getEmail());
        user.setLikeColor(request.getLikeColor());
        //  우리는 저장하지 않았어요.
        //  Dirty Checking
        //  영속성컨텍스트에 있는 엔티티의 변경을 감지해서
        //  자동으로 update sql을 실행하는 기능
        //  Transactional 이 생성
        //  같은 트랜잭션 내부에서 일어나야 한다.
        return UserResponse.from(user);
    }

    @Transactional
    public void delete(Long id) {
        Users user = userRepository.findById(id)
                .orElseThrow(()-> new UserNotFoundException());
        userRepository.delete(user);
    }
}
