package com.diboot.web.config;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.diboot.framework.config.BaseConfig;
import com.diboot.framework.config.BaseCons;
import com.diboot.framework.security.CsrfProtectionInterceptor;
import com.diboot.framework.utils.D;
import com.diboot.framework.utils.DateConverter4Spring;
import com.diboot.web.security.SecurityInterceptor;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.hibernate.validator.HibernateValidator;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.aop.interceptor.SimpleAsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.Executor;

/***
 * Dibo Spring配置文件 
 * @author Mazc@dibo.ltd
 * @version 2016年12月7日
 * Copyright @ www.dibo.ltd
 */
@Configuration
@EnableWebMvc
@EnableAsync
@EnableTransactionManagement(proxyTargetClass=true)
@ComponentScan(basePackages={"com.diboot"})
@MapperScan(basePackages={"com.diboot.**.mapper"})
public class SpringMvcConfig implements WebMvcConfigurer, AsyncConfigurer {
	private static final Logger logger = LoggerFactory.getLogger(SpringMvcConfig.class);
	
	private final Long maxUploadSize = 10485760L;
	
	@Bean(name = "dataSource")
    public DataSource dataSource() {
		// DataSource连接池配置
		HikariConfig config = new HikariConfig();
		config.setJdbcUrl(BaseConfig.getProperty("datasource.url"));
		config.setUsername(BaseConfig.getProperty("datasource.username"));
		config.setPassword(BaseConfig.getProperty("datasource.password"));
		config.setDriverClassName("com.mysql.jdbc.Driver");
		// 生产环境参数
		if(AppConfig.isProductionEnv()){
			config.setMaximumPoolSize(100);
		}
		else{
			// 开发模式下，用于diboot devtools
			config.setMaximumPoolSize(10);
			config.addDataSourceProperty("useInformationSchema", true);
		}
		return new HikariDataSource(config);
    }

	@Bean
    public PlatformTransactionManager txManager() {
		PlatformTransactionManager txManager = new DataSourceTransactionManager(dataSource());
		return txManager;
    }
	
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
    	// 默认拦截器
		SecurityInterceptor appInterceptor = new SecurityInterceptor();
    	registry.addInterceptor(appInterceptor);
		// CSRF拦截器
		CsrfProtectionInterceptor csrfInterceptor = new CsrfProtectionInterceptor();
		csrfInterceptor.setIgnoreAjaxRequest(true);
		registry.addInterceptor(csrfInterceptor);
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

	@Override
	public void addFormatters(FormatterRegistry registry) {
		registry.addConverter(new DateConverter4Spring());
	}

    @Bean(name = "freeMarkerConfigurer")
    public FreeMarkerConfigurer freeMarkerConfigurer(){
		FreeMarkerConfigurer freeMarkerConfigurer = new FreeMarkerConfigurer();
    	freeMarkerConfigurer.setTemplateLoaderPath("/WEB-INF/views-pro/");
    	
    	Properties settings = new Properties();
    	settings.setProperty("locale", "zh_CN");
    	settings.setProperty("default_encoding", BaseCons.CHARSET_UTF8);
    	settings.setProperty("url_escaping_charset", BaseCons.CHARSET_UTF8);
    	settings.setProperty("number_format", "#");
    	settings.setProperty("date_format", "yyyy-MM-dd");
    	settings.setProperty("time_format", "HH:mm");
    	settings.setProperty("datetime_format", "yyyy-MM-dd HH:mm");
    	
    	if(AppConfig.isProductionEnv()){
    		settings.setProperty("template_update_delay", "36000000");
    		settings.setProperty("template_exception_handler", "ignore");
    		logger.info("Freemarker Template 缓存生效.");
    	}
    	else{
			settings.setProperty("template_update_delay", "0");
    	}
    	freeMarkerConfigurer.setFreemarkerSettings(settings);
    	return freeMarkerConfigurer;
    }
    
    @Bean(name = "viewResolver")
	public FreeMarkerViewResolver viewResolver() {
    	FreeMarkerViewResolver viewResolver = new FreeMarkerViewResolver();
    	
    	viewResolver.setCache(true);
    	viewResolver.setPrefix("");
    	viewResolver.setSuffix(".ftl");
    	viewResolver.setContentType("text/html;charset="+BaseCons.CHARSET_UTF8);
    	// get session 
    	viewResolver.setAllowSessionOverride(true);
    	viewResolver.setExposeRequestAttributes(true);
    	viewResolver.setExposeSessionAttributes(true);
    	viewResolver.setExposeSpringMacroHelpers(true);
    	
    	viewResolver.setRequestContextAttribute("ctx");
    	
		return viewResolver;
	}

    @Override
    public Validator getValidator(){
    	LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
    	validator.setProviderClass(HibernateValidator.class);

    	return validator;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
    	registry.addResourceHandler("/static/**").addResourceLocations("/static/");
		//registry.addResourceHandler("/upload/**").addResourceLocations("/upload/");
    }
    
    /**
     * 配置Json与Java对象的转换器
     */
    @Override  
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();

		FastJsonConfig fastJsonConfig = new FastJsonConfig();
		fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat);
		fastJsonConfig.setDateFormat(D.FORMAT_DATETIME_Y4MDHM);
		//处理中文乱码问题
		List<MediaType> fastMediaTypes = new ArrayList<>();
		fastMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
		converter.setSupportedMediaTypes(fastMediaTypes);
		converter.setFastJsonConfig(fastJsonConfig);

		converters.add(converter);
    }
    
    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer.mediaType("json", MediaType.APPLICATION_JSON);
    }
    
    @Bean  
    public MultipartResolver multipartResolver(){  
        CommonsMultipartResolver bean = new CommonsMultipartResolver();  
        bean.setDefaultEncoding(BaseCons.CHARSET_UTF8);
        bean.setMaxUploadSize(maxUploadSize);  
        return bean;
    }

    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(2);
        executor.setQueueCapacity(4);
        executor.setMaxPoolSize(10);
        executor.setThreadNamePrefix("Async-");
        executor.initialize();
        return executor;
    }

    @Override
	public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
		return new SimpleAsyncUncaughtExceptionHandler();
	}

}