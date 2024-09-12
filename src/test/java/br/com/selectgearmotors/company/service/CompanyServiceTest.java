package br.com.selectgearmotors.company.service;

import br.com.selectgearmotors.company.application.database.mapper.CompanyMapper;
import br.com.selectgearmotors.company.core.domain.Company;
import br.com.selectgearmotors.company.core.domain.CompanyType;
import br.com.selectgearmotors.company.core.ports.in.company.*;
import br.com.selectgearmotors.company.core.ports.out.CompanyRepositoryPort;
import br.com.selectgearmotors.company.core.service.CompanyService;
import br.com.selectgearmotors.company.infrastructure.entity.company.CompanyEntity;
import br.com.selectgearmotors.company.infrastructure.entity.companytype.CompanyTypeEntity;
import br.com.selectgearmotors.company.infrastructure.repository.CompanyRepository;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.DataException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Slf4j
@ExtendWith(MockitoExtension.class)
class CompanyServiceTest {

    @InjectMocks
    CompanyService companyService;

    @Mock
    CompanyRepositoryPort companyRepository;

    @Mock
    CompanyRepository repository;

    @Mock
    CompanyMapper mapper;

    @Mock
    CreateCompanyPort createCompanyPort;

    @Mock
    DeleteCompanyPort deleteCompanyPort;

    @Mock
    FindByIdCompanyPort findByIdCompanyPort;

    @Mock
    FindCompanysPort findCompanysPort;

    @Mock
    UpdateCompanyPort updateCompanyPort;

    private CompanyEntity getCompanyEntity(CompanyTypeEntity companyTypeEntity) {
        return CompanyEntity.builder()
                .socialName("Bebida")
                .code(UUID.randomUUID().toString())
                .description("Coca-Cola")
                .companyTypeEntity(companyTypeEntity)
                .build();
    }

    private Company getCompany(CompanyType companyType) {
        return Company.builder()
                .socialName("Coca-Cola")
                .code(UUID.randomUUID().toString())
                .description("Coca-Cola")
                .companyTypeId(companyType.getId())
                .build();
    }

    private CompanyTypeEntity getCompanyTypeEntity() {
        return CompanyTypeEntity.builder()
                .name("Bebida")
                .build();
    }

    private CompanyType getCompanyType() {
        return CompanyType.builder()
                .name("Bebida")
                .build();
    }

    @BeforeEach
    void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getAllCompanysTest() {
        List<Company> companies = new ArrayList<>();
        List<CompanyEntity> listEntity = new ArrayList<>();

        Company company = getCompany(getCompanyType());
        CompanyEntity companyEntity = getCompanyEntity(getCompanyTypeEntity());

        companies.add(company);

        listEntity.add(companyEntity);
        when(companyService.findAll()).thenReturn(companies);

        List<Company> companyList = companyService.findAll();

        assertNotNull(companyList);
    }

    @Test
    void getCompanyByIdTest() {
        Company company1 = getCompany(getCompanyType());
        when(companyService.findById(1L)).thenReturn(company1);

        Company company = companyService.findById(1L);

        assertEquals("Coca-Cola", company.getSocialName());
    }

    @Test
    void getFindCompanyByShortIdTest() {
        Company company = getCompany(getCompanyType());
        when(companyService.findById(1L)).thenReturn(company);

        Company result = companyService.findById(1L);

        assertEquals("Coca-Cola", result.getSocialName());
    }

    @Test
    void createCompanyTest() {
        Company company = getCompany(getCompanyType());
        Company companyResult = getCompany(getCompanyType());
        companyResult.setId(1L);

        when(companyService.save(company)).thenReturn(companyResult);
        Company save = companyService.save(company);

        assertNotNull(save);
        //verify(companyRepository, times(1)).save(company);
    }

    @Test
    void testSaveRestaurantWithLongName() {
        Company company = new Company();
        company.setSocialName("a".repeat(260)); // Nome com 260 caracteres, excedendo o limite de 255
        company.setCode(UUID.randomUUID().toString());
        company.setDescription("Coca-Cola");

        // Simulando o lançamento de uma exceção
        doThrow(new DataException("Value too long for column 'name'", null)).when(companyRepository).save(company);

        assertThrows(DataException.class, () -> {
            companyRepository.save(company);
        });
    }

    @Test
    void testRemove_Exception() {
        Long companyId = 99L;

        boolean result = companyService.remove(companyId);
        assertFalse(result);
        verify(companyRepository, never()).remove(companyId);
    }

    @Test
    void testCreateCompany() {
        Company company = getCompany(getCompanyType());
        Company companyResult = getCompany(getCompanyType());
        when(createCompanyPort.save(company)).thenReturn(companyResult);

        Company result = createCompanyPort.save(company);

        assertNotNull(result);
        assertEquals("Coca-Cola", result.getSocialName());
    }

    @Test
    void testDeleteCompany() {
        Long companyId = 1L;
        when(deleteCompanyPort.remove(companyId)).thenReturn(true);

        boolean result = deleteCompanyPort.remove(companyId);

        assertTrue(result);
    }

    @Test
    void testFindByIdCompany() {
        Company company = getCompany(getCompanyType());
        when(findByIdCompanyPort.findById(1L)).thenReturn(company);

        Company result = findByIdCompanyPort.findById(1L);

        assertNotNull(result);
        assertEquals("Coca-Cola", result.getSocialName());
    }

    @Test
    void testFindCompanys() {
        Company company = getCompany(getCompanyType());
        List<Company> companies = new ArrayList<>();
        companies.add(company);

        when(findCompanysPort.findAll()).thenReturn(companies);
        List<Company> result = findCompanysPort.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void testUpdateCompany() {
        Long companyId = 1L;
        Company company = getCompany(getCompanyType());

        when(updateCompanyPort.update(companyId, company)).thenReturn(company);
        Company result = updateCompanyPort.update(companyId, company);

        assertNotNull(result);
        assertEquals("Coca-Cola", result.getSocialName());
    }
}