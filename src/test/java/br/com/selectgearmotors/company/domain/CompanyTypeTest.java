package br.com.selectgearmotors.company.domain;

import br.com.selectgearmotors.company.core.domain.CompanyType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CompanyTypeTest {

    private CompanyType companyType;
    private CompanyType anotherCompanyType;

    @BeforeEach
    void setUp() {
        companyType = CompanyType.builder()
                .id(1L)
                .name("Beverages")
                .build();

        anotherCompanyType = CompanyType.builder()
                .id(2L)
                .name("Snacks")
                .build();
    }

    @Test
    void testGetters() {
        assertEquals(1L, companyType.getId());
        assertEquals("Beverages", companyType.getName());
    }

    @Test
    void testSetters() {
        companyType.setId(2L);
        companyType.setName("Snacks");

        assertEquals(2L, companyType.getId());
        assertEquals("Snacks", companyType.getName());
    }

    @Test
    void testEquals() {
        CompanyType copy = CompanyType.builder()
                .id(1L)
                .name("Beverages")
                .build();

        assertEquals(companyType, copy);
    }

    @Test
    void testHashCode() {
        CompanyType copy = CompanyType.builder()
                .id(1L)
                .name("Beverages")
                .build();

        assertEquals(companyType.hashCode(), copy.hashCode());
    }

    @Disabled
    void testToString() {
        String expected = "ProductCategory(id=1, name=Beverages)";
        assertEquals(expected, companyType.toString());
    }

    @Test
    void testUpdate() {
        companyType.update(3L, anotherCompanyType);

        assertEquals(3L, companyType.getId());
        assertEquals("Snacks", companyType.getName());
    }

    @Test
    void testNoArgsConstructor() {
        CompanyType newCompanyType = new CompanyType();
        assertNotNull(newCompanyType);
    }

    @Test
    void testAllArgsConstructor() {
        CompanyType newCompanyType = new CompanyType(4L, "Frozen Foods");
        assertEquals(4L, newCompanyType.getId());
        assertEquals("Frozen Foods", newCompanyType.getName());
    }
}
