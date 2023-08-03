package ssubob.ssubob.user.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import ssubob.ssubob.user.domain.User;
import ssubob.ssubob.user.request.UserCreate;
import ssubob.ssubob.user.service.UserService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @PostMapping("/create")
    public ResponseEntity<User> create(@RequestBody @Valid UserCreate userCreate) {
        User user = userService.create(userCreate);
        if (user == null)
            return ResponseEntity.badRequest().build();
        return ResponseEntity.ok().body(user);
    }
}
