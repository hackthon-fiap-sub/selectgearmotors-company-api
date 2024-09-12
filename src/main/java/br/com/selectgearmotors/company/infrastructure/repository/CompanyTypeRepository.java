package br.com.selectgearmotors.company.infrastructure.repository;

import br.com.selectgearmotors.company.infrastructure.entity.companytype.CompanyTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CompanyTypeRepository extends JpaRepository<CompanyTypeEntity, Long> {
    Optional<CompanyTypeEntity> findByName(String name);
}
