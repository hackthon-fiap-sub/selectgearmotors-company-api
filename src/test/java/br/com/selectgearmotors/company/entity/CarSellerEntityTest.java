package br.com.selectgearmotors.company.entity;

import br.com.selectgearmotors.company.infrastructure.entity.carseller.CarSellerEntity;
import br.com.selectgearmotors.company.infrastructure.entity.company.CompanyEntity;
import br.com.selectgearmotors.company.infrastructure.entity.media.MediaEntity;
import org.junit.jupiter.api.Disabled;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CarSellerEntityTest {

    @Disabled
    public void testUpdate() {
        // Arrange
        CompanyEntity companyEntity1 = new CompanyEntity();
        CompanyEntity companyEntity2 = new CompanyEntity();
        MediaEntity mediaEntity = new MediaEntity();

        CarSellerEntity originalEntity = new CarSellerEntity(1L, "Carlos", "carlos@localhost", "OSKSIEEIEIEIE", "(34) 99192-9929","123.456.789-00", LocalDate.now(), "SP 9999999", "SP",  LocalDate.now(),  LocalDate.now(), companyEntity1, mediaEntity);
        CarSellerEntity updatedEntity = new CarSellerEntity(2L, "Carlos", "carlos@localhost", "OSKSIEEIEIEIE", "(34) 99192-9929", "123.456.789-00", LocalDate.now(), "SP 9999999", "SP",  LocalDate.now(),  LocalDate.now(), companyEntity2, mediaEntity);

        // Act
        originalEntity.update(2L, updatedEntity);

        // Assert
        assertEquals(2L, originalEntity.getId());
        assertEquals("123.456.789-00", originalEntity.getSocialId());
        assertEquals(companyEntity2, originalEntity.getCompanyEntity());
    }
}
