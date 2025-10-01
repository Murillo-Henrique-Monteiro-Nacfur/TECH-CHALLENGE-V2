package org.hospital.gateway.api.dto.appointment;

import lombok.Builder;
import lombok.Getter;
import org.hospital.appointment.StatusAppointment;
import org.springframework.graphql.data.method.annotation.SchemaMapping;

import java.util.List;

@Builder
@Getter
@SchemaMapping(typeName = "AppointmentFindAllFilterRequestInput")
public class AppointmentFindAllFilterRequestDTO {
    private Long doctorId;
    private Long patientId;
    private List<StatusAppointment> statusAppointments;
    private boolean upcoming;
}
