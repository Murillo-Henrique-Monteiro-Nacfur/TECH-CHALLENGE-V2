package org.hospital.schedule_api.domain.validator.appointment;


import lombok.RequiredArgsConstructor;
import org.hospital.core.domain.entity.Appointments;
import org.hospital.core.domain.entity.User;
import org.hospital.core.domain.service.UserServiceCore;
import org.hospital.core.infrastructure.database.entitydb.UserRoles;
import org.hospital.core.infrastructure.exception.ApplicationException;
import org.hospital.schedule_api.domain.validator.appointment.create.AppointmentValidatorCreateInterface;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AppointmentDoctorAndPatientValidator implements AppointmentValidatorCreateInterface {

    private final UserServiceCore userServiceCore;

    @Override
    public void validate(Appointments appointment) {
        verifyDoctor(appointment.getDoctor().getId());
        verifyPatient(appointment.getPatient().getId());
    }

    private void verifyDoctor(Long doctorId) {
        User doctor = userServiceCore.getUserById(doctorId).orElseThrow(() -> new ApplicationException("Médico não encontrado"));
        if(!doctor.getRoles().contains(UserRoles.DOCTOR)){
            throw new ApplicationException("O usuário informado como médico não possui a role de médico");
        }
    }
    private void verifyPatient(Long patientId) {
        User patient = userServiceCore.getUserById(patientId).orElseThrow(() -> new ApplicationException("Paciente não encontrado"));
        if(!patient.getRoles().contains(UserRoles.PATIENT)){
            throw new ApplicationException("O usuário informado como médico não possui a role de médico");
        }
    }
}
