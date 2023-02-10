package ca.saultcollege.server.controller;

import ca.saultcollege.server.data.*;
import ca.saultcollege.server.repositories.AccountRepository;
import ca.saultcollege.server.security.JwtTokenUtil;
import ca.saultcollege.server.data.Registry;
import ca.saultcollege.server.repositories.RegistryRepository;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class IntranetController {

    @Autowired
    AuthenticationManager authManager;

    @Autowired
    JwtTokenUtil jwtUtil;

    @Autowired
    RegistryRepository registryRepository;

    @Autowired
    AccountRepository accountRepository;

    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@RequestBody @Valid AuthRequest request) {
        try {
            Authentication authentication = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(), request.getPassword())
            );
            Account account = (Account) authentication.getPrincipal();
            String accessToken = jwtUtil.generateAccessToken(account);
            AuthResponse response = new AuthResponse(account.getEmail(), accessToken);
            return ResponseEntity.ok().body(response);
        } catch( Exception ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

    }

    @PostMapping("/signup")
    private ResponseEntity<String> signUp(@RequestBody Account signUpFormData) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String password = passwordEncoder.encode(signUpFormData.getPassword());
        Account newAccount = new Account(signUpFormData.getEmail(), password);
        Account savedAccount = accountRepository.save(newAccount);
        return ResponseEntity.ok("createAccount(): " + signUpFormData.getEmail());
    }

    @GetMapping("/user")
    private ResponseEntity<String> getUser() {
        return ResponseEntity.ok("Jimmy Smith");
    }

    @GetMapping("/publiccontent")
    private ResponseEntity<String> getPublicContent() {
        return ResponseEntity.ok(getRegistry("public_content"));
    }

    @PutMapping("/publiccontent")
    public ResponseEntity<Boolean> savePublicContent(@RequestBody @Valid Registry content) {
        Boolean result = updateRegistry(content.getRegistryKey(), content.getRegistryValue());
        return ResponseEntity.ok(result);
    }

    @GetMapping("/staffcontent")
    private ResponseEntity<String> getStaffContent() {
        return ResponseEntity.ok(getRegistry("staff_content"));
    }

    @PutMapping("/staffcontent")
    public ResponseEntity<Boolean> saveStaffContent(@RequestBody @Valid Registry content) {
        Boolean result = updateRegistry(content.getRegistryKey(), content.getRegistryValue());
        return ResponseEntity.ok(result);
    }

    private String getRegistry(String registryKey) {
        //Find the record for the registry entry based on the supplied key
        List<Registry> registryEntries = registryRepository.findByRegistryKey(registryKey);
        Registry registryEntry = new Registry();
        if (registryEntries.size() == 0) {
            return "";
        }

        return registryEntries.get(0).getRegistryValue();
    }

    private boolean updateRegistry(String registryKey, String registryValue) {
        //Find the record for the registry entry based on the supplied key
        List<Registry> registryEntries = registryRepository.findByRegistryKey(registryKey);
        Registry registryEntry = new Registry();
        if (registryEntries.size() == 0) {
            registryEntry.setRegistryKey(registryKey);
        } else {
            registryEntry = registryEntries.get(0);
        }
        registryEntry.setRegistryValue(registryValue);
        //Update the registry table with new value
        registryRepository.save(registryEntry);
        return true;
    }
}
