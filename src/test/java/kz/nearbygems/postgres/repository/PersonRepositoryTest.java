package kz.nearbygems.postgres.repository;

import kz.nearbygems.postgres.PostgresSampleApplicationTests;
import kz.nearbygems.postgres.model.Person;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PersonRepositoryTest extends PostgresSampleApplicationTests {

  @Autowired
  private TestEntityManager manager;

  @Autowired
  private PersonRepository repository;

  @Test
  public void save() {

    final var person = Person.builder()
                             .username("username")
                             .lastName("lastname")
                             .firstName("firstname")
                             .build();

    //
    final var id = repository.save(person).getId();
    //

    final var savedPerson = manager.find(Person.class, id);

    assertNotNull(savedPerson);

    assertThat(savedPerson.getUsername()).isEqualTo(person.getUsername());
    assertThat(savedPerson.getLastName()).isEqualTo(person.getLastName());
    assertThat(savedPerson.getFirstName()).isEqualTo(person.getFirstName());
  }

  @Test
  public void existsDeveloperByUsername() {

    final var person = Person.builder()
                             .username("username")
                             .lastName("lastname")
                             .firstName("firstname")
                             .build();

    manager.persist(person);

    //
    final var exists = repository.existsPersonByUsername(person.getUsername());
    //

    assertTrue(exists);
  }

  @Test
  public void deleteDeveloperByUsername() {

    final var developer = Person.builder()
                                .username("username")
                                .lastName("lastname")
                                .firstName("firstname")
                                .build();

    final var id = manager.persist(developer).getId();

    //
    repository.deletePersonByUsername(developer.getUsername());
    //

    final var savedChat = manager.find(Person.class, id);

    assertThat(savedChat).isNull();
  }

}
