package com.samoonpride.backend.model;

import com.samoonpride.backend.enums.StaffEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = "email"),
        @UniqueConstraint(columnNames = "username")
})
public class Staff {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

}
