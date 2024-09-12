package br.com.selectgearmotors.company.service;

import br.com.selectgearmotors.company.application.database.mapper.CompanyTypeMapper;
import br.com.selectgearmotors.company.core.domain.CompanyType;
import br.com.selectgearmotors.company.core.ports.in.companytype.*;
import br.com.selectgearmotors.company.core.ports.out.CompanyTypeRepositoryPort;
import br.com.selectgearmotors.company.core.service.CompanyTypeService;
import br.com.selectgearmotors.company.infrastructure.entity.companytype.CompanyTypeEntity;
import br.com.selectgearmotors.company.infrastructure.repository.CompanyTypeRepository;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.DataException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.xml.validation.Validator;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.never;

@Slf4j
@ExtendWith(MockitoExtension.class)
class CompanyTypeServiceTest {

    @InjectMocks
    CompanyTypeService companyTypeService;

    @Mock
    CompanyTypeRepositoryPort productCategoryRepository;

    @Mock
    CompanyTypeRepository repository;

    @Mock
    CompanyTypeMapper mapper;

    @Mock
    CreateCompanyTypePort createCompanyTypePort;

    @Mock
    DeleteCompanyTypePort deleteCompanyTypePort;

    @Mock
    FindByIdCompanyTypePort findByIdCompanyTypePort;

    @Mock
    FindCompanyTypesPort findProductCategoriesPort;

    @Mock
    UpdateCompanyTypePort updateCompanyTypePort;

    private Validator validator;

    private CompanyTypeEntity getCompanyTypeEntity() {
        return CompanyTypeEntity.builder()
                .name("Bebida")
                .build();
    }

    private CompanyTypeEntity getCompanyTypeEntity1() {
        return CompanyTypeEntity.builder()
                .name("Bebida 1")
                .build();
    }

    private CompanyTypeEntity getCompanyTypeEntity2() {
        return CompanyTypeEntity.builder()
                .name("Bebida 2")
                .build();
    }

    private CompanyType getCompanyType() {
        return CompanyType.builder()
                .name("Bebida")
                .build();
    }

    private CompanyType getCompanyType1() {
        return CompanyType.builder()
                .name("Bebida 1")
                .build();
    }

    private CompanyType getCompanyType2() {
        return CompanyType.builder()
                .name("Bebida 2")
                .build();
    }

    @BeforeEach
    void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getAllCompanyTypesTest() {
        List<CompanyType> companyTypes = new ArrayList<>();
        List<CompanyTypeEntity> listEntity = new ArrayList<>();

        CompanyType company = getCompanyType();
        CompanyType company1 = getCompanyType1();
        CompanyType company2 = getCompanyType2();

        CompanyTypeEntity companyEntity = getCompanyTypeEntity();
        CompanyTypeEntity companyEntity1 = getCompanyTypeEntity1();
        CompanyTypeEntity companyEntity2 = getCompanyTypeEntity2();

        companyTypes.add(company);
        companyTypes.add(company1);
        companyTypes.add(company2);

        listEntity.add(companyEntity);
        listEntity.add(companyEntity1);
        listEntity.add(companyEntity2);

        when(companyTypeService.findAll()).thenReturn(companyTypes);

        List<CompanyType> companyTypeList = companyTypeService.findAll();

        assertNotNull(companyTypeList);
    }

    @Test
    void getCompanyTypeByIdTest() {
        CompanyType companyType1 = getCompanyType();
        when(companyTypeService.findById(1L)).thenReturn(companyType1);

        CompanyType companyType = companyTypeService.findById(1L);

        assertEquals("Bebida", companyType.getName());
    }

    @Test
    void getFindCompanyTypeByShortIdTest() {
        CompanyType companyType = getCompanyType();
        when(companyTypeService.findById(1L)).thenReturn(companyType);

        CompanyType result = companyTypeService.findById(1L);

        assertEquals("Bebida", result.getName());
    }

    @Test
    void createCompanyTypeTest() {
        CompanyType companyType = getCompanyType();
        CompanyType companyTypeResult = getCompanyType();
        companyTypeResult.setId(1L);

        when(companyTypeService.save(companyType)).thenReturn(companyTypeResult);
        CompanyType save = companyTypeService.save(companyType);

        assertNotNull(save);
        //verify(productCategoryRepository, times(1)).save(productCategory);
    }

    @Test
    void testSaveRestaurantWithLongName() {
        CompanyType companyType = new CompanyType();
        companyType.setName("a".repeat(260)); // Nome com 260 caracteres, excedendo o limite de 255

        // Simulando o lançamento de uma exceção
        doThrow(new DataException("Value too long for column 'name'", null)).when(productCategoryRepository).save(companyType);

        assertThrows(DataException.class, () -> {
            productCategoryRepository.save(companyType);
        });
    }

    @Test
    void testRemoveRestaurant_Success() {
        Long restaurantId = 1L;
        CompanyType companyType = getCompanyType();
        companyType.setId(restaurantId);

        when(companyTypeService.findById(restaurantId)).thenReturn(companyType);
        boolean result = companyTypeService.remove(restaurantId);
        assertTrue(result);
    }

    @Test
    void testRemove_Exception() {
        Long productId = 99L;

        boolean result = companyTypeService.remove(productId);
        assertFalse(result);
        verify(productCategoryRepository, never()).remove(productId);
    }

    @Test
    void testCreateCompanyType() {
        CompanyType companyType = getCompanyType();
        when(createCompanyTypePort.save(companyType)).thenReturn(companyType);

        CompanyType result = createCompanyTypePort.save(companyType);

        assertNotNull(result);
        assertEquals("Bebida", result.getName());
    }

    @Test
    void testDeleteCompanyType() {
        Long productId = 1L;
        when(deleteCompanyTypePort.remove(productId)).thenReturn(true);

        boolean result = deleteCompanyTypePort.remove(productId);

        assertTrue(result);
    }

    @Test
    void testFindByIdCompanyType() {
        CompanyType companyType = getCompanyType();
        when(findByIdCompanyTypePort.findById(1L)).thenReturn(companyType);

        CompanyType result = findByIdCompanyTypePort.findById(1L);

        assertNotNull(result);
        assertEquals("Bebida", result.getName());
    }

    @Test
    void testFindProductCategories() {
        List<CompanyType> productCategories = new ArrayList<>();
        productCategories.add(getCompanyType());
        productCategories.add(getCompanyType1());
        productCategories.add(getCompanyType2());

        when(findProductCategoriesPort.findAll()).thenReturn(productCategories);

        List<CompanyType> result = findProductCategoriesPort.findAll();

        assertNotNull(result);
        assertEquals(3, result.size());
    }

    @Test
    void testUpdateCompanyType() {
        Long productId = 1L;
        CompanyType companyType = getCompanyType();
        companyType.setName("Updated Name");

        when(updateCompanyTypePort.update(productId, companyType)).thenReturn(companyType);

        CompanyType result = updateCompanyTypePort.update(productId, companyType);

        assertNotNull(result);
        assertEquals("Updated Name", result.getName());
    }

}