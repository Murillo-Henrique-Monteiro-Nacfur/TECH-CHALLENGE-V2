package org.hospital.schedule_api.domain.usecase;

import lombok.RequiredArgsConstructor;
import org.hospital.core.domain.entity.Appointments;
import org.hospital.core.infrastructure.database.repository.AppointmentsRepository;
import org.hospital.schedule_api.domain.presenter.AppointmentPresenter;
import org.hospital.schedule_api.domain.validator.appointment.create.AppointmentValidatorCreateInterface;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AppointmentCreateUseCase {
    public final AppointmentsRepository appointmentsRepository;
    public final AppointmentPresenter appointmentPresenter;
    public final List<AppointmentValidatorCreateInterface> validators;

    public Appointments execute(Appointments appointments) {
        validate(appointments);
        var appointment = appointmentPresenter.toJpaEntity(appointments);
        appointment = appointmentsRepository.save(appointment);
        return appointmentPresenter.toDomain(appointment);
    }

    private void validate(Appointments appointments) {
        validators.forEach(validator -> validator.validate(appointments));
    }
}
