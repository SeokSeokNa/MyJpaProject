package com.firstjpa.minijpa.api;

import com.firstjpa.minijpa.api_dto.UserApiDto;
import com.firstjpa.minijpa.domain.User;
import com.firstjpa.minijpa.repository.UserRepository2;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class UserApiController {

    private final UserRepository2 userRepository;

    @GetMapping("/api/v1/user")
    public List<UserApiDto> boardV1_page() {
        List<User> findUser = userRepository.findAll();
        List<UserApiDto> collect = findUser.stream()
                .map(user -> new UserApiDto(user))
                .collect(Collectors.toList());
        return collect;
    }
}
