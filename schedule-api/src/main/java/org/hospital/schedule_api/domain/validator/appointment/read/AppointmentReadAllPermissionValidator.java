package org.hospital.schedule_api.domain.validator.appointment.read;


import lombok.RequiredArgsConstructor;
import org.hospital.core.domain.dto.SessionDTO;
import org.hospital.core.domain.service.SessionService;
import org.hospital.core.infrastructure.exception.ApplicationException;
import org.hospital.schedule_api.domain.dto.AppointmentFilterRequestDTO;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AppointmentReadAllPermissionValidator implements AppointmentValidatorReadAllFilteredInterface {

    private final SessionService sessionService;

    @Override
    public void validate(AppointmentFilterRequestDTO appointmentFilterRequestDTO) {
        SessionDTO sessionDTO = sessionService.getSessionDTO();
        if (sessionDTO.isPatient() && appointmentFilterRequestDTO.getPatientUserId() != null && !sessionDTO.getUserId().equals(appointmentFilterRequestDTO.getPatientUserId())) {
            throw new ApplicationException("Patient only have permission to see their own appointments");
        }
        if (sessionDTO.isDoctor() && !sessionDTO.getUserId().equals(appointmentFilterRequestDTO.getDoctorUserId())) {
            throw new ApplicationException("Doctor only have permission to see their own appointments");
        }
    }
}