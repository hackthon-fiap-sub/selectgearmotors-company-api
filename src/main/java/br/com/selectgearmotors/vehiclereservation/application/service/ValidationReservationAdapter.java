package br.com.selectgearmotors.vehiclereservation.application.service;

import br.com.selectgearmotors.vehiclereservation.application.api.exception.ResourceNotFoundException;
import br.com.selectgearmotors.vehiclereservation.gateway.client.ClientWebClient;
import br.com.selectgearmotors.vehiclereservation.gateway.dto.ClientResponseDTO;
import br.com.selectgearmotors.vehiclereservation.gateway.dto.VehicleResponseDTO;
import br.com.selectgearmotors.vehiclereservation.gateway.vehicle.VehicleWebClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
public class ValidationReservationAdapter {

    private final ClientWebClient clientWebClient; //fazer igual o repositorio - criar um adapter
    private final VehicleWebClient vehicleWebClient;

    public ValidationReservationAdapter(ClientWebClient clientWebClient, VehicleWebClient vehicleWebClient) {
        this.clientWebClient = clientWebClient;
        this.vehicleWebClient = vehicleWebClient;
    }

    public boolean validateReservation(UUID clientCode, UUID vehicleCode) {

        ClientResponseDTO clientResponseDTO = clientWebClient.get(clientCode);
        if (clientResponseDTO == null) {
            log.error("Cliente não encontrado: {}", clientCode);
            throw new ResourceNotFoundException("Cliente não encontrado");
        }

        VehicleResponseDTO vehicleResponseDTO = vehicleWebClient.get(vehicleCode);
        if (vehicleResponseDTO == null) {
            log.error("Veículo não encontrado: {}", vehicleCode);
            throw new ResourceNotFoundException("Veículo não encontrado");
        }

        log.info("Validating reservation for client {} and vehicle {}", clientCode, vehicleCode);
        return true;
    }
}
