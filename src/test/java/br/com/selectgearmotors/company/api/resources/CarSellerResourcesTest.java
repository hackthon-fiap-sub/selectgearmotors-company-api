package br.com.selectgearmotors.company.api.resources;

import br.com.selectgearmotors.company.application.api.mapper.CarSellerApiMapper;
import br.com.selectgearmotors.company.core.domain.CarSeller;
import br.com.selectgearmotors.company.core.service.CarSellerService;
import br.com.selectgearmotors.company.factory.ObjectFactory;
import br.com.selectgearmotors.company.infrastructure.entity.company.CompanyEntity;
import br.com.selectgearmotors.company.infrastructure.entity.carseller.CarSellerEntity;
import br.com.selectgearmotors.company.infrastructure.entity.companytype.CompanyTypeEntity;
import br.com.selectgearmotors.company.infrastructure.repository.CarSellerRepository;
import br.com.selectgearmotors.company.infrastructure.repository.CompanyRepository;
import br.com.selectgearmotors.company.infrastructure.repository.CompanyTypeRepository;
import br.com.selectgearmotors.company.util.JsonUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ImportAutoConfiguration(exclude = FlywayAutoConfiguration.class)
@TestPropertySource("classpath:application-test.properties")
class CarSellerResourcesTest {

    private static final Logger log = LoggerFactory.getLogger(CarSellerResourcesTest.class);

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private CarSellerService service;

    @Autowired
    private CompanyTypeRepository companyTypeRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private CarSellerRepository carSellerRepository;

    private CarSeller carSellerRequest;

    private Long companyTypeEntityId;

    private Long companyEntityId;

    private Long companyPhysicalEntityId;

    @Mock
    private CarSellerApiMapper companyPhysicalApiMapper;

    private Faker faker = new Faker();

    @BeforeEach
    void setup() {
        carSellerRepository.deleteAll();
        companyRepository.deleteAll();
        companyTypeRepository.deleteAll();

        CompanyTypeEntity companyTypeEntity = createCompanyTypeEntity();
        this.companyTypeEntityId = companyTypeEntity.getId();
        log.info("companyTypeEntityId: {}", companyTypeEntityId);

        CompanyEntity companyEntity = createCompanyEntity(companyTypeEntity);
        this.companyEntityId = companyEntity.getId();
        log.info("companyEntityId: {}", companyEntityId);

        this.carSellerRequest = getCarSellerRequest(companyEntity.getId());
        this.companyPhysicalEntityId = carSellerRequest.getCompanyId();
        log.info("companyPhysicalEntityId: {}", companyPhysicalEntityId);
    }

    @AfterEach
    void tearDown() {
        carSellerRepository.deleteAll();
        companyRepository.deleteAll();
        companyTypeRepository.deleteAll();
    }

    public CarSeller getCarSellerRequest(Long companyId) {
        CarSeller build = CarSeller.builder()
                .companyId(companyId)
                .socialId("967.382.310-32")
                .build();

        CarSeller saved = service.save(build);
        return saved;
    }

    public CarSellerEntity getCarSeller(CompanyEntity companyEntity) {
        CarSellerEntity carSellerEntity = ObjectFactory.getInstance().getCarSellerEntity(companyEntity);
        CarSellerEntity carSellerEntitySaved = carSellerRepository.save(carSellerEntity);

        verifyDataSavedCarSellerEntity(carSellerEntitySaved);
        return carSellerEntitySaved;
    }

    private void verifyDataSavedCarSellerEntity(CarSellerEntity carSellerEntity) {
        assertThat(carSellerRepository.findById(carSellerEntity.getId())).isPresent();
    }

    private CompanyEntity createCompanyEntity(CompanyTypeEntity companyTypeEntity) {
        CompanyEntity companyEntity = ObjectFactory.getInstance().getCompany(companyTypeEntity);
        CompanyEntity companyEntitySaved = companyRepository.save(companyEntity);

        verifyDataSavedCompanyEntity(companyEntitySaved);
        return companyEntitySaved;
    }

    private void verifyDataSavedCompanyEntity(CompanyEntity companyEntity) {
        assertThat(companyRepository.findById(companyEntity.getId()).isPresent());
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
        mockMvc.perform(get("/v1/company-physicals/{id}", this.companyPhysicalEntityId))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.socialId").value("96738231032"));
    }

    @Disabled
    void getAll() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/v1/company-physicals")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].id").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].socialId").exists());
    }

    @Disabled
    void getAll_isNull() throws Exception {
        carSellerRepository.deleteAll();

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .get("/v1/company-physicals")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        assertThat(responseContent).isEmpty();
    }

    @Disabled
    void create() throws Exception {
        String create = JsonUtil.getJson(this.carSellerRequest);

        assertThat(create).isNotNull().isNotEmpty();

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .post("/v1/company-physicals")
                        .content(create)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        System.out.println("Response Content: " + responseContent);
        assertThat(responseContent).isNotNull().isNotEmpty();
    }

    @Disabled
    void update() throws Exception {
        String update = JsonUtil.getJson(this.carSellerRequest);
        System.out.println("Generated JSON for Update: " + update);

        assertThat(update).isNotNull().isNotEmpty();

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .put("/v1/company-physicals/{id}", this.companyPhysicalEntityId)
                        .content(update)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String responseContentUpdate = result.getResponse().getContentAsString();
        log.info("Response update {}", responseContentUpdate);
        assertThat(responseContentUpdate).isNotNull().isNotEmpty();
    }

    @Disabled
    void delete() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .delete("/v1/company-physicals/{id}", this.companyPhysicalEntityId))
                .andExpect(status().isNoContent())
                .andReturn();

        String responseContentDelete = result.getResponse().getContentAsString();
        log.info("Response delete {}", responseContentDelete);
        assertThat(responseContentDelete).isEmpty();
    }
}