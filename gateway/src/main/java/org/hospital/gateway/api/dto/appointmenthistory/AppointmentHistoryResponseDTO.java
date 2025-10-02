package org.hospital.gateway.api.dto.appointmenthistory;

import lombok.Builder;
import lombok.Getter;
import org.hospital.core.infrastructure.database.entitydb.StatusAppointments;
import org.springframework.graphql.data.method.annotation.SchemaMapping;

import java.time.LocalDateTime;

@Builder
@Getter
@SchemaMapping(typeName = "AppointmentHistoryResponseDTO")
public class AppointmentHistoryResponseDTO {
    private Long id;
    private Long idAppointment;
    private String patientName;
    private String doctorName;
    private LocalDateTime dateHourStart;
    private LocalDateTime dateHourEnd;
    private String statusAppointment;
    private String description;
    private String information;
}
