package com.iyke.Activity_Tracker.dtos.dtoRequests;

import com.iyke.Activity_Tracker.entities.User;
import com.iyke.Activity_Tracker.enums.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
@Data
@Builder
@AllArgsConstructor
@ToString
@NoArgsConstructor
public class ActivityDto {
    @NotEmpty
    @NotBlank
    @NotNull
    @Size(min = 3, max = 15)
    private String name;
    @NotEmpty @NotBlank @NotNull
    @Size(min = 20, max = 1000)
    private String description;
    @Enumerated(EnumType.STRING)
    private Status status;
    @CreationTimestamp
    private Timestamp createdDate;
    @UpdateTimestamp
    private Timestamp upDateDate;
    private Timestamp doneDate;
    @ManyToOne(cascade = {CascadeType.DETACH,CascadeType.MERGE,
            CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinColumn (name = "user_id")
    private User user;
}
