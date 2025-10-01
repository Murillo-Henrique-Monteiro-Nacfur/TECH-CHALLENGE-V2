package org.hospital.schedule_api.domain.validator.appointment.update;


import org.hospital.core.domain.entity.Appointments;

public interface AppointmentValidatorUpdateInterface {
    void validate(Appointments appointment);

}
