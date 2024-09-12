package br.com.selectgearmotors.company.api.mapper;

import br.com.selectgearmotors.company.application.api.dto.request.CompanyRequest;
import br.com.selectgearmotors.company.application.api.dto.response.CompanyResponse;
import br.com.selectgearmotors.company.application.api.mapper.CompanyApiMapper;
import br.com.selectgearmotors.company.core.domain.Company;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class CompanyApiMapperTest {

    private CompanyApiMapper companyApiMapper;

    @BeforeEach
    public void setUp() {
        companyApiMapper = Mappers.getMapper(CompanyApiMapper.class);
    }

    @Test
    void testFromRequest() {
        // Arrange
        CompanyRequest request = new CompanyRequest();
        request.setSocialName("Company Name");
        request.setDescription("Company Description");
        // Act
        Company company = companyApiMapper.fromRequest(request);

        // Assert
        assertNotNull(company);
        assertEquals(request.getSocialName(), company.getSocialName());
        assertEquals(request.getDescription(), company.getDescription());
    }

    @Test
    void testFromEntity() {
        // Arrange
        Company product = new Company();
        product.setId(1L);
        product.setCode("P001");
        product.setSocialName("Company Name");
        product.setDescription("Company Description");

        // Act
        CompanyResponse response = companyApiMapper.fromEntity(product);

        // Assert
        assertNotNull(response);
        assertEquals(product.getId(), response.getId());
        assertEquals(product.getCode(), response.getCode());
        assertEquals(product.getSocialName(), response.getSocialName());
        assertEquals(product.getDescription(), response.getDescription());
    }

    @Test
    void testMap() {
        // Arrange
        Company product1 = new Company();
        product1.setId(1L);
        product1.setCode("P001");
        product1.setSocialName("Company 1");
        product1.setDescription("Description 1");

        Company product2 = new Company();
        product2.setId(2L);
        product2.setCode("P002");
        product2.setSocialName("Company 2");
        product2.setDescription("Description 2");

        List<Company> products = Arrays.asList(product1, product2);

        // Act
        List<CompanyResponse> responses = companyApiMapper.map(products);

        // Assert
        assertNotNull(responses);
        assertEquals(2, responses.size());

        CompanyResponse response1 = responses.get(0);
        assertEquals(product1.getId(), response1.getId());
        assertEquals(product1.getCode(), response1.getCode());
        assertEquals(product1.getSocialName(), response1.getSocialName());
        assertEquals(product1.getDescription(), response1.getDescription());

        CompanyResponse response2 = responses.get(1);
        assertEquals(product2.getId(), response2.getId());
        assertEquals(product2.getCode(), response2.getCode());
        assertEquals(product2.getSocialName(), response2.getSocialName());
        assertEquals(product2.getDescription(), response2.getDescription());
    }
}
