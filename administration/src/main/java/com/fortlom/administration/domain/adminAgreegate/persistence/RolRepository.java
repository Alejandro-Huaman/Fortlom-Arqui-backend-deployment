package com.fortlom.administration.domain.adminAgreegate.persistence;
import com.fortlom.administration.domain.adminAgreegate.entity.Rol;
import com.fortlom.administration.domain.adminAgreegate.enumeration.Rolname;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface RolRepository extends JpaRepository<Rol,Long>{
    Optional<Rol> findByName(Rolname name);
    boolean existsByName(Rolname name);
}
