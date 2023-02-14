package dev.miguel.backendmobile.respository;

import dev.miguel.backendmobile.domain.entity.MobileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MobileRespository extends JpaRepository<MobileEntity, String> {

}
