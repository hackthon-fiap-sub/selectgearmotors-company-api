package br.com.selectgearmotors.company.repository;

import br.com.selectgearmotors.company.infrastructure.entity.carseller.CarSellerEntity;
import br.com.selectgearmotors.company.infrastructure.entity.company.CompanyEntity;
import br.com.selectgearmotors.company.infrastructure.entity.companytype.CompanyTypeEntity;
import br.com.selectgearmotors.company.infrastructure.repository.CarSellerRepository;
import br.com.selectgearmotors.company.infrastructure.repository.CompanyRepository;
import br.com.selectgearmotors.company.infrastructure.repository.CompanyTypeRepository;
import com.github.javafaker.Faker;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.TestPropertySource;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@DataJpaTest
@ImportAutoConfiguration(exclude = FlywayAutoConfiguration.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource("classpath:application-test.properties")
class CarSellerRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CompanyTypeRepository companyTypeRepository;

    @Autowired
    private CarSellerRepository carSellerRepository;

    @Autowired
    private CompanyRepository companyRepository;

    private CompanyEntity companyEntity;

    private CarSellerEntity carSellerEntity;

    private Faker faker = new Faker();

    private CompanyEntity getCompany(CompanyTypeEntity companyTypeEntity) {
        return CompanyEntity.builder()
                .socialName(faker.food().vegetable())
                .code(UUID.randomUUID().toString())
                .email(faker.internet().emailAddress())
                .mobile("(34) 97452-6758")
                .address(faker.address().fullAddress())
                .dataProcessingConsent(faker.bool().bool())
                .description("Coca-Cola")
                .companyTypeEntity(companyTypeEntity)
                .build();
    }

    private CompanyTypeEntity getCompanyType() {
        return CompanyTypeEntity.builder()
                .name(faker.food().ingredient())
                .build();
    }

    private CarSellerEntity getCarSeller(CompanyEntity companyEntity) {
        return CarSellerEntity.builder()
                .id(1l)
                .socialId("35511965081")
                .companyEntity(companyEntity)
                .build();
    }

    private CarSellerEntity getCarSellerCompanySize(CompanyEntity companyEntity) {
        return CarSellerEntity.builder()
                .id(1l)
                .socialId("35511965081")
                .companyEntity(companyEntity)
                .build();
    }

    @BeforeEach
    void setUp() {
        carSellerRepository.deleteAll();
        companyRepository.deleteAll();
        companyTypeRepository.deleteAll();

        CompanyTypeEntity companyTypeEntity = companyTypeRepository.save(getCompanyType());
        this.companyEntity = companyRepository.save(getCompany(companyTypeEntity));
    }

    @Disabled
    void should_store_a_company() {
        log.info("Setting up test data...");

        CarSellerEntity carSeller = getCarSeller(this.companyEntity);
        var carSellerEntitySaved = carSellerRepository.save(carSeller);

        assertThat(carSellerEntitySaved).isNotNull();
        assertThat(carSellerEntitySaved.getId()).isNotNull();
        assertThat(carSellerEntitySaved.getSocialId()).isEqualTo(carSeller.getSocialId());
    }

    @Disabled
    void should_find_company_by_id() {
        log.info("Setting up test data...");
        CarSellerEntity carSeller = getCarSeller(this.companyEntity);
        var carSellerEntitySaved = carSellerRepository.save(carSeller);

        Optional<CarSellerEntity> foundCompany = carSellerRepository.findById(carSellerEntitySaved.getId());
        assertThat(foundCompany).isPresent();
        assertThat(foundCompany.get().getSocialId()).isEqualTo(foundCompany.get().getSocialId());
    }

    @Disabled
    void should_find_all_companys() {
        log.info("Cleaning up database...");

        CarSellerEntity carSeller = getCarSeller(this.companyEntity);
        var carSellerEntitySaved = carSellerRepository.save(carSeller);

        Iterable<CarSellerEntity> companys = carSellerRepository.findAll();
        List<CarSellerEntity> companyList = new ArrayList<>();
        companys.forEach(companyList::add);

        assertThat(companyList).hasSize(1);
        assertThat(companyList).extracting(CarSellerEntity::getSocialId).contains(carSellerEntitySaved.getSocialId());
    }

    @Disabled
    void should_delete_all_companys() {
        log.info("Cleaning up database...");
        Iterable<CarSellerEntity> companys = carSellerRepository.findAll();
        assertThat(companys).isEmpty();
    }

    @Disabled
    void whenInvalidId_thenReturnNull() {
        log.info("Cleaning up database...");
        CarSellerEntity fromDb = carSellerRepository.findById(-11L).orElse(null);
        assertThat(fromDb).isNull();
    }

    @Disabled
    void givenSetOfCompanys_whenFindAll_thenReturnAllCompanys() {
        CarSellerEntity carSeller = getCarSeller(this.companyEntity);
        carSellerRepository.save(carSeller);

        Iterable<CarSellerEntity> companys = carSellerRepository.findAll();
        List<CarSellerEntity> companyList = new ArrayList<>();
        companys.forEach(companyList::add);

        assertThat(companyList).hasSize(1);
    }

    @Disabled
    void testSaveRestaurantWithLongName() {
        CarSellerEntity carSeller = getCarSeller(this.companyEntity);
        carSellerRepository.save(carSeller);
    }
}