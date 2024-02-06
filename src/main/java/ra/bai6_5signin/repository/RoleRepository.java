package ra.bai6_5signin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ra.bai6_5signin.model.ERoles;
import ra.bai6_5signin.model.Roles;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Roles,Long> {
    Optional<Roles> findByName(ERoles eRoles);
}
