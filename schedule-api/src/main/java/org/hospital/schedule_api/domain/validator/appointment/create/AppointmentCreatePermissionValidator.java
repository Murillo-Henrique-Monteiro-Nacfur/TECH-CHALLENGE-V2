package org.hospital.schedule_api.domain.validator.appointment.create;


import lombok.RequiredArgsConstructor;
import org.hospital.core.domain.dto.SessionDTO;
import org.hospital.core.domain.entity.Appointments;
import org.hospital.core.domain.service.SessionService;
import org.hospital.core.infrastructure.database.entitydb.UserRoles;
import org.hospital.core.infrastructure.exception.ApplicationException;
import org.hospital.schedule_api.domain.validator.appointment.update.AppointmentValidatorUpdateInterface;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AppointmentCreatePermissionValidator implements AppointmentValidatorCreateInterface, AppointmentValidatorUpdateInterface {

    private final SessionService sessionService;

    @Override
    public void validate(Appointments appointment) {
        SessionDTO sessionDTO = sessionService.getSessionDTO();
        if(sessionDTO.getRoles().stream().noneMatch(userRoles -> List.of(UserRoles.ADMIN,UserRoles.DOCTOR, UserRoles.NURSE).contains(userRoles))){
            throw new ApplicationException("User does not have permission to create an appointment");
        }
        if(sessionDTO.isDoctor() && !sessionDTO.getUserId().equals(appointment.getDoctor().getId())){
            throw new ApplicationException("Doctor can only create appointments for themselves");
        }
    }
}
