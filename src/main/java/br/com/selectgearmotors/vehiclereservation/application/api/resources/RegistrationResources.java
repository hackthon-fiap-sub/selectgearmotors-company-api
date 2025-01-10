package br.com.selectgearmotors.vehiclereservation.application.api.resources;

import br.com.selectgearmotors.vehiclereservation.application.api.dto.request.ReservationRequest;
import br.com.selectgearmotors.vehiclereservation.application.api.dto.response.ReservationResponse;
import br.com.selectgearmotors.vehiclereservation.application.api.exception.ResourceFoundException;
import br.com.selectgearmotors.vehiclereservation.application.api.exception.ResourceNotFoundException;
import br.com.selectgearmotors.vehiclereservation.application.api.mapper.ReservationApiMapper;
import br.com.selectgearmotors.vehiclereservation.application.service.ValidationReservationAdapter;
import br.com.selectgearmotors.vehiclereservation.commons.Constants;
import br.com.selectgearmotors.vehiclereservation.commons.util.RestUtils;
import br.com.selectgearmotors.vehiclereservation.core.domain.Reservation;
import br.com.selectgearmotors.vehiclereservation.core.ports.in.reservation.*;
import br.com.selectgearmotors.vehiclereservation.gateway.client.ClientWebClient;
import br.com.selectgearmotors.vehiclereservation.gateway.dto.ClientResponseDTO;
import br.com.selectgearmotors.vehiclereservation.gateway.dto.VehicleResponseDTO;
import br.com.selectgearmotors.vehiclereservation.gateway.vehicle.VehicleWebClient;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/v1/registrations")
@CrossOrigin(origins = "*", allowedHeaders = "Content-Physical, Authorization", maxAge = 3600)
public class RegistrationResources {

    private final CreateReservationPort createReservationPort;
    private final DeleteReservationPort deleteReservationPort;
    private final FindByIdReservationPort findByIdReservationPort;
    private final FindReservationsPort findProductCategoriesPort;
    private final UpdateReservationPort updateReservationPort;
    private final ReservationApiMapper reservationApiMapper;
    private final ValidationReservationAdapter validationReservationAdapter;

    @Autowired
    public RegistrationResources(CreateReservationPort createReservationPort, DeleteReservationPort deleteReservationPort, FindByIdReservationPort findByIdReservationPort, FindReservationsPort findProductCategoriesPort, UpdateReservationPort updateReservationPort, ReservationApiMapper reservationApiMapper, ValidationReservationAdapter validationReservationAdapter) {
        this.createReservationPort = createReservationPort;
        this.deleteReservationPort = deleteReservationPort;
        this.findByIdReservationPort = findByIdReservationPort;
        this.findProductCategoriesPort = findProductCategoriesPort;
        this.updateReservationPort = updateReservationPort;
        this.reservationApiMapper = reservationApiMapper;
        this.validationReservationAdapter = validationReservationAdapter;
    }

    @Operation(summary = "Create a new Reservation", tags = {"productCategorys", "post"})
    @ApiResponse(responseCode = "201", content = {
            @Content(schema = @Schema(implementation = RegistrationResources.class), mediaType = "application/json")})
    @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ReservationResponse> save(@Valid @RequestBody ReservationRequest request) {
        try {
            log.info("Chegada do objeto para ser salvo {}", request);
            Reservation reservation = reservationApiMapper.fromRequest(request);

            boolean validated = validationReservationAdapter.validateReservation(reservation.getBuyerId(), reservation.getVehicleId());
            if (!validated) {
                throw new ResourceNotFoundException("Cliente ou veículo não encontrado");
            }

            Reservation saved = createReservationPort.save(reservation);
            if (saved == null) {
                throw new ResourceNotFoundException("Produto não encontroado ao cadastrar");
            }

            ReservationResponse reservationPhysicalResponse = reservationApiMapper.fromEntity(saved);
            URI location = RestUtils.getUri(reservationPhysicalResponse.getId());
            return ResponseEntity.created(location).body(reservationPhysicalResponse);
        } catch (Exception ex) {
            log.error(Constants.ERROR_EXCEPTION_RESOURCE + "-save: {}", ex.getMessage());
            return ResponseEntity.ok().build();
        }
    }

    @Operation(summary = "Update a Reservation by Id", tags = {"productCategorys", "put"})
    @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = RegistrationResources.class), mediaType = "application/json")})
    @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})
    @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())})
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ReservationResponse> update(@PathVariable("id") Long id, @Valid @RequestBody ReservationRequest request) {
        try {
            log.info("Chegada do objeto para ser alterado {}", request);
            var productCategory = reservationApiMapper.fromRequest(request);
            Reservation updated = updateReservationPort.update(id, productCategory);
            if (updated == null) {
                throw new ResourceFoundException("\"Produto não encontroado ao atualizar");
            }

            ReservationResponse reservationResponse = reservationApiMapper.fromEntity(updated);
            return ResponseEntity.ok(reservationResponse);
        } catch (Exception ex) {
            log.error(Constants.ERROR_EXCEPTION_RESOURCE + "-update: {}", ex.getMessage());
            return ResponseEntity.ok().build();
        }
    }

    @Operation(summary = "Update a Reservation by Id", tags = {"productCategorys", "put"})
    @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = RegistrationResources.class), mediaType = "application/json")})
    @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})
    @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())})
    @PutMapping("/{id}/sold")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ReservationResponse> updateDone(@PathVariable("id") Long id) {
        try {
            Reservation updated = updateReservationPort.updateDone(id);
            if (updated == null) {
                throw new ResourceFoundException("\"Produto não encontroado ao atualizar");
            }

            ReservationResponse reservationResponse = reservationApiMapper.fromEntity(updated);
            return ResponseEntity.ok(reservationResponse);
        } catch (Exception ex) {
            log.error(Constants.ERROR_EXCEPTION_RESOURCE + "-update: {}", ex.getMessage());
            return ResponseEntity.ok().build();
        }
    }

    @Operation(summary = "Update a Reservation by Id", tags = {"productCategorys", "put"})
    @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = RegistrationResources.class), mediaType = "application/json")})
    @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})
    @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())})
    @PutMapping("/{id}/cancelled")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ReservationResponse> updateCancelled(@PathVariable("id") Long id) {
        try {
            Reservation updated = updateReservationPort.updateCancelled(id);
            if (updated == null) {
                throw new ResourceFoundException("\"Produto não encontroado ao atualizar");
            }

            ReservationResponse reservationResponse = reservationApiMapper.fromEntity(updated);
            return ResponseEntity.ok(reservationResponse);
        } catch (Exception ex) {
            log.error(Constants.ERROR_EXCEPTION_RESOURCE + "-update: {}", ex.getMessage());
            return ResponseEntity.ok().build();
        }
    }

    @Operation(summary = "Retrieve all Reservation", tags = {"productCategorys", "get", "filter"})
    @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = RegistrationResources.class), mediaType = "application/json")})
    @ApiResponse(responseCode = "204", description = "There are no Associations", content = {
            @Content(schema = @Schema())})
    @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<ReservationResponse>> findAll() {
        List<Reservation> reservationList = findProductCategoriesPort.findAll();
        List<ReservationResponse> reservationResponse = reservationApiMapper.map(reservationList);
        return reservationResponse.isEmpty() ?
                ResponseEntity.noContent().build() :
                ResponseEntity.ok(reservationResponse);
    }

    @Operation(
            summary = "Retrieve a Reservation by Id",
            description = "Get a Reservation object by specifying its id. The response is Association object with id, title, description and published status.",
            tags = {"productCategorys", "get"})
    @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = RegistrationResources.class), mediaType = "application/json")})
    @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())})
    @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ReservationResponse> findOne(@PathVariable("id") Long id) {
        try {
            Reservation reservationSaved = findByIdReservationPort.findById(id);
            if (reservationSaved == null) {
                throw new ResourceFoundException("Produto não encontrado ao buscar por código");
            }

            ReservationResponse reservationPhysicalResponse = reservationApiMapper.fromEntity(reservationSaved);
            return ResponseEntity.ok(reservationPhysicalResponse);

        } catch (Exception ex) {
            log.error(Constants.ERROR_EXCEPTION_RESOURCE + "-findOne: {}", ex.getMessage());
            return ResponseEntity.ok().build();
        }
    }

    @Operation(
            summary = "Retrieve a Reservation by Id",
            description = "Get a Reservation object by specifying its id. The response is Association object with id, title, description and published status.",
            tags = {"productCategorys", "get"})
    @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = RegistrationResources.class), mediaType = "application/json")})
    @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())})
    @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})
    @GetMapping("/vehicle/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ReservationResponse> findByVehicleId(@PathVariable("id") UUID id) {
        try {
            Reservation reservationSaved = findByIdReservationPort.findByVehicleId(id);
            if (reservationSaved == null) {
                throw new ResourceFoundException("Produto não encontrado ao buscar por código");
            }

            ReservationResponse reservationPhysicalResponse = reservationApiMapper.fromEntity(reservationSaved);
            return ResponseEntity.ok(reservationPhysicalResponse);

        } catch (Exception ex) {
            log.error(Constants.ERROR_EXCEPTION_RESOURCE + "-findByVehicleId: {}", ex.getMessage());
            return ResponseEntity.ok().build();
        }
    }

    @Operation(
            summary = "Retrieve a Reservation by Id",
            description = "Get a Reservation object by specifying its id. The response is Association object with id, title, description and published status.",
            tags = {"productCategorys", "get"})
    @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = RegistrationResources.class), mediaType = "application/json")})
    @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())})
    @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})
    @GetMapping("/buyer/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ReservationResponse> findByBuyerId(@PathVariable("code") UUID id) {
        try {
            Reservation reservationSaved = findByIdReservationPort.findByBuyerId(id);
            if (reservationSaved == null) {
                throw new ResourceFoundException("Produto não encontrado ao buscar por código");
            }

            ReservationResponse reservationPhysicalResponse = reservationApiMapper.fromEntity(reservationSaved);
            return ResponseEntity.ok(reservationPhysicalResponse);

        } catch (Exception ex) {
            log.error(Constants.ERROR_EXCEPTION_RESOURCE + "-findByBuyerId: {}", ex.getMessage());
            return ResponseEntity.ok().build();
        }
    }

    @Operation(summary = "Delete a Reservation by Id", tags = {"productCategorytrus", "delete"})
    @ApiResponse(responseCode = "204", content = {@Content(schema = @Schema())})
    @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> remove(@PathVariable("id") Long id) {
        deleteReservationPort.remove(id);
        return ResponseEntity.noContent().build();
    }
}