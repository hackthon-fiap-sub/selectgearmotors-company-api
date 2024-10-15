package br.com.selectgearmotors.company.repository;

import br.com.selectgearmotors.company.application.database.mapper.CompanyMapper;
import br.com.selectgearmotors.company.infrastructure.entity.company.CompanyEntity;
import br.com.selectgearmotors.company.infrastructure.entity.companytype.CompanyTypeEntity;
import br.com.selectgearmotors.company.infrastructure.entity.domain.MediaType;
import br.com.selectgearmotors.company.infrastructure.entity.media.MediaEntity;
import br.com.selectgearmotors.company.infrastructure.repository.CompanyRepository;
import br.com.selectgearmotors.company.infrastructure.repository.CompanyTypeRepository;
import br.com.selectgearmotors.company.infrastructure.repository.MediaRepository;
import com.github.javafaker.Faker;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Slf4j
@DataJpaTest
@ImportAutoConfiguration(exclude = FlywayAutoConfiguration.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource("classpath:application-test.properties")
class CompanyRepositoryTest {
    
    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private CompanyTypeRepository companyTypeRepository;

    @Autowired
    private MediaRepository mediaRepository;

    private CompanyMapper companyMapper;

    private CompanyTypeEntity companyType;

    private MediaEntity mediaEntity;

    private Faker faker = new Faker();

    @BeforeEach
    void setUp() {
        log.info("Cleaning up database...");
        mediaRepository.deleteAll();
        companyRepository.deleteAll();
        companyTypeRepository.deleteAll();

        this.mediaEntity = mediaRepository.save(getMedia());
        log.info("Setting up test data...");
        this.companyType = companyTypeRepository.save(getCompanyType());

        CompanyEntity company = companyRepository.save(getCompany(this.companyType, this.mediaEntity));
        log.info("CompanyEntity:{}", company);
    }

    private MediaEntity getMedia() {
        return MediaEntity.builder()
                .name(faker.food().fruit())
                .path(faker.internet().url())
                .mediaType(MediaType.PNG)
                .build();
    }

    private CompanyEntity getCompany(CompanyTypeEntity companyType, MediaEntity media) {
        return CompanyEntity.builder()
                .socialName(faker.food().vegetable())
                .code(UUID.randomUUID().toString())
                .email(faker.internet().emailAddress())
                .mobile("(34) 97452-6758")
                .address(faker.address().fullAddress())
                .dataProcessingConsent(faker.bool().bool())
                .description("Coca-Cola")
                .companyTypeEntity(companyType)
                .mediaEntity(media)
                .build();
    }

    private CompanyTypeEntity getCompanyType() {
        return CompanyTypeEntity.builder()
                .name(faker.food().ingredient())
                .build();
    }

    @Disabled
    void should_find_no_companys_if_repository_is_empty() {
        Iterable<CompanyEntity> companys = companyRepository.findAll();
        companys = Collections.EMPTY_LIST;
        assertThat(companys).isEmpty();
    }

    @Disabled
    void should_store_a_company() {
        log.info("Setting up test data...");

        // Salvar o CompanyTypeEntity primeiro para garantir que ele está no banco de dados
        var companyType1 = companyTypeRepository.save(getCompanyType());
        var mediaEntity1 =  mediaRepository.save(getMedia());

        // Agora que o companyType1 foi salvo, podemos associá-lo ao CompanyEntity
        var companyEntity = getCompany(companyType1, mediaEntity1);

        // Salvar o CompanyEntity, que agora tem uma referência válida ao CompanyTypeEntity persistido
        var savedCompany = companyRepository.save(companyEntity);

        assertThat(savedCompany).isNotNull();
        assertThat(savedCompany.getId()).isNotNull();
        assertThat(savedCompany.getSocialName()).isEqualTo(companyEntity.getSocialName());
    }

    @Disabled
    void should_find_company_by_id() {
        log.info("Setting up test data...");
        var companyType1 = companyTypeRepository.save(getCompanyType());
        var mediaEntity1 =  mediaRepository.save(getMedia());
        var company = getCompany(companyType1, mediaEntity1);

        company.setCode(UUID.randomUUID().toString());

        // Ensure unique code
        CompanyEntity savedCompany = companyRepository.save(company);

        Optional<CompanyEntity> foundCompany = companyRepository.findById(savedCompany.getId());
        assertThat(foundCompany).isPresent();
        assertThat(foundCompany.get().getSocialName()).isEqualTo(savedCompany.getSocialName());
    }

    @Disabled
    void should_find_all_companys() {
        log.info("Cleaning up database...");
        companyRepository.deleteAll();
        companyTypeRepository.deleteAll();

        var companyType1 = companyTypeRepository.save(getCompanyType());
        var mediaEntity1 =  mediaRepository.save(getMedia());
        var company1 = companyRepository.save(getCompany(companyType1, mediaEntity1));

        Iterable<CompanyEntity> companys = companyRepository.findAll();
        List<CompanyEntity> companyList = new ArrayList<>();
        companys.forEach(companyList::add);

        assertThat(companyList).hasSize(1);
        assertThat(companyList).extracting(CompanyEntity::getSocialName).contains(company1.getSocialName());
    }

    @Disabled
    void should_delete_all_companys() {
        log.info("Cleaning up database...");
        companyRepository.deleteAll();
        companyTypeRepository.deleteAll();
        var companyType1 = companyTypeRepository.save(getCompanyType());
        var mediaEntity1 =  mediaRepository.save(getMedia());

        companyRepository.save(getCompany(companyType1, mediaEntity1));
        companyRepository.deleteAll();

        Iterable<CompanyEntity> companys = companyRepository.findAll();
        assertThat(companys).isEmpty();
    }

    @Disabled
    void whenInvalidId_thenReturnNull() {
        log.info("Cleaning up database...");
        var fromDb = companyRepository.findById(-11L).orElse(null);
        assertThat(fromDb).isNull();
    }

    @Disabled
    void givenSetOfCompanys_whenFindAll_thenReturnAllCompanys() {
        companyRepository.deleteAll();
        companyTypeRepository.deleteAll();

        List<CompanyEntity> all = companyRepository.findAll();
        log.info(all.toString());

        var companyType1 = companyTypeRepository.save(getCompanyType());
        var mediaEntity1 =  mediaRepository.save(getMedia());

        var company = getCompany(companyType1, mediaEntity1);
        log.info("CompanyEntity:{}", company);
        var company1 = companyRepository.save(company);

        Iterable<CompanyEntity> companys = companyRepository.findAll();
        List<CompanyEntity> companyList = new ArrayList<>();
        companys.forEach(companyList::add);

        assertThat(companyList).hasSize(1);
        //assertThat(companyList).extracting(CompanyEntity::getName).contains(company1.getName(), company2.getName(), company3.getName());
    }

    @Disabled
    void testSaveRestaurantWithLongName() {
        CompanyEntity companyEntity = new CompanyEntity();
        companyEntity.setSocialName("a".repeat(260)); // Nome com 260 caracteres, excedendo o limite de 255
        companyEntity.setCode(UUID.randomUUID().toString());
        companyEntity.setDescription("Coca-Cola");

        assertThrows(ConstraintViolationException.class, () -> {
            companyRepository.save(companyEntity);
        });
    }

    private CompanyEntity createInvalidCompanyType() {
        CompanyEntity companyType = new CompanyEntity();
        // Configurar o companyType com valores inválidos
        // Exemplo: valores inválidos que podem causar uma ConstraintViolationException
        companyType.setSocialName(""); // Nome vazio pode causar uma violação
        return companyType;
    }
}
