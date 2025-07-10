package br.com.maus.services;

import br.com.maus.exception.ResourceNotFoundException;
import br.com.maus.model.Person;
import br.com.maus.repository.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class PersonService {

    private final AtomicLong counter = new AtomicLong();
    private Logger logger = LoggerFactory.getLogger(PersonService.class.getName());

    @Autowired
    PersonRepository repository;

    public Person findById(Long id) {
        logger.info("Finding person...");

        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
    }

    public List<Person> findAll() {
        logger.info("Finding all people...");

        return repository.findAll();
    }

    public Person create(Person person) {
        logger.info("Creating person...");

        return repository.save(person);
    }

    public Person update(Person person) {
        logger.info("Updating person...");

        Person entity = repository.findById(person.getId())
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));

        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setAddress(person.getAddress());
        entity.setGender(person.getGender());

        return repository.save(entity);
    }

    public void delete(Long id) {
        Person entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));

        repository.delete(entity);
    }
}
