package org.hospital.schedule_api.domain.validator.appointment.create;


import org.hospital.core.domain.entity.Appointments;

public interface AppointmentValidatorCreateInterface{
    void validate(Appointments appointment);

}
