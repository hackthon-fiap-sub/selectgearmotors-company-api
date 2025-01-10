package br.com.selectgearmotors.vehiclereservation.application.database.repository;

import br.com.selectgearmotors.vehiclereservation.application.api.exception.ResourceFoundException;
import br.com.selectgearmotors.vehiclereservation.application.api.exception.ResourceNotRemoveException;
import br.com.selectgearmotors.vehiclereservation.application.database.mapper.ReservationMapper;
import br.com.selectgearmotors.vehiclereservation.core.domain.Reservation;
import br.com.selectgearmotors.vehiclereservation.core.ports.out.ReservationRepositoryPort;
import br.com.selectgearmotors.vehiclereservation.infrastructure.entity.reservation.ReservationEntity;
import br.com.selectgearmotors.vehiclereservation.infrastructure.repository.ReservationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
public class ReservationRepositoryAdapter implements ReservationRepositoryPort {

    private final ReservationRepository reservationRepository;
    private final ReservationMapper reservationMapper;

    @Autowired
    public ReservationRepositoryAdapter(ReservationRepository reservationRepository, ReservationMapper reservationMapper) {
        this.reservationRepository = reservationRepository;
        this.reservationMapper = reservationMapper;
    }

    @Override
    public Reservation save(Reservation reservation) {
        try {
            ReservationEntity reservationEntity = reservationMapper.fromModelTpEntity(reservation);
            Long reservationId = reservation.getId();

            if (reservationId != null) {
                Optional<ReservationEntity> reservationEntityById = reservationRepository.findById(reservationId);
                if (reservationEntityById.isEmpty()) {
                    throw new ResourceFoundException("Reserva já cadastrado");
                }
            }

            ReservationEntity saved = reservationRepository.save(reservationEntity);
            validateSavedEntity(saved);
            return reservationMapper.fromEntityToModel(saved);
        } catch (ResourceFoundException e) {
            log.error("Erro ao salvar produto: {}", e.getMessage());
            return null;
        }
    }

    @Override
    public boolean remove(Long id) {
         try {
             reservationRepository.deleteById(id);
            return Boolean.TRUE;
        } catch (ResourceNotRemoveException e) {
            return Boolean.FALSE;
        }
    }

    @Override
    public Reservation findById(Long id) {
        Optional<ReservationEntity> buCarSeller = reservationRepository.findById(id);
        return buCarSeller.map(reservationMapper::fromEntityToModel).orElse(null);
    }

    @Override
    public List<Reservation> findAll() {
        List<ReservationEntity> all = reservationRepository.findAll();
        return reservationMapper.map(all);
    }

    @Override
    public Reservation update(Long id, Reservation carSeller) {
        Optional<ReservationEntity> resultById = reservationRepository.findById(id);
        if (resultById.isPresent()) {
            ReservationEntity productCategoryToChange = resultById.get();
            productCategoryToChange.update(id, productCategoryToChange);

            return reservationMapper.fromEntityToModel(reservationRepository.save(productCategoryToChange));
        }
        return null;
    }

    @Override
    public Reservation findByVehicleId(UUID vehicleId) {

        Optional<ReservationEntity> buCarSeller = reservationRepository.findByVehicleId(vehicleId);
        return buCarSeller.map(reservationMapper::fromEntityToModel).orElse(null);
    }

    @Override
    public Reservation findByBuyerId(UUID buyerId) {
        Optional<ReservationEntity> buCarSeller = reservationRepository.findByBuyerId(buyerId);
        return buCarSeller.map(reservationMapper::fromEntityToModel).orElse(null);
    }

    private void validateSavedEntity(ReservationEntity saved) {
        if (saved == null) {
            throw new ResourceFoundException("Erro ao salvar produto no repositorio: entidade salva é nula");
        }
    }
}
