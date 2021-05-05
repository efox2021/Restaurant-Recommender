package com.mc_03.Backend.Cookie;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.Collection;

public interface CookieRepository extends CrudRepository<Cookie, Integer> {

    Collection<Cookie> findAll();

    Cookie save(Cookie cookie);

    long count();

    Collection<Cookie> findBycookieId(@Param("cookieId") String cookieId);

    @Transactional
    void deleteBycookieId(@Param("cookieId") String cookieId);

    void delete(Cookie cookie);
}
