package com.samoonpride.backend.model;

import com.samoonpride.backend.enums.StaffEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Setter
@Getter
@NoArgsConstructor
@RequiredArgsConstructor
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = "username")
})
@EntityListeners(AuditingEntityListener.class)
public class Staff {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NonNull
    private String username;

    @NonNull
    @Size(min = 8, message = "Password should be at least 8 characters")
    private String password;

    @NonNull
    @Enumerated(EnumType.STRING)
    private StaffEnum role;

    @OneToMany(mappedBy = "staff")
    private Set<Issue> issues = new HashSet<>();

    @OneToMany(mappedBy = "staff")
    private Set<StaffIssueAssignment> assignees = new HashSet<>();

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime lastModifiedDate;

    // isPending
    public boolean isPending() {
        return this.role.equals(StaffEnum.PENDING);
    }
}
