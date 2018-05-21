package com.mavha.personascrud.service;

import com.mavha.personascrud.domain.Person;
import com.mavha.personascrud.exception.PersonAlreadyExistException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

@Service
@Transactional
public class PersonService {

    private final EntityManager entityManager;

    @Inject
    public PersonService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Person createPerson(Person person) throws PersonAlreadyExistException {
        entityManager.persist(person);

        return person;
    }

    public List<Person> findPersons() {
        String queryString = "SELECT p FROM Person p";
        return entityManager.createQuery(queryString, Person.class).getResultList();
    }
}
