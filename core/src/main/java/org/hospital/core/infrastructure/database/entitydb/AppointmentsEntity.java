package org.hospital.core.infrastructure.database.entitydb;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "APPOINTMENTS", schema = "hospital")
public class AppointmentsEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator", sequenceName = "APPOINTMENTS_ID_seq", allocationSize = 1)
    @Column(name = "ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DOCTOR_ID", nullable = false)
    private UserEntity doctor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PATIENT_ID", nullable = false)
    private UserEntity patient;

    @Setter
    @Column(name = "DATE_HOUR_SCHEDULED")
    private LocalDateTime dateHourScheduled;

    @Setter
    @Column(name = "DATE_HOUR_START")
    private LocalDateTime dateHourStart;

    @Setter
    @Column(name = "DATE_HOUR_END")
    private LocalDateTime dateHourEnd;

    @Setter
    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private StatusAppointments statusAppointment;

    @Setter
    @Column(name = "DESCRIPTION")
    private String description;

    @Setter
    @Column(name = "INFORMATION")
    private String information;
}
