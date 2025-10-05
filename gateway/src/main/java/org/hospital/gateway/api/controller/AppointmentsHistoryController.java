package org.hospital.gateway.api.controller;

import lombok.RequiredArgsConstructor;
import org.hospital.gateway.api.dto.appointmenthistory.AppointmentHistoryPagedResponseDTO;
import org.hospital.gateway.api.dto.common.PageableDTO;
import org.hospital.gateway.domain.service.AppointmentsHistoryServiceGrpc;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class AppointmentsHistoryController {
    private final AppointmentsHistoryServiceGrpc appointmentsServiceGrpc;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_NURSE')")
    @QueryMapping
    public AppointmentHistoryPagedResponseDTO findAllAppointmentsHistory(@Argument("pageable") PageableDTO pageable) {
        return appointmentsServiceGrpc.findAllAppointmentsHistory(pageable);
    }

}
