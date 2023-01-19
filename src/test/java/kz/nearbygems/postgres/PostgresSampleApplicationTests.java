package kz.nearbygems.postgres;

import org.junit.ClassRule;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.PostgreSQLContainer;

@DataJpaTest
@AutoConfigureTestEntityManager
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(initializers = {PostgresSampleApplicationTests.Initializer.class})
public class PostgresSampleApplicationTests {

  @SuppressWarnings("rawtypes")
  @ClassRule
  public static PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer("postgres:12.0")
      .withDatabaseName("db")
      .withUsername("bergen")
      .withPassword("123");

  static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    public void initialize(ConfigurableApplicationContext configurableApplicationContext) {

      postgreSQLContainer.start();

      TestPropertyValues.of("spring.datasource.url=" + postgreSQLContainer.getJdbcUrl(),
                            "spring.datasource.username=" + postgreSQLContainer.getUsername(),
                            "spring.datasource.password=" + postgreSQLContainer.getPassword(),
                            "spring.jpa.database=" + Database.POSTGRESQL,
                            "spring.jpa.generate-ddl=" + true,
                            "spring.liquibase.change-log=" + "classpath:/db/changelog/db.changelog-test.yaml",
                            "spring.liquibase.enabled=" + true,
                            "spring.liquibase.url=" + postgreSQLContainer.getJdbcUrl(),
                            "spring.liquibase.user=" + postgreSQLContainer.getUsername(),
                            "spring.liquibase.password=" + postgreSQLContainer.getPassword())
                        .applyTo(configurableApplicationContext.getEnvironment());
    }

  }

}
