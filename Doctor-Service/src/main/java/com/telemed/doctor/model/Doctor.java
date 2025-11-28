package com.telemed.doctor.model;

import com.telemed.doctor.model.enums.Department;
import com.telemed.doctor.model.enums.Gender;
import com.telemed.doctor.model.enums.Specialization;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "doctor")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Doctor {

    @Id
    @Column(name = "doctor_id")
    private Long doctorId;   // manually assigned

   @Column(name = "first_name", nullable = true)
private String firstName;

@Column(name = "last_name", nullable = true)
private String lastName;

@Enumerated(EnumType.STRING)
@Column(nullable = true)
private Specialization specialization;

@Enumerated(EnumType.STRING)
@Column(nullable = true)
private Department department;

@Column(nullable = true)
private Integer experience;
  // years of experience

    private String emailId;
    private String phone;

    @Enumerated(EnumType.STRING)
    private Gender gender ;


    private String address ;


    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
