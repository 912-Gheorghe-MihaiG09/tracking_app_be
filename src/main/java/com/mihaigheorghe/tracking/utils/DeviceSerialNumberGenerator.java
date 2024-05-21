package com.mihaigheorghe.tracking.utils;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.Configurable;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;
import java.util.Optional;
import java.util.stream.Stream;

import com.mihaigheorghe.tracking.domain.device.Device;


public class DeviceSerialNumberGenerator
        implements IdentifierGenerator, Configurable {
    @Override
    public Serializable generate(
            SharedSessionContractImplementor session, Object obj)
            throws HibernateException {
        String idFieldName = session.getEntityPersister(obj.getClass().getName(), obj)
                .getIdentifierPropertyName();
        String query = String.format("SELECT %s FROM %s ORDER BY %s DESC",
                idFieldName,
                obj.getClass().getSimpleName(), idFieldName);

        Stream<String> ids = session.createQuery(query, String.class).stream();

        String nextSerialNumber = "00000";

        Optional<String> lastSerialNumberOptional = ids.findFirst();
        if(lastSerialNumberOptional.isPresent()){
            String lastSerialNumber = lastSerialNumberOptional.get().substring(12);
            System.out.println(lastSerialNumber);
            nextSerialNumber = String.valueOf(Integer.valueOf(lastSerialNumber) + 1);
           while(nextSerialNumber.length() != 5){
              nextSerialNumber = "0" + nextSerialNumber;
           }
        }
        return "TD1-0000000-" + nextSerialNumber;
    }
}