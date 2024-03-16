package com.samoonpride.backend.model;

import com.samoonpride.backend.enums.IssueStatus;
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
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class Issue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NonNull
    private String title;

    private String thumbnailPath;

    private float latitude;

    private float longitude;

    @Enumerated(EnumType.STRING)
    private IssueStatus status = IssueStatus.IN_CONSIDERATION;

    @ManyToOne
    @JoinColumn(name = "staff_id")
    private Staff staff;

    @ManyToOne
    @JoinColumn(name = "line_user_id")
    private LineUser lineUser;

    @ManyToOne
    @JoinColumn(name = "duplicate_issue_id")
    private Issue duplicateIssue;

    @OneToMany(mappedBy = "issue")
    private Set<StaffIssueAssignment> assignees = new HashSet<>();

    @OneToMany(mappedBy = "issue")
    private Set<Subscribe> subscribes = new HashSet<>();

    @OneToMany(mappedBy = "issue")
    private Set<Media> media = new HashSet<>();

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime lastModifiedDate;

    @Override
    public String toString() {
        StringBuilder assigneeIdsString = new StringBuilder();
        if (assignees != null && !assignees.isEmpty()) {
            assigneeIdsString.append(assignees.stream()
                    .map(StaffIssueAssignment::getStaff)
                    .map(Staff::getId)
                    .toList());
        } else {
            assigneeIdsString.append("[]");
        }

        String duplicateIssueIdString = (duplicateIssue != null) ? String.valueOf(duplicateIssue.getId()) : "null";

        return String.format("{" +
                        "id= %d," +
                        " title='%s'," +
                        " duplicateIssueId=%s," +
                        " assigneeIds=%s," +
                        " latitude=%f," +
                        " longitude=%f," +
                        " status=%s" +
                        "}",
                id, title, duplicateIssueIdString, assigneeIdsString, latitude, longitude, status
        );
    }
}
