package br.com.selectgearmotors.company.application.api.dto.response;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Data
@Tag(name = "Response Message Response")
public class ResponseMessage {
    private String message;
}
