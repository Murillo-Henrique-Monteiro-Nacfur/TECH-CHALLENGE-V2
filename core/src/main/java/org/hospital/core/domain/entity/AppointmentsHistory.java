package org.hospital.core.domain.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hospital.core.infrastructure.database.entitydb.StatusAppointments;

import java.time.LocalDateTime;


@Builder
@Getter
@Setter
public class AppointmentsHistory {
    private Long id;
    private Long idAppointment;
    private String patientName;
    private String doctorName;
    private LocalDateTime dateHourStart;
    private LocalDateTime dateHourEnd;
    private StatusAppointments statusAppointment;
    private String description;
    private String information;
}
