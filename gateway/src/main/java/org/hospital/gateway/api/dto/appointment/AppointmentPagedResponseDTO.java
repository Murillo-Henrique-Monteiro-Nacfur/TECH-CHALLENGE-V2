package org.hospital.gateway.api.dto.appointment;

import lombok.Builder;
import lombok.Getter;
import org.springframework.graphql.data.method.annotation.SchemaMapping;

import java.util.List;

@Builder
@Getter
@SchemaMapping(typeName = "AppointmentPagedResponseDTO")
public class AppointmentPagedResponseDTO {
    private Long totalElements;
    private Integer totalPages;
    private Integer page;
    private Integer size;
    private List<AppointmentResponseDTO> appointments;
}
