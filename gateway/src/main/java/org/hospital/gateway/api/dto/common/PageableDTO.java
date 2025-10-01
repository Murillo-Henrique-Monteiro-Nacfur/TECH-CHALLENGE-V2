package org.hospital.gateway.api.dto.common;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.graphql.data.method.annotation.SchemaMapping;

@Getter
@Setter
@Builder
@SchemaMapping(typeName = "Pageable")
public class PageableDTO {
    private Integer page;
    private Integer size;
}
