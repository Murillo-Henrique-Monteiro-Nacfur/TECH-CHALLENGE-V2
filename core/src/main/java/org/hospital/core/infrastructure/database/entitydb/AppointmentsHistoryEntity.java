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
public class AppointmentsHistoryEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator", sequenceName = "APPOINTMENTS_HISTORY_ID_seq", allocationSize = 1)
    @Column(name = "ID")
    private Long id;

    @Column(name = "APPOINTMENT_ID")
    private Long appointmentId;

    @Column(name = "DOCTOR_NAME")
    private String doctorName;

    @Column(name = "PATIENT_NAME")
    private String patientName;

    @Column(name = "DATE_HOUR_START")
    private LocalDateTime dateHourStart;

    @Column(name = "DATE_HOUR_END")
    private LocalDateTime dateHourEnd;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private StatusAppointments statusAppointment;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "INFORMATION")
    private String information;
}
