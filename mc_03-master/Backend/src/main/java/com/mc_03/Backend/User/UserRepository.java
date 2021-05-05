package com.mc_03.Backend.User;
import java.util.Collection;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;



/**
 * Repository class for <code>Users</code> domain objects All method names are compliant with Spring Data naming
 * conventions so this interface can easily be extended for Spring Data See here: http://static.springsource.org/spring-data/jpa/docs/current/reference/html/jpa.repositories.html#jpa.query-methods.query-creation
 *
 * @author Ehren Fox
 * @author Carson Meyer
 */

@Repository
public interface UserRepository extends CrudRepository<Users, Long> {

    Collection<Users> findAll();

    Optional<Users> findById(@Param("id") Long id);

    Collection<Users> findByUsername(@Param("username") String id);

    Users save(Users users);
}

