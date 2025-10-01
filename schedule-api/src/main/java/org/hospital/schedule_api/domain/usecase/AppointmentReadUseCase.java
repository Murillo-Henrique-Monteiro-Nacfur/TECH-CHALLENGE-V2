package org.hospital.schedule_api.domain.usecase;

import lombok.RequiredArgsConstructor;
import org.hospital.core.domain.dto.SessionDTO;
import org.hospital.core.domain.service.SessionService;
import org.hospital.core.infrastructure.database.entitydb.AppointmentsEntity;
import org.hospital.schedule_api.domain.dto.AppointmentFilterRequestDTO;
import org.hospital.core.domain.entity.Appointments;
import org.hospital.core.infrastructure.database.repository.AppointmentsRepository;
import org.hospital.schedule_api.domain.presenter.AppointmentPresenter;
import org.hospital.schedule_api.domain.validator.appointment.read.AppointmentReadAllPermissionValidator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AppointmentReadUseCase {
    private final AppointmentsRepository appointmentsRepository;
    private final AppointmentPresenter appointmentPresenter;
    private final List<AppointmentReadAllPermissionValidator> validators;
    private final SessionService sessionService;


    public Page<Appointments> getAppointmentsByFilter(AppointmentFilterRequestDTO appointmentFilterRequestDTO, Pageable pageable) {
        SessionDTO sessionDTO = sessionService.getSessionDTO();
        validate(appointmentFilterRequestDTO);
        if (sessionDTO.isDoctor() && appointmentFilterRequestDTO.getDoctorUserId() == null) {
            appointmentFilterRequestDTO.setDoctorUserId(sessionDTO.getUserId());
        }
        if (sessionDTO.isPatient() && appointmentFilterRequestDTO.getPatientUserId() == null) {
            appointmentFilterRequestDTO.setPatientUserId(sessionDTO.getUserId());
        }

        Page<AppointmentsEntity> appointmentsEntities = appointmentsRepository.getAllPagedByFilter(appointmentFilterRequestDTO.getDoctorUserId(),
                appointmentFilterRequestDTO.getPatientUserId(),
                appointmentFilterRequestDTO.getStatusAppointment(),
                appointmentFilterRequestDTO.isUpcoming(),
                false,
                pageable);
        return appointmentPresenter.toDomainPaged(appointmentsEntities);

    }

    private void validate(AppointmentFilterRequestDTO appointmentFilterRequestDTO) {
        validators.forEach(validator -> validator.validate(appointmentFilterRequestDTO));
    }
}
