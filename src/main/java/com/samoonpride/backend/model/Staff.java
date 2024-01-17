package com.samoonpride.backend.model;

import com.samoonpride.backend.enums.StaffEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = "email"),
        @UniqueConstraint(columnNames = "username")
})
public class Staff implements UserDetails {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NonNull
    @Email(message = "Email should be valid")
    private String email;

    @NonNull
    private String username;

    @NonNull
    @Size(min = 8, message = "Password should be at least 8 characters")
    private String password;

    @NonNull
    @Enumerated(EnumType.STRING)
    private StaffEnum role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
