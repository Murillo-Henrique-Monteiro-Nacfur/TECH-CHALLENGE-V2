package org.hospital.schedule_api.domain.validator.appointment.update;


import lombok.RequiredArgsConstructor;
import org.hospital.core.domain.entity.Appointments;
import org.hospital.core.infrastructure.database.entitydb.StatusAppointments;
import org.hospital.core.infrastructure.database.repository.AppointmentsRepository;
import org.hospital.core.infrastructure.exception.ApplicationException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AppointmentReopenValidator implements AppointmentValidatorUpdateInterface {
    private final AppointmentsRepository appointmentsRepository;
    @Override
    public void validate(Appointments appointment) {
        var appointmentInDB = appointmentsRepository.findById(appointment.getId())
                .orElseThrow(() -> new ApplicationException("Consulta não encontrada"));
        if(appointmentInDB.getStatusAppointment().equals(StatusAppointments.COMPLETED) && !appointment.getStatusAppointment().equals(StatusAppointments.COMPLETED)){
            throw new ApplicationException("Consulta finalizada não pode ser reaberta");
        }
    }
}
