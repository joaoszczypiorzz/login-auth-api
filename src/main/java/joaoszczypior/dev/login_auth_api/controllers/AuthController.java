package joaoszczypior.dev.login_auth_api.controllers;

import joaoszczypior.dev.login_auth_api.domain.user.User;
import joaoszczypior.dev.login_auth_api.dto.LoginRequestDto;
import joaoszczypior.dev.login_auth_api.dto.RegisterRequestDto;
import joaoszczypior.dev.login_auth_api.dto.ResponseDto;
import joaoszczypior.dev.login_auth_api.infra.security.TokenService;
import joaoszczypior.dev.login_auth_api.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;


    @PostMapping(value = "/login")
    public ResponseEntity login (@RequestBody LoginRequestDto dto) {
        User user = this.userRepository.findByEmail(dto.email())
                .orElseThrow(() -> new RuntimeException("User Not Found"));

        if(passwordEncoder.matches(dto.password(), user.getPassword())) {
            String token = this.tokenService.generateToken(user);
            return ResponseEntity.ok(new ResponseDto(user.getName(), token));
        }

        return ResponseEntity.badRequest().build();
    }

    @PostMapping(value = "/register")
    public ResponseEntity register (@RequestBody RegisterRequestDto dto) {
        Optional<User> user = this.userRepository.findByEmail(dto.email());

        if(user.isEmpty()) {
            User newUser = User.builder()
                    .email(dto.email())
                    .name(dto.name())
                    .password(passwordEncoder.encode(dto.password()))
                    .build();
            userRepository.save(newUser);

            String token = tokenService.generateToken(newUser);
            return ResponseEntity.ok(new ResponseDto(newUser.getName(), token));
        }

        return ResponseEntity.badRequest().build();
    }

}
