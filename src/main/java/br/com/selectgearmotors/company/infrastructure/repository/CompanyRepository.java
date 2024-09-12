package br.com.selectgearmotors.company.infrastructure.repository;

import br.com.selectgearmotors.company.infrastructure.entity.company.CompanyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository<CompanyEntity, Long> {
    CompanyEntity findByCode(String code);
    CompanyEntity findBySocialName(String name);
    CompanyEntity findByEmail(String email);
}
