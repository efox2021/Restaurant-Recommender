package com.mc_03.Backend.Cookie;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;
import java.util.Random;

public class CookieGenerator implements IdentifierGenerator {

    private static final Character[] hexa = {'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f'};

    public static final String generatorName = "CookieGenerator";

    @Override
    public Serializable generate(SharedSessionContractImplementor sharedSessionContractImplementor, Object o) throws HibernateException {
        Random rnd = new Random();
        String returnString = "";
        for(int i = 0; i < 32; i++)
        {
            returnString += hexa[rnd.nextInt(15)];
        }

        return returnString;
    }
}
