package org.hospital.schedule_api.domain.usecase.appointmenthistory;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hospital.core.infrastructure.database.entitydb.AppointmentsHistoryEntity;
import org.hospital.core.infrastructure.database.entitydb.StatusAppointments;
import org.hospital.core.infrastructure.database.repository.AppointmentsHistoryRepository;
import org.hospital.core.infrastructure.database.repository.AppointmentsRepository;
import org.hospital.core.infrastructure.exception.ApplicationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AppointmentHistoryCreateUseCase {
    public final AppointmentsHistoryRepository appointmentsHistoryRepository;
    public final AppointmentsRepository appointmentsRepository;

    @Transactional
    public void execute(Long idAppointment) {
        if(appointmentsHistoryRepository.findAppointmentHistoryByAppointmentId(idAppointment).stream().findFirst().isPresent()){
            return;
        }
        var appointment = appointmentsRepository.findById(idAppointment).orElseThrow(() -> new ApplicationException("Appointment not found"));
        if (StatusAppointments.COMPLETED.equals(appointment.getStatusAppointment())) {
            var appointmentsHistory = AppointmentsHistoryEntity.builder()
                    .appointmentId(appointment.getId())
                    .patient(appointment.getPatient())
                    .doctor(appointment.getDoctor())
                    .dateHourStart(appointment.getDateHourStart())
                    .dateHourEnd(appointment.getDateHourEnd())
                    .statusAppointment(appointment.getStatusAppointment())
                    .description(appointment.getDescription())
                    .information(appointment.getInformation())
                    .build();
            appointmentsHistoryRepository.save(appointmentsHistory);
        }
        log.info("Appointment with ID {} is not completed. No history record created.", idAppointment);
    }

}
