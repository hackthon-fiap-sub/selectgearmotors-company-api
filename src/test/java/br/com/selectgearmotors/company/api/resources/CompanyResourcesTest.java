package br.com.selectgearmotors.company.api.resources;

import br.com.selectgearmotors.company.application.api.dto.request.CompanyRequest;
import br.com.selectgearmotors.company.application.api.mapper.CompanyApiMapper;
import br.com.selectgearmotors.company.core.domain.Company;
import br.com.selectgearmotors.company.core.service.CompanyService;
import br.com.selectgearmotors.company.core.service.CompanyTypeService;
import br.com.selectgearmotors.company.factory.ObjectFactory;
import br.com.selectgearmotors.company.infrastructure.entity.companytype.CompanyTypeEntity;
import br.com.selectgearmotors.company.infrastructure.entity.media.MediaEntity;
import br.com.selectgearmotors.company.infrastructure.repository.CompanyRepository;
import br.com.selectgearmotors.company.infrastructure.repository.CompanyTypeRepository;
import br.com.selectgearmotors.company.infrastructure.repository.MediaRepository;
import br.com.selectgearmotors.company.util.JsonUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
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

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ImportAutoConfiguration(exclude = FlywayAutoConfiguration.class)
@TestPropertySource("classpath:application-test.properties")
class CompanyResourcesTest {

    private static final Logger log = LoggerFactory.getLogger(CompanyResourcesTest.class);

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private CompanyService service;

    @Autowired
    private CompanyRepository repository;

    @Autowired
    private CompanyTypeService companyTypeService;

    @Autowired
    private CompanyTypeRepository companyTypeRepository;

    @Autowired
    private MediaRepository mediaRepository;

    private CompanyTypeEntity companyTypeEntity;

    private Long companyTypeEntityId;

    @Mock
    private CompanyApiMapper companyApiMapper;

    private Faker faker = new Faker();
    private Long companyCategoryId;
    private Long mediaId;
    private Long companyId;
    private CompanyRequest companyRequest;
    private String companyCode;

    @BeforeEach
    void setup() {
        repository.deleteAll();
        companyTypeRepository.deleteAll();

        this.companyTypeEntity = createCompanyTypeEntity();
        this.companyTypeEntityId = companyTypeEntity.getId();
        log.info("companyTypeEntityId: {}", companyTypeEntityId);


        MediaEntity mediaEntity = mediaRepository.save(getMedia());
        this.mediaId = mediaEntity.getId();
        log.info("mediaId: {}", mediaId);
    }

    public MediaEntity getMedia() {
        return MediaEntity.builder()
                .name(faker.food().ingredient())
                .mediaType(br.com.selectgearmotors.company.infrastructure.entity.domain.MediaType.JPG)
                .build();
    }

    private CompanyRequest createCompanyRequest(Long companyTypeEntityId, Long mediaId) {
        return CompanyRequest.builder()
                .companyTypeId(companyTypeEntityId)
                .socialName(faker.company().name())
                .email(faker.internet().emailAddress())
                .mobile("(34) 97452-6758")
                .description(faker.lorem().sentence())
                .address(faker.address().fullAddress())
                .dataProcessingConsent(faker.bool().bool())
                .mediaId(mediaId)
                .build();
    }

    private Company getCompany(Long companyTypeId, Long mediaId) {
        return Company.builder()
                .code(UUID.randomUUID().toString())
                .email(faker.internet().emailAddress())
                .mobile("(34) 97452-6758")
                .address(faker.address().fullAddress())
                .dataProcessingConsent(faker.bool().bool())
                .description("Coca-Cola")
                .companyTypeId(companyTypeId)
                .mediaId(mediaId)
                .build();
    }

    private CompanyTypeEntity createCompanyTypeEntity() {
        CompanyTypeEntity companyTypeEntity = ObjectFactory.getInstance().getCompanyType();
        CompanyTypeEntity companyTypeEntitySaved = companyTypeRepository.save(companyTypeEntity);

        verifyDataSavedCompanyTypeEntity(companyTypeEntitySaved);
        return companyTypeEntitySaved;
    }

    private void verifyDataSavedCompanyTypeEntity(CompanyTypeEntity companyTypeEntitySaved) {
        assertThat(companyTypeRepository.findById(companyTypeEntitySaved.getId())).isPresent();
    }

    @Disabled
    void findsTaskById() throws Exception {
        repository.deleteAll();
        Company company = getCompany(this.companyTypeEntityId, this.mediaId);
        Company saved = service.save(company);
        log.info("Update: {}", saved);

        MvcResult result = mockMvc.perform(get("/v1/companys/{id}", saved.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        System.out.println("Response Content: " + responseContent);
        assertThat(responseContent).isNotEmpty();
    }

    @Disabled
    void getAll() throws Exception {
        repository.deleteAll();
        Company company = getCompany(this.companyTypeEntityId, this.mediaId);
        service.save(company);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .get("/v1/companys")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        System.out.println("Response Content: " + responseContent);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/v1/companys")
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
                        .get("/v1/companys")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        assertThat(responseContent).isEmpty();
    }

    @Disabled
    void create() throws Exception {
        CompanyRequest companyRequest1 = createCompanyRequest(this.companyTypeEntityId, this.mediaId);
        this.companyRequest = companyRequest1;
        log.info("companyRequest: {}", companyRequest);

        String create = JsonUtil.getJson(this.companyRequest);

        assertThat(create).isNotNull().isNotEmpty();  // Verifique se o JSON não é nulo ou vazio

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .post("/v1/companys")
                        .content(create)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        System.out.println("Response Content: " + responseContent);
        assertThat(responseContent).isNotEmpty();
    }

    @Disabled
    void update() throws Exception {
        repository.deleteAll();
        Company company = getCompany(this.companyTypeEntityId, this.mediaId);
        Company saved = service.save(company);

        String update = JsonUtil.getJson(saved);
        System.out.println("Generated JSON for Update: " + update);

        assertThat(update).isNotNull().isNotEmpty();  // Verifique se o JSON não é nulo ou vazio

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .put("/v1/companys/{id}", saved.getId())
                        .content(update)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        System.out.println("Response Content: " + responseContent);
        assertThat(responseContent).isNotEmpty();
    }

    @Disabled
    void delete() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete("/v1/companys/{id}", this.companyTypeEntityId))
                .andExpect(status().isNoContent())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        System.out.println("Response Content: " + responseContent);
        assertThat(responseContent).isEmpty();
    }

    @Disabled
    void findByCode_companyFound() throws Exception {
        repository.deleteAll();
        Company company = getCompany(this.companyTypeEntityId, this.mediaId);
        Company saved = service.save(company);

        MvcResult result = mockMvc.perform(get("/v1/companys/code/{code}", saved.getCode()))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        System.out.println("Response Content: " + responseContent);

        mockMvc.perform(get("/v1/companys/code/{code}", saved.getCode()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").exists());
    }

    @Disabled
    void testByCode_Exception() throws Exception {
        // Simulando a exceção que será lançada
        String errorMessage = "Produto nao encontrado ao buscar por codigo";
        when(companyApiMapper.fromRequest(any(CompanyRequest.class))).thenThrow(new RuntimeException(errorMessage));

        // Executa a requisição e espera um status 404
        MvcResult result = mockMvc.perform(get("/v1/companys/code/{code}", 99L))
                .andDo(print())
                .andExpect(status().isNotFound()) // Verifica se o status HTTP é 404
                .andReturn();

        // Verifica o corpo da resposta
        String responseContent = result.getResponse().getContentAsString();

        // Usa uma biblioteca de assertivas para garantir que o conteúdo da resposta contém as informações esperadas
        assertThat(responseContent).contains("\"statusCode\":404");
        assertThat(responseContent).contains("\"message\":\"" + errorMessage + "\"");
    }
}