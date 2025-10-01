package org.hospital.schedule_api.domain.validator.appointment;


import org.hospital.core.domain.entity.Appointments;
import org.hospital.core.infrastructure.exception.ApplicationException;
import org.hospital.schedule_api.domain.validator.appointment.create.AppointmentValidatorCreateInterface;
import org.hospital.schedule_api.domain.validator.appointment.update.AppointmentValidatorUpdateInterface;
import org.springframework.stereotype.Service;

@Service
public class AppointmentDateStartEndValidator implements AppointmentValidatorCreateInterface, AppointmentValidatorUpdateInterface {

    @Override
    public void validate(Appointments appointment) {
        if(appointment.isDateStartAfterDateEnd()){
            throw new ApplicationException("A data de início não pode ser maior que a data de fim");
        }
    }
}
