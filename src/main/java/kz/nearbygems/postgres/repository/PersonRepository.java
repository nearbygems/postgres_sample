package kz.nearbygems.postgres.repository;

import kz.nearbygems.postgres.model.Person;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends PagingAndSortingRepository<Person, Long> {

  boolean existsPersonByUsername(String username);

  void deletePersonByUsername(String username);

}
