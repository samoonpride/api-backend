package com.samoonpride.backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

@Table
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Subscribe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "line_user_id")
    private LineUser lineUser;

    @ManyToOne
    @JoinColumn(name = "report_id")
    private Report report;

    @CreatedDate
    private String createdDate;
}
