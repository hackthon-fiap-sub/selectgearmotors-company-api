package br.com.selectgearmotors.company.domain;

import br.com.selectgearmotors.company.core.domain.Company;
import org.junit.jupiter.api.Test;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CompanyTest {

    @Test
    void testGettersAndSetters() {
        Company company = new Company();
        company.setId(1L);
        company.setCode("CODE123");
        company.setSocialName("Client Name");
        company.setDescription("Client Description");

        assertEquals(1L, company.getId());
        assertEquals("CODE123", company.getCode());
        assertEquals("Client Name", company.getSocialName());
        assertEquals("Client Description", company.getDescription());
    }

    @Test
    void testBuilder() {
        Company company = Company.builder()
                .id(1L)
                .code("CODE123")
                .socialName("Client Name")
                .description("Client Description")
                .build();

        assertEquals(1L, company.getId());
        assertEquals("CODE123", company.getCode());
        assertEquals("Client Name", company.getSocialName());
        assertEquals("Client Description", company.getDescription());
    }

    @Test
    void testUpdate() {
        Company company = new Company();
        company.setId(1L);
        company.setCode("CODE123");
        company.setSocialName("Client Name");
        company.setDescription("Client Description");

        Company newCompany = new Company();
        newCompany.setCode("NEWCODE");
        newCompany.setSocialName("New Client Name");
        newCompany.setDescription("New Client Description");

        company.update(2L, newCompany);

        assertEquals(2L, company.getId());
        assertEquals("NEWCODE", company.getCode());
        assertEquals("New Client Name", company.getSocialName());
        assertEquals("New Client Description", company.getDescription());
    }

    @Test
    void testNoArgsConstructor() {
        Company company = new Company();
        assertNotNull(company);
    }
}