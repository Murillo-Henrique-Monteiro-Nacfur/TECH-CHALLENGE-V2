package org.hospital.schedule_api.domain.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hospital.appointment.StatusAppointment;

import java.util.List;

@Builder
@Getter
@Setter
public class AppointmentFilterRequestDTO {
    private Long id;
    private Long doctorUserId;
    private Long patientUserId;
    private List<StatusAppointment> statusAppointment;
    private boolean upcoming;
}
