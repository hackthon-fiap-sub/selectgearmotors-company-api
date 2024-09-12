package br.com.selectgearmotors.company.api.mapper;

import br.com.selectgearmotors.company.application.api.dto.request.CarSellerRequest;
import br.com.selectgearmotors.company.application.api.mapper.CarSellerApiMapper;
import br.com.selectgearmotors.company.application.api.mapper.CarSellerApiMapperImpl;
import br.com.selectgearmotors.company.core.domain.CarSeller;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CarSellerApiMapperTest {

    private CarSellerApiMapper mapper;

    @BeforeEach
    void setUp() {
        // Supondo que você esteja usando MapStruct com Spring
        // Se não estiver usando Spring, pode instanciar o mapper diretamente
        mapper = new CarSellerApiMapperImpl();
    }

    @Test
    void testFromRequest() {
        // Arrange
        CarSellerRequest request = new CarSellerRequest();
        request.setSocialId("123.456.789-00");

        // Act
        CarSeller carSeller = mapper.fromRequest(request);

        // Assert
        assertNotNull(carSeller, "O objeto CarSaller não deveria ser nulo");
        assertEquals("123.456.789-00", carSeller.getSocialId(), "O companyId deveria ser '12.345.678/0001-00'");
    }

    @Test
    void testFromRequestWithNullCompanyId() {
        // Arrange
        CarSellerRequest request = new CarSellerRequest();
        request.setSocialId(null);

        // Act
        CarSeller carSeller = mapper.fromRequest(request);

        // Assert
        assertNotNull(carSeller, "O objeto CarSaller não deveria ser nulo, mesmo com companyId nulo");
        assertNull(carSeller.getSocialId(), "O companyId deveria ser nulo");
    }
}
