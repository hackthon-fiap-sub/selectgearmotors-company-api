package br.com.selectgearmotors.company.factory;

import br.com.selectgearmotors.company.infrastructure.entity.carseller.CarSellerEntity;
import br.com.selectgearmotors.company.infrastructure.entity.company.CompanyEntity;
import br.com.selectgearmotors.company.infrastructure.entity.companytype.CompanyTypeEntity;
import com.github.javafaker.Faker;

import java.util.UUID;

public class ObjectFactory {
    public static ObjectFactory instance;
    private final Faker faker = new Faker();

    private ObjectFactory() {}

    public static ObjectFactory getInstance() {
        if (instance == null) {
            instance = new ObjectFactory();
        }
        return instance;
    }

    public CompanyTypeEntity getCompanyType() {
        return CompanyTypeEntity.builder()
                .id(1L)
                .name("Pragmatico")
                .build();
    }

    public CompanyEntity getCompany(CompanyTypeEntity companyType) {
        return CompanyEntity.builder()
                .socialName(faker.food().vegetable())
                .code(UUID.randomUUID().toString())
                .email(faker.internet().emailAddress())
                .mobile("(34) 97452-6758")
                .address(faker.address().fullAddress())
                .dataProcessingConsent(faker.bool().bool())
                .description("Coca-Cola")
                .companyTypeEntity(companyType)
                .build();
    }

    public CarSellerEntity getCarSellerEntity(CompanyEntity companyEntity) {
        return CarSellerEntity.builder()
                .id(1L)
                .socialId("96738231032")
                .companyEntity(companyEntity)
                .build();
    }

    public CarSellerEntity getCarSellerEntityl(CompanyEntity companyEntity) {
        return CarSellerEntity.builder()
                .id(1L)
                .socialId("96738231032")
                .companyEntity(companyEntity)
                .build();
    }
}