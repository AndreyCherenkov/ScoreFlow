package ru.andreycherenkov.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import ru.andreycherenkov.repository.CustomerRepository;

@RequiredArgsConstructor
@Component
public class AuthUserDetailsService implements UserDetailsService {

    private final CustomerRepository customerRepository;

    @Override
    public UserDetails loadUserByUsername(String phone) throws UsernameNotFoundException {
        return customerRepository.findByPhone(phone)
                .map(CustomerDetails::new)
                .orElseThrow(
                        () -> new UsernameNotFoundException("Customer not found with phone: " + phone)
                );
    }
}
