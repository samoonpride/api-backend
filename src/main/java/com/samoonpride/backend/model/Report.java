package com.samoonpride.backend.model;

import com.samoonpride.backend.enums.ReportStatus;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NonNull
    private String title;

    private float latitude;

    private float longitude;

    @NonNull
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private ReportStatus status = ReportStatus.PENDING;

    @ManyToOne
    @JoinColumn(name = "line_user_id", nullable = false)
    private LineUser lineUser;

    @OneToMany(mappedBy = "report")
    private Set<Subscribe> subscribes = new HashSet<>();

    @CreatedDate
    private String createdDate;

}
