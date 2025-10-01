package org.hospital.schedule_api.domain.validator.appointment.read;


import org.hospital.core.domain.entity.Appointments;
import org.hospital.schedule_api.domain.dto.AppointmentFilterRequestDTO;

public interface AppointmentValidatorReadAllFilteredInterface {
    void validate(AppointmentFilterRequestDTO appointmentFilterRequestDTO);

}
