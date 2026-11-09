package com.bonar.Shelf_Guard.auth;

import com.bonar.Shelf_Guard.entities.Users;
import com.bonar.Shelf_Guard.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsersDetailService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Optional<Users> account = userRepository.findByEmail(email);
        if (account.isEmpty()) throw new UsernameNotFoundException("User not found!");
        Users user = account.get();

        return new UsersAuthDetails(user);
    }
}
