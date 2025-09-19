package org.hospital.schedule_api.domain.usecase;

import lombok.RequiredArgsConstructor;
import org.hospital.core.domain.entity.Appointments;
import org.hospital.core.infrastructure.database.repository.AppointmentsRepository;
import org.hospital.schedule_api.domain.mapper.AppointmentMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AppointmentCreateUseCase {
    public final AppointmentsRepository appointmentsRepository;
    public final AppointmentMapper appointmentMapper;
    public Appointments createAppointment(Appointments appointments) {
        //todo validações
        var appointment = appointmentMapper.toJpaEntity(appointments);
        appointment = appointmentsRepository.save(appointment);
        return appointmentMapper.toDomain(appointment);
    }
}
