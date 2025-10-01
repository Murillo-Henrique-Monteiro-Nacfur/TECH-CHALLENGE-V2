package org.hospital.gateway.api.controller;

import lombok.RequiredArgsConstructor;
import org.hospital.gateway.api.dto.appointment.*;
import org.hospital.gateway.api.dto.common.PageableDTO;
import org.hospital.gateway.domain.service.AppointmentsServiceGrpc;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class AppointmentsController {
    private final AppointmentsServiceGrpc appointmentsServiceGrpc;

    @PreAuthorize("hasAnyRole('ROLE_DOCTOR', 'ROLE_NURSE')")
    @MutationMapping
    public AppointmentCreateUpdateResponseDTO createAppointment(@Argument("input") AppointmentCreateRequestDTO appointmentRequestDTO) {
        return appointmentsServiceGrpc.createAppointments(appointmentRequestDTO);
    }

    @PreAuthorize("hasAnyRole('ROLE_DOCTOR', 'ROLE_NURSE', 'ROLE_PATIENT')")
    @QueryMapping
    public AppointmentPagedResponseDTO findAllAppointments(@Argument("input") AppointmentFindAllFilterRequestDTO appointmentFindAllFilterRequestDTO,
                                                           @Argument("pageable") PageableDTO pageable){
       return appointmentsServiceGrpc.findAllAppointments(appointmentFindAllFilterRequestDTO, pageable);
    }

    @PreAuthorize("hasAnyRole('ROLE_DOCTOR', 'ROLE_NURSE')")
    @MutationMapping
    public AppointmentCreateUpdateResponseDTO updateAppointment(@Argument("input") AppointmentUpdateRequestDTO appointmentUpdateStatusRequestDTO){
      return appointmentsServiceGrpc.updateAppointment(appointmentUpdateStatusRequestDTO);
    }
}
