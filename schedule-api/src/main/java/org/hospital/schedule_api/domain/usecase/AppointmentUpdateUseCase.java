package org.hospital.schedule_api.domain.usecase;

import lombok.RequiredArgsConstructor;
import org.hospital.core.domain.entity.Appointments;
import org.hospital.core.infrastructure.database.entitydb.AppointmentsEntity;
import org.hospital.core.infrastructure.database.repository.AppointmentsRepository;
import org.hospital.core.infrastructure.exception.ApplicationException;
import org.hospital.schedule_api.domain.presenter.AppointmentPresenter;
import org.hospital.schedule_api.domain.validator.appointment.create.AppointmentValidatorCreateInterface;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.Optional.ofNullable;

@Service
@RequiredArgsConstructor
public class AppointmentUpdateUseCase {
    public final AppointmentsRepository appointmentsRepository;
    public final AppointmentPresenter appointmentPresenter;
    public final List<AppointmentValidatorCreateInterface> validators;

    public Appointments execute(Appointments appointments) {
        AppointmentsEntity appointmentsEntity = appointmentsRepository.findById(appointments.getId()).orElseThrow(() -> new ApplicationException("Appointment not found"));

        Appointments appointmentsOld = appointmentPresenter.toDomain(appointmentsEntity);

        updateAppointmentData(appointmentsOld, appointments);
        validate(appointmentsOld);
        var appointment = appointmentPresenter.toJpaEntity(appointmentsOld);
        appointment = appointmentsRepository.save(appointment);
        return appointmentPresenter.toDomain(appointment);
    }

    private void updateAppointmentData(Appointments appointmentsOld, Appointments appointments) {
        ofNullable(appointments.getDateHourStart()).ifPresent(appointmentsOld::setDateHourStart);
        ofNullable(appointments.getDateHourEnd()).ifPresent(appointmentsOld::setDateHourEnd);
        ofNullable(appointments.getDateHourScheduled()).ifPresent(appointmentsOld::setDateHourScheduled);
        ofNullable(appointments.getStatusAppointment()).ifPresent(appointmentsOld::setStatusAppointment);
        ofNullable(appointments.getInformation()).ifPresent(appointmentsOld::setInformation);
        ofNullable(appointments.getDescription()).ifPresent(appointmentsOld::setDescription);
    }

    private void validate(Appointments appointments) {
        validators.forEach(validator -> validator.validate(appointments));
    }
}
