package br.com.selectgearmotors.company.application.api.resources;

import br.com.selectgearmotors.company.application.api.dto.request.CompanyTypeRequest;
import br.com.selectgearmotors.company.application.api.dto.response.CompanyTypeResponse;
import br.com.selectgearmotors.company.application.api.exception.ResourceFoundException;
import br.com.selectgearmotors.company.application.api.exception.ResourceNotFoundException;
import br.com.selectgearmotors.company.application.api.mapper.CompanyTypeApiMapper;
import br.com.selectgearmotors.company.commons.Constants;
import br.com.selectgearmotors.company.commons.util.RestUtils;
import br.com.selectgearmotors.company.core.domain.CompanyType;
import br.com.selectgearmotors.company.core.ports.in.companytype.*;
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
@RequestMapping("/v1/company-types")
@CrossOrigin(origins = "*", allowedHeaders = "Content-Type, Authorization", maxAge = 3600)
public class CompanyTypeResources {

    private final CreateCompanyTypePort createCompanyTypePort;
    private final DeleteCompanyTypePort deleteCompanyTypePort;
    private final FindByIdCompanyTypePort findByIdCompanyTypePort;
    private final FindCompanyTypesPort findProductCategoriesPort;
    private final UpdateCompanyTypePort updateCompanyTypePort;
    private final CompanyTypeApiMapper companyTypeApiMapper;

    @Autowired
    public CompanyTypeResources(CreateCompanyTypePort createCompanyTypePort, DeleteCompanyTypePort deleteCompanyTypePort, FindByIdCompanyTypePort findByIdCompanyTypePort, FindCompanyTypesPort findProductCategoriesPort, UpdateCompanyTypePort updateCompanyTypePort, CompanyTypeApiMapper companyTypeApiMapper) {
        this.createCompanyTypePort = createCompanyTypePort;
        this.deleteCompanyTypePort = deleteCompanyTypePort;
        this.findByIdCompanyTypePort = findByIdCompanyTypePort;
        this.findProductCategoriesPort = findProductCategoriesPort;
        this.updateCompanyTypePort = updateCompanyTypePort;
        this.companyTypeApiMapper = companyTypeApiMapper;
    }

    @Operation(summary = "Create a new CompanyType", tags = {"productCategorys", "post"})
    @ApiResponse(responseCode = "201", content = {
            @Content(schema = @Schema(implementation = CompanyTypeResources.class), mediaType = "application/json")})
    @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<CompanyTypeResponse> save(@Valid @RequestBody CompanyTypeRequest request) {
        try {
            log.info("Chegada do objeto para ser salvo {}", request);
            CompanyType companyType = companyTypeApiMapper.fromRequest(request);
            CompanyType saved = createCompanyTypePort.save(companyType);
            if (saved == null) {
                throw new ResourceNotFoundException("Produto não encontroado ao cadastrar");
            }

            CompanyTypeResponse companyTypeResponse = companyTypeApiMapper.fromEntity(saved);
            URI location = RestUtils.getUri(companyTypeResponse.getId());
            return ResponseEntity.created(location).body(companyTypeResponse);
        } catch (Exception ex) {
            log.error(Constants.ERROR_EXCEPTION_RESOURCE + "-save: {}", ex.getMessage());
            return ResponseEntity.ok().build();
        }
    }

    @Operation(summary = "Update a CompanyType by Id", tags = {"productCategorys", "put"})
    @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = CompanyTypeResources.class), mediaType = "application/json")})
    @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})
    @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())})
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<CompanyTypeResponse> update(@PathVariable("id") Long id, @Valid @RequestBody CompanyTypeRequest request) {
        try {
            log.info("Chegada do objeto para ser alterado {}", request);
            var productCategory = companyTypeApiMapper.fromRequest(request);
            CompanyType updated = updateCompanyTypePort.update(id, productCategory);
            if (updated == null) {
                throw new ResourceFoundException("\"Produto não encontroado ao atualizar");
            }

            CompanyTypeResponse companyTypeResponse = companyTypeApiMapper.fromEntity(updated);
            return ResponseEntity.ok(companyTypeResponse);
        } catch (Exception ex) {
            log.error(Constants.ERROR_EXCEPTION_RESOURCE + "-update: {}", ex.getMessage());
            return ResponseEntity.ok().build();
        }
    }

    @Operation(summary = "Retrieve all CompanyType", tags = {"productCategorys", "get", "filter"})
    @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = CompanyTypeResources.class), mediaType = "application/json")})
    @ApiResponse(responseCode = "204", description = "There are no Associations", content = {
            @Content(schema = @Schema())})
    @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<CompanyTypeResponse>> findAll() {
        List<CompanyType> companyTypeList = findProductCategoriesPort.findAll();
        List<CompanyTypeResponse> companyTypeResponse = companyTypeApiMapper.map(companyTypeList);
        return companyTypeResponse.isEmpty() ?
                ResponseEntity.noContent().build() :
                ResponseEntity.ok(companyTypeResponse);
    }

    @Operation(
            summary = "Retrieve a CompanyType by Id",
            description = "Get a CompanyType object by specifying its id. The response is Association object with id, title, description and published status.",
            tags = {"productCategorys", "get"})
    @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = CompanyTypeResources.class), mediaType = "application/json")})
    @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())})
    @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<CompanyTypeResponse> findOne(@PathVariable("id") Long id) {
        try {
            CompanyType companyTypeSaved = findByIdCompanyTypePort.findById(id);
            if (companyTypeSaved == null) {
                throw new ResourceFoundException("Produto não encontrado ao buscar por código");
            }

            CompanyTypeResponse companyTypeResponse = companyTypeApiMapper.fromEntity(companyTypeSaved);
            return ResponseEntity.ok(companyTypeResponse);

        } catch (Exception ex) {
            log.error(Constants.ERROR_EXCEPTION_RESOURCE + "-findOne: {}", ex.getMessage());
            return ResponseEntity.ok().build();
        }
    }

    @Operation(summary = "Delete a CompanyType by Id", tags = {"productCategorytrus", "delete"})
    @ApiResponse(responseCode = "204", content = {@Content(schema = @Schema())})
    @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> remove(@PathVariable("id") Long id) {
        deleteCompanyTypePort.remove(id);
        return ResponseEntity.noContent().build();
    }
}