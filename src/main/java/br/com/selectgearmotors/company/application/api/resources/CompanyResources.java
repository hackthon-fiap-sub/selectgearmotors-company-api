package br.com.selectgearmotors.company.application.api.resources;

import br.com.selectgearmotors.company.application.api.dto.request.CompanyRequest;
import br.com.selectgearmotors.company.application.api.dto.response.CompanyResponse;
import br.com.selectgearmotors.company.application.api.exception.ResourceFoundException;
import br.com.selectgearmotors.company.application.api.mapper.CompanyApiMapper;
import br.com.selectgearmotors.company.commons.util.RestUtils;
import br.com.selectgearmotors.company.core.domain.Company;
import br.com.selectgearmotors.company.core.ports.in.company.*;
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
@RequestMapping("/v1/companies")
@CrossOrigin(origins = "*", allowedHeaders = "Content-Type, Authorization", maxAge = 3600)
public class CompanyResources {

    private final CreateCompanyPort createCompanyPort;
    private final DeleteCompanyPort deleteCompanyPort;
    private final FindByIdCompanyPort findByIdCompanyPort;
    private final FindCompanysPort findCompanysPort;
    private final UpdateCompanyPort updateCompanyPort;
    private final CompanyApiMapper companyApiMapper;

    @Autowired
    public CompanyResources(CreateCompanyPort createCompanyPort, DeleteCompanyPort deleteCompanyPort, FindByIdCompanyPort findByIdCompanyPort, FindCompanysPort findCompanysPort, UpdateCompanyPort updateCompanyPort, CompanyApiMapper companyApiMapper) {
        this.createCompanyPort = createCompanyPort;
        this.deleteCompanyPort = deleteCompanyPort;
        this.findByIdCompanyPort = findByIdCompanyPort;
        this.findCompanysPort = findCompanysPort;
        this.updateCompanyPort = updateCompanyPort;
        this.companyApiMapper = companyApiMapper;
    }

    @Operation(summary = "Create a new Company", tags = {"companys", "post"})
    @ApiResponse(responseCode = "201", content = {
            @Content(schema = @Schema(implementation = CompanyResources.class), mediaType = "application/json")})
    @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<CompanyResponse> save(@Valid @RequestBody CompanyRequest request) {
        log.info("Chegada do objeto para ser salvo {}", request);
        Company company = companyApiMapper.fromRequest(request);
        Company saved = createCompanyPort.save(company);
        if (saved == null) {
            throw new ResourceFoundException("Produto não encontroado ao cadastrar");
        }

        CompanyResponse companyResponse = companyApiMapper.fromEntity(saved);
        URI location = RestUtils.getUri(companyResponse.getId());

        return ResponseEntity.created(location).body(companyResponse);
    }

    @Operation(summary = "Update a Company by Id", tags = {"companys", "put"})
    @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = CompanyResources.class), mediaType = "application/json")})
    @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})
    @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())})
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<CompanyResponse> update(@PathVariable("id") Long id, @Valid @RequestBody CompanyRequest request) {
        log.info("Chegada do objeto para ser alterado {}", request);
        var company = companyApiMapper.fromRequest(request);
        Company updated = updateCompanyPort.update(id, company);
        if (updated == null) {
            throw new ResourceFoundException("Produto não encontroado ao atualizar");
        }

        CompanyResponse companyResponse = companyApiMapper.fromEntity(updated);
        return ResponseEntity.ok(companyResponse);
    }

    @Operation(summary = "Retrieve all Company", tags = {"companys", "get", "filter"})
    @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = CompanyResources.class), mediaType = "application/json")})
    @ApiResponse(responseCode = "204", description = "There are no Associations", content = {
            @Content(schema = @Schema())})
    @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<CompanyResponse>> findAll() {
        List<Company> companyList = findCompanysPort.findAll();
        List<CompanyResponse> companyResponse = companyApiMapper.map(companyList);
        return companyResponse.isEmpty() ?
                ResponseEntity.noContent().build() :
                ResponseEntity.ok(companyResponse);
    }

    @Operation(
            summary = "Retrieve a Company by Id",
            description = "Get a Company object by specifying its id. The response is Association object with id, title, description and published status.",
            tags = {"companys", "get"})
    @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = CompanyResources.class), mediaType = "application/json")})
    @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())})
    @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<CompanyResponse> findOne(@PathVariable("id") Long id) {
        Company companySaved = findByIdCompanyPort.findById(id);
        if (companySaved == null) {
            throw new ResourceFoundException("Produto não encontrado ao buscar por id");
        }

        CompanyResponse companyResponse = companyApiMapper.fromEntity(companySaved);
        return ResponseEntity.ok(companyResponse);
    }

    @Operation(
            summary = "Retrieve a Company by Id",
            description = "Get a Company object by specifying its id. The response is Association object with id, title, description and published status.",
            tags = {"companys", "get"})
    @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = CompanyResources.class), mediaType = "application/json")})
    @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())})
    @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})
    @GetMapping("/code/{code}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<CompanyResponse> findByCode(@PathVariable("code") String code) {
        Company companySaved = findByIdCompanyPort.findByCode(code);
        if (companySaved == null) {
            throw new ResourceFoundException("Produto nao encontrado ao buscar por codigo");
        }

        CompanyResponse companyResponse = companyApiMapper.fromEntity(companySaved);
        return ResponseEntity.ok(companyResponse);
    }

    @Operation(summary = "Delete a Company by Id", tags = {"companytrus", "delete"})
    @ApiResponse(responseCode = "204", content = {@Content(schema = @Schema())})
    @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> remove(@PathVariable("id") Long id) {
        deleteCompanyPort.remove(id);
        return ResponseEntity.noContent().build();
    }
}