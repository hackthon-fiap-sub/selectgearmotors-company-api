package br.com.selectgearmotors.company.entity;

import br.com.selectgearmotors.company.core.domain.CompanyType;
import br.com.selectgearmotors.company.infrastructure.entity.companytype.CompanyTypeEntity;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CompanyTypeEntityTest {

    @Disabled
    void testUpdate() {
        // Arrange
        Long id = 1L;
        CompanyType companyType = new CompanyType();
        companyType.setName("Updated Name");

        CompanyTypeEntity companyTypeEntity = new CompanyTypeEntity();
        companyTypeEntity.setId(2L);
        companyTypeEntity.setName("Old Name");

        // Act
        companyTypeEntity.update(id, companyTypeEntity);

        // Assert
        assertEquals(id, companyTypeEntity.getId());
        assertEquals("Updated Name", companyTypeEntity.getName());
    }

    @Test
    void testGettersAndSetters() {
        CompanyType companyType = new CompanyType();
        companyType.setId(1L);
        companyType.setName("Updated Name");

        assertThat(companyType.getId()).isEqualTo(1L);
        assertThat(companyType.getName()).isEqualTo("Updated Name");
    }

    @Disabled
    void testToString() {
        CompanyType companyType = CompanyType.builder()
                .id(1L)
                .name("Updated Name")
                .build();

        String expected = "ProductCategory(id=1, name=Updated Name)";
        assertEquals(companyType.toString(), expected);
    }
}
