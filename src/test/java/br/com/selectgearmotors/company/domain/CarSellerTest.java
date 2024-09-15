package br.com.selectgearmotors.company.domain;

import br.com.selectgearmotors.company.core.domain.CarSeller;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;


public class CarSellerTest {

    @Test
    public void testConstructor() {
        // Arrange & Act
        CarSeller entity = new CarSeller(1L, "Carlos", "carlos@localhost", "OSKSIEEIEIEIE", "(34) 99192-9929", "123.456.789-00", LocalDate.now(), "SP 9999999", "SP", LocalDate.now(),  LocalDate.now(), 1L, 1L);

        // Assert
        assertEquals(1L, entity.getId());
        assertEquals("123.456.789-00", entity.getSocialId());
        assertEquals(1L, entity.getCompanyId());
    }

    @Test
    public void testGettersAndSetters() {
        // Arrange
        CarSeller entity = new CarSeller();

        // Act
        entity.setId(1L);
        entity.setSocialId("123.456.789-00");
        entity.setCompanyId(1L);

        // Assert
        assertEquals(1L, entity.getId());
        assertEquals("123.456.789-00", entity.getSocialId());
        assertEquals(1L, entity.getCompanyId());
    }

    @Disabled
    public void testToString() {
        // Arrange
        CarSeller entity = new CarSeller(1L, "Carlos", "carlos@localhost", "OSKSIEEIEIEIE", "(34) 99192-9929", "123.456.789-00", LocalDate.now(), "SP 9999999", "SP", LocalDate.now(),  LocalDate.now(), 1L, 1L);

        // Act
        String toString = entity.toString();

        // Assert
        assertNotNull(toString);
        assertTrue(toString.contains("CompanyPhysical"));
        assertTrue(toString.contains("id=1"));
        assertTrue(toString.contains("socialId=123.456.789-00"));
        assertTrue(toString.contains("companyId=1"));
    }

    @Disabled
    public void testHashCode() {
        // Arrange
        CarSeller entity1 = new CarSeller(1L, "Carlos", "carlos@localhost", "OSKSIEEIEIEIE", "(34) 99192-9929","123.456.789-00", LocalDate.now(), "SP 9999999", "SP", LocalDate.now(),  LocalDate.now(), 1L, 1L);
        CarSeller entity2 = new CarSeller(1L, "Carlos", "carlos@localhost", "OSKSIEEIEIEIE", "(34) 99192-9929","123.456.789-00", LocalDate.now(), "SP 9999999", "SP", LocalDate.now(),  LocalDate.now(), 1L, 1L);

        // Act & Assert
        assertEquals(entity1.hashCode(), entity2.hashCode());
    }

    @Disabled
    public void testEquals() {
        // Arrange
        CarSeller entity1 = new CarSeller(1L, "Carlos", "carlos@localhost", "OSKSIEEIEIEIE", "(34) 99192-9929","123.456.789-00", LocalDate.now(), "SP 9999999", "SP", LocalDate.now(),  LocalDate.now(), 1L, 1L);
        CarSeller entity2 = new CarSeller(1L, "Carlos", "carlos@localhost", "OSKSIEEIEIEIE", "(34) 99192-9929","123.456.789-00", LocalDate.now(), "SP 9999999", "SP", LocalDate.now(),  LocalDate.now(), 1L, 1L);
        CarSeller entity3 = new CarSeller(2L, "Carlos", "carlos@localhost", "OSKSIEEIEIEIE", "(34) 99192-9929","123.456.789-00", LocalDate.now(), "SP 9999999", "SP", LocalDate.now(),  LocalDate.now(), 2L, 1L);

        // Act & Assert
        assertEquals(entity1, entity2);
        assertNotEquals(entity1, entity3);
    }
}
