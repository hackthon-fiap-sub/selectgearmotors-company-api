package br.com.selectgearmotors.vehiclereservation.gateway.vehicle;

import br.com.selectgearmotors.vehiclereservation.commons.Constants;
import br.com.selectgearmotors.vehiclereservation.gateway.dto.VehicleResponseDTO;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.UUID;

@Component
public class VehicleWebClient {

    private final HttpServletRequest request;

    @Value("${gateway.vehicle.url}")
    private String baseUrl;

    private WebClient webClient;

    public VehicleWebClient(HttpServletRequest request, WebClient.Builder webClientBuilder) {
        this.request = request;
        this.webClient = webClientBuilder.build();
    }

    @PostConstruct
    private void init() {
        this.webClient = this.webClient.mutate().baseUrl(baseUrl).build();
    }

    public VehicleResponseDTO get(UUID clientCode) {
        // Pega o token armazenado no filtro
        String bearerToken = (String) request.getAttribute(Constants.BEARER_TOKEN_ATTRIBUTE);

        return webClient.get()
                .uri("/vehicles/code/{clientCode}", clientCode)
                .headers(headers -> headers.setBearerAuth(bearerToken))
                .retrieve()
                .bodyToMono(VehicleResponseDTO.class)
                .block();
    }

    public void setStatus(String code) {
        // Pega o token armazenado no filtro
        String bearerToken = (String) request.getAttribute(Constants.BEARER_TOKEN_ATTRIBUTE);

        webClient.put()
                .uri("/vehicles/{code}/reserved", code)
                .headers(headers -> headers.setBearerAuth(bearerToken))
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }

    public String getBaseUrl() {
        return baseUrl;
    }
}
