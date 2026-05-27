package ru.andreycherenkov.security;

import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.andreycherenkov.entity.Customer;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
public class CustomerDetails implements UserDetails {

    private final Customer customer;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //todo add roles
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return customer.getPasswordHash();
    }

    @Override
    public String getUsername() {
        return customer.getPhone();
    }

    public UUID getUserId() {
        return customer.getCustomerId();
    }
}
