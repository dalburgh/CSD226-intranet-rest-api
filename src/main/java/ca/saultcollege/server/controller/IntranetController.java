package ca.saultcollege.server.controller;

import ca.saultcollege.server.data.*;
import ca.saultcollege.server.security.JwtTokenUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import jakarta.validation.Valid;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class IntranetController {

    @Autowired
    AuthenticationManager authManager;

    @Autowired
    JwtTokenUtil jwtUtil;

    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@RequestBody @Valid AuthRequest request) {
        try {
            Authentication authentication = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(), request.getPassword())
            );
            Account account = new Account();
            account.setId(1);
            account.setEmail(authentication.getPrincipal().toString());
            String accessToken = jwtUtil.generateAccessToken(account);
            AuthResponse response = new AuthResponse(account.getEmail(), accessToken);
            return ResponseEntity.ok().body(response);
        } catch( Exception ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

    }

    @PostMapping("/signup")
    private ResponseEntity<String> signUp(@RequestBody Account signUpInfo) {
        return ResponseEntity.ok("Created an account for " + signUpInfo.getEmail() + "\nID #: " + signUpInfo.getId());
    }

    @GetMapping("/user")
    private ResponseEntity<String> getUser() {
        return ResponseEntity.ok("Jimmy Smith");
    }

    @GetMapping("/publiccontent")
    private ResponseEntity<String> getPublicContent() {
        return ResponseEntity.ok("Public Content");
    }

    @GetMapping("/staffcontent")
    private ResponseEntity<String> getStaffContent() {
        return ResponseEntity.ok("Staff Content");
    }
}
