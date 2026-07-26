package joaoszczypior.dev.login_auth_api.dto;

public record RegisterRequestDto(
      String name,
      String email,
      String password
) {}
