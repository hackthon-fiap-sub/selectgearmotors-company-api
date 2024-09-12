package br.com.selectgearmotors.company.api.mapper;

import br.com.selectgearmotors.company.application.api.dto.request.CompanyTypeRequest;
import br.com.selectgearmotors.company.application.api.dto.response.CompanyTypeResponse;
import br.com.selectgearmotors.company.application.api.mapper.CompanyTypeApiMapper;
import br.com.selectgearmotors.company.core.domain.CompanyType;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class CompanyTypeApiMapperTest {

    private final CompanyTypeApiMapper mapper = Mappers.getMapper(CompanyTypeApiMapper.class);

    @Test
    void testFromRequest() {
        // Arrange
        CompanyTypeRequest request = new CompanyTypeRequest();
        request.setName("Electronics");

        // Act
        CompanyType companyType = mapper.fromRequest(request);

        // Assert
        assertNotNull(companyType);
        assertEquals("Electronics", companyType.getName());
    }

    @Test
    void testFromEntity() {
        // Arrange
        CompanyType companyType = new CompanyType();
        companyType.setId(1L);
        companyType.setName("Electronics");

        // Act
        CompanyTypeResponse response = mapper.fromEntity(companyType);

        // Assert
        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Electronics", response.getName());
    }

    @Test
    void testMap() {
        // Arrange
        CompanyType companyType1 = new CompanyType();
        companyType1.setId(1L);
        companyType1.setName("Electronics");

        CompanyType companyType2 = new CompanyType();
        companyType2.setId(2L);
        companyType2.setName("Furniture");

        List<CompanyType> productCategories = Arrays.asList(companyType1, companyType2);

        // Act
        List<CompanyTypeResponse> responses = mapper.map(productCategories);

        // Assert
        assertNotNull(responses);
        assertEquals(2, responses.size());

        assertEquals(1L, responses.get(0).getId());
        assertEquals("Electronics", responses.get(0).getName());

        assertEquals(2L, responses.get(1).getId());
        assertEquals("Furniture", responses.get(1).getName());
    }
}
