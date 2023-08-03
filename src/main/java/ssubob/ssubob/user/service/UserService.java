package ssubob.ssubob.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ssubob.ssubob.user.domain.User;
import ssubob.ssubob.user.repository.UserRepository;
import ssubob.ssubob.user.request.UserCreate;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("user not found"));
    }

    public User create(UserCreate userCreate) {
        try {

            userRepository.findByEmail(userCreate.getEmail()).ifPresent(user -> {
                throw new IllegalArgumentException("email " + user.getEmail() + " is already exist");
            });
        }catch (Exception e){
            return null;
        }

        User user = User.builder()
                .email(userCreate.getEmail())
                .password(bCryptPasswordEncoder.encode(userCreate.getPassword()))
                .build();
        userRepository.save(user);
        return user;
    }
}
