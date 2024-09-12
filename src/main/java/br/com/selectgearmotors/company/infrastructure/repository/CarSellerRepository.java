package br.com.selectgearmotors.company.infrastructure.repository;

import br.com.selectgearmotors.company.infrastructure.entity.carseller.CarSellerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CarSellerRepository extends JpaRepository<CarSellerEntity, Long> {
    Optional<CarSellerEntity> findBySocialId(String socialId);
    Optional<CarSellerEntity> findByCompanyEntityId(Long id);
    Optional<CarSellerEntity> findByEmail(String email);
    Optional<CarSellerEntity> findByCode(String code);
}
