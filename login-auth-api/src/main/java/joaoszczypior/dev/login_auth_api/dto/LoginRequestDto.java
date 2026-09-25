package joaoszczypior.dev.login_auth_api.dto;

public record LoginRequestDto(
        String email,
        String password
) {}
