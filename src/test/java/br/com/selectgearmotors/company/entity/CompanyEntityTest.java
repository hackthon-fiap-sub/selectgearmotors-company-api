package br.com.selectgearmotors.company.entity;

import br.com.selectgearmotors.company.core.domain.Company;
import br.com.selectgearmotors.company.infrastructure.entity.company.CompanyEntity;
import br.com.selectgearmotors.company.infrastructure.entity.companytype.CompanyTypeEntity;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CompanyEntityTest {

    @Test
    void testUpdate() {
        // Arrange
        Long id = 1L;
        Company company = new Company();
        company.setSocialName("Updated Name");
        company.setDescription("Updated Description");

        CompanyEntity entity = new CompanyEntity();
        entity.setId(2L);
        entity.setSocialName("Old Name");
        entity.setDescription("Old Description");

        // Act
        entity.update(id, entity);

        // Assert
        assertEquals(id, entity.getId());
        assertEquals("Old Name", entity.getSocialName());
        assertEquals("Old Description", entity.getDescription());
    }

    @Test
    void testGettersAndSetters() {
        CompanyEntity entity = new CompanyEntity();
        entity.setId(1L);
        entity.setSocialName("Old Name");
        entity.setDescription("Old Description");

        assertThat(entity.getId()).isEqualTo(1L);
        assertThat(entity.getSocialName()).isEqualTo("Old Name");
    }

    @Disabled
    void testToString() {
        CompanyTypeEntity companyCategory = new CompanyTypeEntity();
        companyCategory.setId(1L);
        companyCategory.setName("Bebida");

        CompanyEntity company = new CompanyEntity();
        company.setId(1L);
        company.setCode("d7d19a26-846f-4808-818b-ffc3495be7bb");
        company.setSocialName("Old Name");
        company.setDescription("Old Description");

        String expected = "ClientEntity(id=1, code=d7d19a26-846f-4808-818b-ffc3495be7bb, name=Old Name, pic=Old Pic, description=Old Description, companyCategory=ClientCategoryEntity(id=1, name=Bebida), restaurant=RestaurantEntity(id=1, name=Test Restaurant, cnpj=12.345.678/0001-99))";
        assertThat(company).hasToString(expected);
    }

    @Disabled
    void testEqualsAndHashCode() {
        CompanyEntity company1 = CompanyEntity.builder()
                .id(1L)
                .socialName("Old Name")
                .description("Old Description")
                .build();

        CompanyEntity company2 = CompanyEntity.builder()
                .id(1L)
                .socialName("Old Name")
                .description("Old Description")
                .build();

        assertThat(company2).isEqualTo(company2);
        assertThat(company1).hasSameHashCodeAs(company2);
    }
}
