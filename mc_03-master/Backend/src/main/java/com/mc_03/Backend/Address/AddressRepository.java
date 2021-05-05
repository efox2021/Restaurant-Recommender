package com.mc_03.Backend.Address;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;


@Repository
public interface AddressRepository extends CrudRepository<Address, Long>{

    Collection<Address> findAll();

    Collection<Address> findByaddressId(int addressId);

    Address save(Address addressSave);

    long count();


}
