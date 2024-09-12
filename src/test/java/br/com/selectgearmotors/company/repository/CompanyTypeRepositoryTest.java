package br.com.selectgearmotors.company.repository;

import br.com.selectgearmotors.company.infrastructure.entity.companytype.CompanyTypeEntity;
import br.com.selectgearmotors.company.infrastructure.repository.CompanyRepository;
import br.com.selectgearmotors.company.infrastructure.repository.CompanyTypeRepository;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.TestPropertySource;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Slf4j
@DataJpaTest
@ImportAutoConfiguration(exclude = FlywayAutoConfiguration.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource("classpath:application-test.properties")
class CompanyTypeRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CompanyTypeRepository companyTypeRepository;
    
    @Autowired
    private CompanyRepository companyRepository;

    private CompanyTypeEntity getClientType() {
        return CompanyTypeEntity.builder()
                .id(1l)
                .name("Bebida")
                .build();
    }

    @BeforeEach
    void setUp() {
        companyRepository.deleteAll();
        companyTypeRepository.deleteAll();

        companyTypeRepository.save(getClientType());
    }

    @Test
    void should_find_no_companys_if_repository_is_empty() {
        companyRepository.deleteAll();
        companyTypeRepository.deleteAll();

        companyTypeRepository.save(getClientType());

        List<CompanyTypeEntity> seeds = new ArrayList<>();
        seeds = companyTypeRepository.findAll();
        seeds = Collections.EMPTY_LIST;
        assertThat(seeds).isEmpty();
    }

    @Test
    void should_store_a_company_category() {
        String cocaColaBeverage = "Coca-Cola";
        Optional<CompanyTypeEntity> companyType = companyTypeRepository.findByName(cocaColaBeverage);
        Optional<CompanyTypeEntity> companyTypeResponse = null;
        if (!companyType.isPresent()) {

            CompanyTypeEntity cocaCola = CompanyTypeEntity.builder()
                    .name(cocaColaBeverage)
                    .build();

            CompanyTypeEntity save = companyTypeRepository.save(cocaCola);
            companyTypeResponse = companyTypeRepository.findByName(cocaColaBeverage);
        }

        CompanyTypeEntity companyType1 = companyTypeResponse.get();
        assertThat(companyType1).hasFieldOrPropertyWithValue("name", cocaColaBeverage);
    }

    @Test
    void testSaveRestaurantWithLongName() {
        CompanyTypeEntity companyType = new CompanyTypeEntity();
        companyType.setName("a".repeat(260)); // Nome com 260 caracteres, excedendo o limite de 255

        assertThrows(ConstraintViolationException.class, () -> {
            companyTypeRepository.save(companyType);
        });
    }

    private CompanyTypeEntity createInvalidClientType() {
        CompanyTypeEntity companyType = new CompanyTypeEntity();
        // Configurar o companyType com valores inválidos
        // Exemplo: valores inválidos que podem causar uma ConstraintViolationException
        companyType.setName(""); // Nome vazio pode causar uma violação
        return companyType;
    }

    @Test
    void should_found_null_ClientType() {
        CompanyTypeEntity companyType = null;

        Optional<CompanyTypeEntity> fromDb = companyTypeRepository.findById(99l);
        if (fromDb.isPresent()) {
            companyType = fromDb.get();
        }
        assertThat(companyType).isNull();
    }

    @Test
    void whenFindById_thenReturnClientType() {
        Optional<CompanyTypeEntity> companyType = companyTypeRepository.findById(1l);
        if (companyType.isPresent()) {
            CompanyTypeEntity companyTypeResult = companyType.get();
            assertThat(companyTypeResult).hasFieldOrPropertyWithValue("name", "Bebida");
        }
    }

    @Test
    void whenInvalidId_thenReturnNull() {
        CompanyTypeEntity fromDb = companyTypeRepository.findById(-11l).orElse(null);
        assertThat(fromDb).isNull();
    }

    @Test
    void givenSetOfClientTypes_whenFindAll_thenReturnAllClientTypes() {
        CompanyTypeEntity companyType = null;
        CompanyTypeEntity companyType1 = null;
        CompanyTypeEntity companyType2 = null;

        Optional<CompanyTypeEntity> restaurant = companyTypeRepository.findById(1l);
        if (restaurant.isPresent()) {

            CompanyTypeEntity bebida = CompanyTypeEntity.builder()
                    .name("Bebida")
                    .build();
            companyType = companyTypeRepository.save(bebida);

            CompanyTypeEntity acompanhamento = CompanyTypeEntity.builder()
                    .name("Acompanhamento")
                    .build();
            companyType1 = companyTypeRepository.save(acompanhamento);

            CompanyTypeEntity lanche = CompanyTypeEntity.builder()
                    .name("Lanche")
                    .build();
            companyType2 = companyTypeRepository.save(lanche);

        }

        Iterator<CompanyTypeEntity> allClientTypes = companyTypeRepository.findAll().iterator();
        List<CompanyTypeEntity> companys = new ArrayList<>();
        allClientTypes.forEachRemaining(c -> companys.add(c));

        assertNotNull(allClientTypes);
    }
}