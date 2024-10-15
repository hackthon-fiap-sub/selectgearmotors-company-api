package br.com.selectgearmotors.company.api.resources;

import br.com.selectgearmotors.company.application.api.dto.request.CompanyTypeRequest;
import br.com.selectgearmotors.company.application.api.mapper.CompanyTypeApiMapper;
import br.com.selectgearmotors.company.core.domain.CompanyType;
import br.com.selectgearmotors.company.core.service.CompanyTypeService;
import br.com.selectgearmotors.company.infrastructure.repository.CompanyTypeRepository;
import br.com.selectgearmotors.company.util.JsonUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ImportAutoConfiguration(exclude = FlywayAutoConfiguration.class)
@TestPropertySource("classpath:application-test.properties")
class CompanyTypeResourcesTest {

    private static final Logger log = LoggerFactory.getLogger(CompanyTypeResourcesTest.class);

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private CompanyTypeService service;

    @Autowired
    private CompanyTypeRepository repository;

    private CompanyType companyType;

    private Long companyTypeId;

    @Mock
    private CompanyTypeApiMapper companyTypeApiMapper;

    private CompanyType getCompanyType() {
        return CompanyType.builder()
                .name("Pragmatico")
                .build();
    }

    private CompanyType getCompanyTypeUpdate() {
        return CompanyType.builder()
                .id(1L)
                .name("Pragmatico")
                .build();
    }

    @BeforeEach
    void setup() {
        repository.deleteAll();

        CompanyType companyTypeSaved = service.save(getCompanyType());
        this.companyType = companyTypeSaved;
        this.companyTypeId = companyTypeSaved.getId();
    }

    @Disabled
    void findsTaskById() throws Exception {
        mockMvc.perform(get("/v1/company-types/{id}", this.companyTypeId))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Pragmatico"));
    }

    @Disabled
    void getAll() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/v1/company-types")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].id").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].name").exists());
    }

    @Disabled
    void getAll_isNull() throws Exception {
        repository.deleteAll();

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .get("/v1/company-types")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        assertThat(responseContent).isEmpty();
    }

    @Disabled
    void create() throws Exception {
        String create = JsonUtil.getJson(this.companyType);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/v1/company-types")
                        .content(create)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty());
    }

    @Disabled
    void create_isNull() throws Exception {
        String create = JsonUtil.getJson(new CompanyType());
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .post("/v1/company-types")
                        .content(create)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        assertThat(responseContent).isEmpty();
    }

    @Disabled
    void testSave_Exception() throws Exception {
        CompanyTypeRequest companyTypeRequest = new CompanyTypeRequest();
        String create = JsonUtil.getJson(companyTypeRequest);

        when(companyTypeApiMapper.fromRequest(companyTypeRequest)).thenThrow(new RuntimeException("Company não encontroado ao cadastrar"));

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .post("/v1/company-types")
                        .content(create)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        assertThat(responseContent).isEmpty();
    }

    @Disabled
    void update() throws Exception {
        String update = JsonUtil.getJson(this.companyType);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/v1/company-types/{id}", this.companyTypeId)
                        .content(update)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Pragmatico"));
    }

    @Disabled
    void update_isNull() throws Exception {
        String update = JsonUtil.getJson(new CompanyTypeRequest());

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .put("/v1/company-types/{id}", this.companyTypeId)
                        .content(update)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        assertThat(responseContent).isEmpty();
    }

    @Disabled
    void testUpdate_Exception() throws Exception {
        CompanyTypeRequest product = new CompanyTypeRequest();
        String create = JsonUtil.getJson(product);

        when(companyTypeApiMapper.fromRequest(product)).thenThrow(new RuntimeException("Produto não encontroado ao atualizar"));

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .put("/v1/company-types/{id}", this.companyTypeId)
                        .content(create)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        assertThat(responseContent).isEmpty();
    }

    @Disabled
    void delete() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/v1/company-types/{id}", 1))
                .andExpect(status().isNoContent());
    }

    @Disabled
    void findByCode_productIsNull() throws Exception {
        MvcResult result = mockMvc.perform(get("/v1/company-types/{id}", 99l))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        assertThat(responseContent).isEmpty();
    }

    @Disabled
    void testById_Exception() throws Exception {
        CompanyTypeRequest companyTypeRequest = new CompanyTypeRequest();
        when(companyTypeApiMapper.fromRequest(companyTypeRequest)).thenThrow(new RuntimeException("Produto não encontrado ao buscar por código"));

        MvcResult result = mockMvc.perform(get("/v1/company-types/{id}", 99L))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        assertThat(responseContent).isEmpty();
    }
}