package br.com.selectgearmotors.company.service;

import br.com.selectgearmotors.company.application.database.mapper.CompanyMapper;
import br.com.selectgearmotors.company.application.database.repository.CarSellerRepositoryAdapter;
import br.com.selectgearmotors.company.core.domain.CarSeller;
import br.com.selectgearmotors.company.core.domain.Company;
import br.com.selectgearmotors.company.core.domain.CompanyType;
import br.com.selectgearmotors.company.core.ports.in.carseller.*;
import br.com.selectgearmotors.company.core.service.CarSellerService;
import br.com.selectgearmotors.company.infrastructure.entity.carseller.CarSellerEntity;
import br.com.selectgearmotors.company.infrastructure.entity.company.CompanyEntity;
import br.com.selectgearmotors.company.infrastructure.entity.companytype.CompanyTypeEntity;
import br.com.selectgearmotors.company.infrastructure.repository.CarSellerRepository;
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
class CallSellerServiceTest {

    @InjectMocks
    CarSellerService carSellerService;

    @Mock
    CarSellerRepositoryAdapter carSellerRepository;

    @Mock
    CompanyRepository repository;

    @Mock
    CompanyMapper mapper;

    @Mock
    CreateCarSellerPort createCarSellerPort;

    @Mock
    DeleteCarSellerPort deleteCarSellerPort;

    @Mock
    FindByIdCarSellerPort findByIdCarSellerPort;

    @Mock
    FindCarSellersPort findCarSellersPort;

    @Mock
    UpdateCarSellerPort updateCarSellerPort;

    private CarSeller getCallSeller(Company company) {
        return CarSeller.builder()
                .socialId("12345678000100")
                .companyId(company.getId())
                .build();
    }

    private CarSellerEntity getCallSellerEntity(CompanyEntity companyEntity) {
        return CarSellerEntity.builder()
                .socialId("12345678000100")
                .companyEntity(companyEntity)
                .build();
    }

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
        List<CarSeller> companys = new ArrayList<>();
        List<CarSellerEntity> listEntity = new ArrayList<>();

        CompanyType companyType = getCompanyType();
        Company company = getCompany(companyType);

        CarSeller carSeller = getCallSeller(company);
        CarSellerEntity carSellerEntity = getCallSellerEntity(getCompanyEntity(getCompanyTypeEntity()));

        companys.add(carSeller);

        listEntity.add(carSellerEntity);

        when(carSellerService.findAll()).thenReturn(companys);

        List<CarSeller> companyList = carSellerService.findAll();

        assertNotNull(companyList);
    }

    @Test
    void getCompanyByIdTest() {
        CompanyType companyType = getCompanyType();
        Company company = getCompany(companyType);

        CarSeller carSeller = getCallSeller(company);
        when(carSellerService.findById(1L)).thenReturn(carSeller);

        CarSeller carSellerResponse = carSellerService.findById(1L);

        assertEquals("12345678000100", carSellerResponse.getSocialId());
    }

    @Test
    void getFindCompanyByShortIdTest() {
        CompanyType companyType = getCompanyType();
        Company company = getCompany(companyType);

        CarSeller carSeller = getCallSeller(company);
        when(carSellerService.findById(1L)).thenReturn(carSeller);

        CarSeller result = carSellerService.findById(1L);

        assertEquals("12345678000100", result.getSocialId());
    }

    @Test
    void createCompanyTest() {
        CompanyType companyType = getCompanyType();
        Company company = getCompany(companyType);

        CarSeller carSeller = getCallSeller(company);
        when(carSellerService.save(carSeller)).thenReturn(carSeller);

        CarSeller save = carSellerService.save(carSeller);

        assertNotNull(save);
        //verify(companyRepository, times(1)).save(company);
    }

    @Test
    void testSaveRestaurantWithLongName() {
        CarSeller carSeller = getCallSeller(getCompany(getCompanyType()));
        carSeller.setSocialId("1".repeat(260)); // Nome com 260 caracteres, excedendo o limite de 255

        // Simulando o lançamento de uma exceção
        doThrow(new DataException("Value too long for column 'name'", null)).when(carSellerRepository).save(carSeller);

        assertThrows(DataException.class, () -> {
            carSellerRepository.save(carSeller);
        });
    }

    @Test
    void testRemove_Exception() {
        Long companyId = 99L;

        boolean result = carSellerService.remove(companyId);
        assertFalse(result);
        verify(carSellerRepository, never()).remove(companyId);
    }

    @Test
    void testCreateCompany() {
        CarSeller carSeller = getCallSeller(getCompany(getCompanyType()));
        when(createCarSellerPort.save(carSeller)).thenReturn(carSeller);

        CarSeller result = createCarSellerPort.save(carSeller);

        assertNotNull(result);
        assertEquals("12345678000100", result.getSocialId());
    }

    @Test
    void testDeleteCompany() {
        Long companyId = 1L;
        when(deleteCarSellerPort.remove(companyId)).thenReturn(true);

        boolean result = deleteCarSellerPort.remove(companyId);

        assertTrue(result);
    }

    @Test
    void testFindByIdCompany() {
        CarSeller carSeller = getCallSeller(getCompany(getCompanyType()));
        when(findByIdCarSellerPort.findById(1L)).thenReturn(carSeller);

        CarSeller result = findByIdCarSellerPort.findById(1L);

        assertNotNull(result);
        assertEquals("12345678000100", result.getSocialId());
    }

    @Test
    void testFindCompanys() {
        CarSeller carSeller = getCallSeller(getCompany(getCompanyType()));
        List<CarSeller> companys = new ArrayList<>();
        companys.add(carSeller);

        when(findCarSellersPort.findAll()).thenReturn(companys);
        List<CarSeller> result = findCarSellersPort.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void testUpdateCompany() {
        Long companyId = 1L;
        CarSeller carSeller = getCallSeller(getCompany(getCompanyType()));

        when(updateCarSellerPort.update(companyId, carSeller)).thenReturn(carSeller);
        CarSeller result = updateCarSellerPort.update(companyId, carSeller);

        assertNotNull(result);
        assertEquals("12345678000100", result.getSocialId());
    }
}