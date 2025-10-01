package org.hospital.historical.domain.usecase;

import lombok.RequiredArgsConstructor;
import org.hospital.core.domain.entity.AppointmentsHistory;
import org.hospital.core.infrastructure.database.entitydb.AppointmentsHistoryEntity;
import org.hospital.core.infrastructure.database.entitydb.StatusAppointments;
import org.hospital.core.infrastructure.database.repository.AppointmentsHistoryRepository;
import org.hospital.core.infrastructure.database.repository.AppointmentsRepository;
import org.hospital.core.infrastructure.exception.ApplicationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AppointmentHistoryCreateUseCase {
    public final AppointmentsHistoryRepository appointmentsHistoryRepository;
    public final AppointmentsRepository appointmentsRepository;

    @Transactional(readOnly = true)
    public AppointmentsHistory execute(Long idAppointment) {
        var appointment = appointmentsRepository.findById(idAppointment).orElseThrow(() -> new ApplicationException("Appointment not found"));
        if (StatusAppointments.COMPLETED.equals(appointment.getStatusAppointment())) {
            var appointmentsHistory = AppointmentsHistoryEntity.builder()
                    .appointmentId(appointment.getId())
                    .patientName(appointment.getPatient().getName())
                    .doctorName(appointment.getDoctor().getName())
                    .dateHourStart(appointment.getDateHourStart())
                    .dateHourEnd(appointment.getDateHourEnd())
                    .statusAppointment(appointment.getStatusAppointment())
                    .description(appointment.getDescription())
                    .information(appointment.getInformation())
                    .build();
            appointmentsHistoryRepository.save(appointmentsHistory);
        }
        throw new ApplicationException("Appointment is not completed");
    }

}
