package com.samoonpride.backend.model;

import com.samoonpride.backend.dto.UserDto;
import com.samoonpride.backend.enums.ReportStatus;
import com.samoonpride.backend.enums.UserEnum;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
@Data
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NonNull
    private String title;

    private float latitude;

    private float longitude;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private ReportStatus status = ReportStatus.IN_CONSIDERATION;

    @ManyToOne
    @JoinColumn(name = "staff_id")
    private Staff staff;

    @ManyToOne
    @JoinColumn(name = "line_user_id")
    private LineUser lineUser;

    @OneToMany(mappedBy = "report")
    private Set<Subscribe> subscribes = new HashSet<>();

    @OneToMany(mappedBy = "report")
    private Set<Media> media = new HashSet<>();

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime lastModifiedDate;
}
