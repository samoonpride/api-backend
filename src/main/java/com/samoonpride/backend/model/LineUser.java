package com.samoonpride.backend.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@Data
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"user_id"}))
public class LineUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NonNull
    private String userId;

    @NonNull
    private String displayName;

    @OneToMany(mappedBy = "lineUser")
    private Set<Report> reports = new HashSet<>();

    @OneToMany(mappedBy = "lineUser")
    private Set<Subscribe> subscribes = new HashSet<>();

    @CreatedDate
    private String createdDate;
}
