package br.com.selectgearmotors.company.application.media.dto;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Tag(name = "File Info DTO")
public class FileInfo {
    private String name;
    private String url;
}
