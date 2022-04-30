package ru.bookcrossing.BookcrossingServer.refresh.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class RefreshRequest {

    @Schema(description = "Токен обновления", example = "cac2ce3e-9ff0-49a7-8afc-3dcae34eafea", required = true)
    private String refresh;
}
