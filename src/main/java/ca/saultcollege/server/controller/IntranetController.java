package ca.saultcollege.server.controller;

import ca.saultcollege.server.data.Account;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class IntranetController {

    @PostMapping("/signup")
    private ResponseEntity<String> signUp(@RequestBody Account signUpInfo) {
        return ResponseEntity.ok("Created an account for " + signUpInfo.getEmail() + "\nID #: " + signUpInfo.getID());
    }

    @GetMapping("/user")
    private ResponseEntity<String> getUser() {
        return ResponseEntity.ok("Jimmy Smith");
    }
}
