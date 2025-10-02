package org.hospital.gateway.api.dto.appointmenthistory;

import lombok.Builder;
import lombok.Getter;
import org.springframework.graphql.data.method.annotation.SchemaMapping;

import java.util.List;

@Builder
@Getter
@SchemaMapping(typeName = "AppointmentHistoryPagedResponseDTO")
public class AppointmentHistoryPagedResponseDTO {
    private Long totalElements;
    private Integer totalPages;
    private Integer page;
    private Integer size;
    private List<AppointmentHistoryResponseDTO> appointments;
}
