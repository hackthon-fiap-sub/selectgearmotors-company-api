package br.com.selectgearmotors.company.application.api.resources;

import br.com.selectgearmotors.company.application.api.dto.request.CarSellerRequest;
import br.com.selectgearmotors.company.application.api.dto.response.CarSellerResponse;
import br.com.selectgearmotors.company.application.api.exception.ResourceFoundException;
import br.com.selectgearmotors.company.application.api.exception.ResourceNotFoundException;
import br.com.selectgearmotors.company.application.api.mapper.CarSellerApiMapper;
import br.com.selectgearmotors.company.commons.Constants;
import br.com.selectgearmotors.company.commons.util.RestUtils;
import br.com.selectgearmotors.company.core.domain.CarSeller;
import br.com.selectgearmotors.company.core.ports.in.carseller.*;
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

@Slf4j
@RestController
@RequestMapping("/v1/car-sellers")
@CrossOrigin(origins = "*", allowedHeaders = "Content-Physical, Authorization", maxAge = 3600)
public class CarSellerResources {

    private final CreateCarSellerPort createCarSellerPort;
    private final DeleteCarSellerPort deleteCarSellerPort;
    private final FindByIdCarSellerPort findByIdCarSellerPort;
    private final FindCarSellersPort findProductCategoriesPort;
    private final UpdateCarSellerPort updateCarSellerPort;
    private final CarSellerApiMapper carSellerPhysicalApiMapper;

    @Autowired
    public CarSellerResources(CreateCarSellerPort createCarSellerPort, DeleteCarSellerPort deleteCarSellerPort, FindByIdCarSellerPort findByIdCarSellerPort, FindCarSellersPort findProductCategoriesPort, UpdateCarSellerPort updateCarSellerPort, CarSellerApiMapper carSellerPhysicalApiMapper) {
        this.createCarSellerPort = createCarSellerPort;
        this.deleteCarSellerPort = deleteCarSellerPort;
        this.findByIdCarSellerPort = findByIdCarSellerPort;
        this.findProductCategoriesPort = findProductCategoriesPort;
        this.updateCarSellerPort = updateCarSellerPort;
        this.carSellerPhysicalApiMapper = carSellerPhysicalApiMapper;
    }

    @Operation(summary = "Create a new CarSeller", tags = {"productCategorys", "post"})
    @ApiResponse(responseCode = "201", content = {
            @Content(schema = @Schema(implementation = CarSellerResources.class), mediaType = "application/json")})
    @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<CarSellerResponse> save(@Valid @RequestBody CarSellerRequest request) {
        try {
            log.info("Chegada do objeto para ser salvo {}", request);
            CarSeller carSeller = carSellerPhysicalApiMapper.fromRequest(request);
            CarSeller saved = createCarSellerPort.save(carSeller);
            if (saved == null) {
                throw new ResourceNotFoundException("Produto não encontroado ao cadastrar");
            }

            CarSellerResponse carSellerPhysicalResponse = carSellerPhysicalApiMapper.fromEntity(saved);
            URI location = RestUtils.getUri(carSellerPhysicalResponse.getId());
            return ResponseEntity.created(location).body(carSellerPhysicalResponse);
        } catch (Exception ex) {
            log.error(Constants.ERROR_EXCEPTION_RESOURCE + "-save: {}", ex.getMessage());
            return ResponseEntity.ok().build();
        }
    }

    @Operation(summary = "Update a CarSeller by Id", tags = {"productCategorys", "put"})
    @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = CarSellerResources.class), mediaType = "application/json")})
    @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})
    @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())})
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<CarSellerResponse> update(@PathVariable("id") Long id, @Valid @RequestBody CarSellerRequest request) {
        try {
            log.info("Chegada do objeto para ser alterado {}", request);
            var productCategory = carSellerPhysicalApiMapper.fromRequest(request);
            CarSeller updated = updateCarSellerPort.update(id, productCategory);
            if (updated == null) {
                throw new ResourceFoundException("\"Produto não encontroado ao atualizar");
            }

            CarSellerResponse carSellerPhysicalResponse = carSellerPhysicalApiMapper.fromEntity(updated);
            return ResponseEntity.ok(carSellerPhysicalResponse);
        } catch (Exception ex) {
            log.error(Constants.ERROR_EXCEPTION_RESOURCE + "-update: {}", ex.getMessage());
            return ResponseEntity.ok().build();
        }
    }

    @Operation(summary = "Retrieve all CarSeller", tags = {"productCategorys", "get", "filter"})
    @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = CarSellerResources.class), mediaType = "application/json")})
    @ApiResponse(responseCode = "204", description = "There are no Associations", content = {
            @Content(schema = @Schema())})
    @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<CarSellerResponse>> findAll() {
        List<CarSeller> carSellerList = findProductCategoriesPort.findAll();
        List<CarSellerResponse> carSellerPhysicalResponse = carSellerPhysicalApiMapper.map(carSellerList);
        return carSellerPhysicalResponse.isEmpty() ?
                ResponseEntity.noContent().build() :
                ResponseEntity.ok(carSellerPhysicalResponse);
    }

    @Operation(
            summary = "Retrieve a CarSeller by Id",
            description = "Get a CarSeller object by specifying its id. The response is Association object with id, title, description and published status.",
            tags = {"productCategorys", "get"})
    @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = CarSellerResources.class), mediaType = "application/json")})
    @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())})
    @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<CarSellerResponse> findOne(@PathVariable("id") Long id) {
        try {
            CarSeller carSellerSaved = findByIdCarSellerPort.findById(id);
            if (carSellerSaved == null) {
                throw new ResourceFoundException("Produto não encontrado ao buscar por código");
            }

            CarSellerResponse carSellerPhysicalResponse = carSellerPhysicalApiMapper.fromEntity(carSellerSaved);
            return ResponseEntity.ok(carSellerPhysicalResponse);

        } catch (Exception ex) {
            log.error(Constants.ERROR_EXCEPTION_RESOURCE + "-findOne: {}", ex.getMessage());
            return ResponseEntity.ok().build();
        }
    }

    @Operation(
            summary = "Retrieve a CarSeller by Id",
            description = "Get a CarSeller object by specifying its id. The response is Association object with id, title, description and published status.",
            tags = {"productCategorys", "get"})
    @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = CarSellerResources.class), mediaType = "application/json")})
    @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())})
    @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})
    @GetMapping("/carSeller/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<CarSellerResponse> findByClientId(@PathVariable("id") Long id) {
        try {
            CarSeller carSellerSaved = findByIdCarSellerPort.findByCompanyId(id);
            if (carSellerSaved == null) {
                throw new ResourceFoundException("Produto não encontrado ao buscar por código");
            }

            CarSellerResponse carSellerPhysicalResponse = carSellerPhysicalApiMapper.fromEntity(carSellerSaved);
            return ResponseEntity.ok(carSellerPhysicalResponse);

        } catch (Exception ex) {
            log.error(Constants.ERROR_EXCEPTION_RESOURCE + "-findOne: {}", ex.getMessage());
            return ResponseEntity.ok().build();
        }
    }

    @Operation(
            summary = "Retrieve a CarSeller by Id",
            description = "Get a CarSeller object by specifying its id. The response is Association object with id, title, description and published status.",
            tags = {"productCategorys", "get"})
    @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = CarSellerResources.class), mediaType = "application/json")})
    @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())})
    @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})
    @GetMapping("/code/{code}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<CarSellerResponse> findByClientId(@PathVariable("code") String code) {
        try {
            CarSeller carSellerSaved = findByIdCarSellerPort.findByCode(code);
            if (carSellerSaved == null) {
                throw new ResourceFoundException("Produto não encontrado ao buscar por código");
            }

            CarSellerResponse carSellerPhysicalResponse = carSellerPhysicalApiMapper.fromEntity(carSellerSaved);
            return ResponseEntity.ok(carSellerPhysicalResponse);

        } catch (Exception ex) {
            log.error(Constants.ERROR_EXCEPTION_RESOURCE + "-findOne: {}", ex.getMessage());
            return ResponseEntity.ok().build();
        }
    }

    @Operation(summary = "Delete a CarSeller by Id", tags = {"productCategorytrus", "delete"})
    @ApiResponse(responseCode = "204", content = {@Content(schema = @Schema())})
    @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> remove(@PathVariable("id") Long id) {
        deleteCarSellerPort.remove(id);
        return ResponseEntity.noContent().build();
    }
}