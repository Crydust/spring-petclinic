package org.springframework.samples.petclinic.owner;

import com.vladmihalcea.sql.SQLStatementCountValidator;
import net.ttddyy.dsproxy.listener.DataSourceQueryCountListener;
import net.ttddyy.dsproxy.support.ProxyDataSourceBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.annotation.DirtiesContext;

import javax.sql.DataSource;

import static com.vladmihalcea.sql.SQLStatementCountValidator.assertSelectCount;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE;

@SpringBootTest(webEnvironment = NONE)
@DirtiesContext
class OwnerControllerIntegrationTests {

	@TestConfiguration
	static class DataSourceConfiguration {

		@Bean
		public DataSource dataSource(DataSourceProperties dataSourceProperties) {
			return ProxyDataSourceBuilder.create(dataSourceProperties.initializeDataSourceBuilder().build())
					.listener(new DataSourceQueryCountListener()).build();
		}

	}

	@Autowired
	OwnerController owners;

	@Test
	void shouldRetrieveAllPetsInOneQuery() {
		SQLStatementCountValidator.reset();
		owners.showOwner(3);
		assertSelectCount(3);
	}

}
