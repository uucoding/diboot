import com.diboot.framework.config.BaseConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.sql.DataSource;

/***
 * Dibo Spring配置文件 
 * @author Mazc@dibo.ltd
 * @version 2016年12月7日
 * Copyright @ www.dibo.ltd
 */
@Configuration
@EnableTransactionManagement(proxyTargetClass=true)
@WebAppConfiguration
@ComponentScan(basePackages = "com.diboot")
@MapperScan(basePackages = "com.diboot.**.mapper")
public class SpringTestConfig implements WebMvcConfigurer {

	@Bean(name = "dataSource")
	public DataSource dataSource() {
		// DataSource连接池配置
		HikariDataSource datasource = new HikariDataSource();
		datasource.setJdbcUrl(BaseConfig.getProperty("spring.datasource.url"));
		datasource.setUsername(BaseConfig.getProperty("spring.datasource.username"));
		datasource.setPassword(BaseConfig.getProperty("spring.datasource.password"));
		datasource.setDriverClassName("com.mysql.jdbc.Driver");
		datasource.setMaximumPoolSize(10);
		return datasource;
	}

	@Bean
	public PlatformTransactionManager txManager() {
		PlatformTransactionManager txManager = new DataSourceTransactionManager(dataSource());
		return txManager;
	}

	@Bean
	public SqlSessionFactory sqlSessionFactory() throws Exception{
		SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
		sqlSessionFactory.setDataSource(dataSource());
		// 设置mybatis-config的路径
		sqlSessionFactory.setConfigLocation(new ClassPathResource("mybatis-config.xml"));
		// 获取当前package并切换到model的路径
		sqlSessionFactory.setTypeAliasesPackage("com.diboot.web.model");

		return sqlSessionFactory.getObject();
	}

}