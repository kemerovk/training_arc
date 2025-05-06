package me.project.training_arc.controller;


import me.project.training_arc.dto.JwtToken;
import me.project.training_arc.dto.SignInRequest;
import me.project.training_arc.dto.SignUpRequest;
import me.project.training_arc.dto.SignUpResponse;
import me.project.training_arc.model.Credentials;
import me.project.training_arc.service.ClientService;
import me.project.training_arc.service.MyUserDetailsService;
import me.project.training_arc.service.RegistrationService;
import me.project.training_arc.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("credentials")
public class CredentialsController {

    @Autowired
    private RegistrationService service;

    @Autowired
    private ClientService client;

    @Autowired
    private MyUserDetailsService userDetailsService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody SignUpRequest request){
        SignUpResponse credentials = service.register(request);
        if(credentials == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return ResponseEntity.ok(credentials);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") int id)  {

        service.delete(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody SignInRequest request){
        Authentication req = UsernamePasswordAuthenticationToken.unauthenticated(request.login(), request.password());
        Authentication response = authenticationManager.authenticate(req);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/login-jwt")
    public ResponseEntity<?> loginJwt(@RequestBody SignInRequest requestedUser) {
        Credentials user = (Credentials) userDetailsService.loadUserByUsername(requestedUser.login());

        if (user == null || !passwordEncoder.matches(requestedUser.password(), user.getPassword())) {
            return ResponseEntity.badRequest().body("Error");
        }

        JwtToken token = tokenService.generateToken(user);
        return ResponseEntity.ok(token);
    }

    /**
     * Обновляет access токен с помощью refresh токена.
     * <p>
     * Принимает токен, проверяет его валидность и выдает новый access/refresh токен, если время жизни access токена прошло.
     *
     * @param token    Объект {@link JwtToken}, содержащий текущие access и refresh токены.
     * @return 200 OK с новым JWT токеном в теле ответа.
     */
    @PostMapping("/refresh")
    public ResponseEntity<JwtToken> refreshToken(@RequestBody JwtToken token) {
        JwtToken newToken = tokenService.refreshToken(token);
        return ResponseEntity.ok(newToken);
    }

    /**
     * Выполняет выход пользователя из системы.
     * <p>
     * Очищает контекст безопасности Spring Security.
     * Это будет означать, что текущий пользователь больше не будет аутентифицирован в приложении.
     *
     * @return 200 OK с сообщением об успешном выходе.
     */
    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        SecurityContextHolder.clearContext(); // Очистка контекста безопасности
        return ResponseEntity.ok("Logout successful");
    }

}
