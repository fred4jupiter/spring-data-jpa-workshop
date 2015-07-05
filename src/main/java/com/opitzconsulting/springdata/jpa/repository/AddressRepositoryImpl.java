package com.opitzconsulting.springdata.jpa.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.opitzconsulting.springdata.jpa.domain.Address;

/**
 * Implementation of the custom address repository with package visibility used.
 */
class AddressRepositoryImpl implements AddressRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Address> lookForAddressesWithCityLike(String city) {
        TypedQuery<Address> query = entityManager.createQuery("Select a from Address a where a.city like :city", Address.class);
        query.setParameter("city", city);
        return query.getResultList();
    }
}
