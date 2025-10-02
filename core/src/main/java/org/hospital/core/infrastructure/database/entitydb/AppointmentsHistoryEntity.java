package org.hospital.core.infrastructure.database.entitydb;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "APPOINTMENTS_HISTORY", schema = "hospital")
public class AppointmentsHistoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator", sequenceName = "APPOINTMENTS_HISTORY_ID_seq", allocationSize = 1)
    @Column(name = "ID")
    private Long id;

    @Column(name = "APPOINTMENT_ID")
    private Long appointmentId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "DOCTOR_ID", nullable = false)
    private UserEntity doctor;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "PATIENT_ID", nullable = false)
    private UserEntity patient;

    @Column(name = "DATE_HOUR_START")
    private LocalDateTime dateHourStart;

    @Column(name = "DATE_HOUR_END")
    private LocalDateTime dateHourEnd;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS_APPOINTMENT")
    private StatusAppointments statusAppointment;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "INFORMATION")
    private String information;
}
