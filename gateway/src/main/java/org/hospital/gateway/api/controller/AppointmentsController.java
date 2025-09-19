package org.hospital.gateway.api.controller;

import lombok.RequiredArgsConstructor;
import org.hospital.gateway.api.dto.AppointmentCreateRequestDTO;
import org.hospital.gateway.api.dto.AppointmentCreateResponseDTO;
import org.hospital.gateway.domain.service.AppointmentsServiceGrpc;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class AppointmentsController {
    private final AppointmentsServiceGrpc appointmentsServiceGrpc;

    @PreAuthorize("isAuthenticated()")
    @MutationMapping
    public AppointmentCreateResponseDTO createAppointment(@Argument("input") AppointmentCreateRequestDTO appointmentRequestDTO) {
        return appointmentsServiceGrpc.createAppointments(appointmentRequestDTO);
    }


}
