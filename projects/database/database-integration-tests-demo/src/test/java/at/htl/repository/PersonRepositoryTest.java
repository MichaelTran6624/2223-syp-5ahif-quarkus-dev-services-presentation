package at.htl.repository;

import at.htl.entity.Person;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class PersonRepositoryTest {

    @Inject
    PersonRepository personRepository;

    @Test
    @Transactional
    void simple_test_persist_person_success(){

        Person newPerson = new Person();
        newPerson.name = "Kani Lenker";
        newPerson.dob = LocalDate.of(2000,4,4);

        personRepository.persist(newPerson);


        assertEquals(1, personRepository.count());

        Person result = personRepository.find("name", "Kani Lenker")
                .firstResult();

        assertEquals("Kani Lenker", result.name)
                ;
        assertEquals(LocalDate.of(2000,4,4), result.dob
                );
    }

}