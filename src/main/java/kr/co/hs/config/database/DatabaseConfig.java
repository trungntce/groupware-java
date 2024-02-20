package kr.co.hs.config.database;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import kr.co.hs.common.mybatis.RefreshableSqlSessionFactoryBean;
import kr.co.hs.common.properties.Database;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.io.IOException;

@Slf4j
@Configuration
@EnableTransactionManagement
public class DatabaseConfig {

	@Bean
	public DataSource dataSource() throws Exception {

		ComboPooledDataSource dataSource = new ComboPooledDataSource();

		dataSource.setDriverClass(Database.DRIVER_REAL);
		dataSource.setJdbcUrl(Database.URL_LOCAL_SE);

		dataSource.setUser(Database.USER);
		dataSource.setPassword(Database.PASSWORD);
		dataSource.setLoginTimeout(Database.LOGIN_TIME_OUT);
		dataSource.setInitialPoolSize(Database.INITIAL_POOL_SIZE);
		dataSource.setMaxPoolSize(Database.MAX_POOL_SIZE);
		dataSource.setMinPoolSize(Database.MIN_POOL_SIZE);
		dataSource.setAcquireIncrement(Database.ACQUIRE_INCREMENT);
		dataSource.setAcquireRetryAttempts(Database.ACQUIRE_RETRY_ATTEMPTS);
		dataSource.setAcquireRetryDelay(Database.ACQUIRE_RETRY_DELAY);
		dataSource.setIdleConnectionTestPeriod(Database.IDLE_CONNECTION_TEST_PERIOD);
		dataSource.setDescription(Database.DESCRIPTION);

		return dataSource;
	}
	@Bean
	public SqlSessionFactoryBean sqlSessionFactory(DataSource dataSource, ApplicationContext applicationContext)
			throws IOException {

		RefreshableSqlSessionFactoryBean sqlSessionFactory = new RefreshableSqlSessionFactoryBean();
		sqlSessionFactory.setInterval(1000);
		sqlSessionFactory.setDataSource(dataSource);
		sqlSessionFactory.setConfigLocation(applicationContext.getResource("classpath:mybatis-config.xml"));
		sqlSessionFactory.setMapperLocations(applicationContext.getResources("classpath*:/**/maps/*.xml"));

		return sqlSessionFactory;
	}

	@Bean
	public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory){
		return new SqlSessionTemplate(sqlSessionFactory);
	}

	@Bean
	public PlatformTransactionManager transactionManager(DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}
}
