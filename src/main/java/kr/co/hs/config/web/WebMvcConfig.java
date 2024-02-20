package kr.co.hs.config.web;

import kr.co.hs.common.preparer.Preparer;
import kr.co.hs.common.tiles.DynamicTilesView;
import kr.co.hs.common.tiles.TilesUrlBasedViewResolver;
import kr.co.hs.common.util.MessageUtil;
import kr.co.hs.config.handler.PageInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.view.tiles3.SimpleSpringPreparerFactory;
import org.springframework.web.servlet.view.tiles3.SpringBeanPreparerFactory;
import org.springframework.web.servlet.view.tiles3.TilesConfigurer;

import java.nio.charset.StandardCharsets;
import java.util.Locale;

@Slf4j
@Configuration
@EnableWebMvc
@EnableAspectJAutoProxy
@ComponentScan(basePackages={"kr.co.hs"})
@EnableScheduling
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private PageInterceptor pageInterceptor;

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addRedirectViewController("/", "/login.hs");
//        registry.addRedirectViewController("/favicon.ico", "/resources/img/common/favicon.ico");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
        registry.addResourceHandler("/upload/**").addResourceLocations("/upload/");
    }

    @Bean
    public TilesConfigurer tilesConfigurer(){

        TilesConfigurer tilesConfigurer = new TilesConfigurer();
        tilesConfigurer.setDefinitions("classpath:tiles-defs.xml");
        tilesConfigurer.setPreparerFactoryClass(SimpleSpringPreparerFactory.class);
        return tilesConfigurer;
    }

    @Bean
    public TaskScheduler taskScheduler(){
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(1000000000);
        return scheduler;
    }

    @Bean
    public TilesUrlBasedViewResolver tilesViewResolver() {

        TilesUrlBasedViewResolver tilesUrlBasedViewResolver = new TilesUrlBasedViewResolver();
        tilesUrlBasedViewResolver.setOrder(1);
        tilesUrlBasedViewResolver.setViewClass(DynamicTilesView.class);
        tilesUrlBasedViewResolver.setPrefix("/WEB-INF/views/");
        tilesUrlBasedViewResolver.setSuffix(".jsp");
        tilesUrlBasedViewResolver.setTilesDefinitionName("base");
        tilesUrlBasedViewResolver.setTilesBodyAttributeName("body");
        tilesUrlBasedViewResolver.setTilesDefinitionDelimiter(".");

        return tilesUrlBasedViewResolver;
    }

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {

        registry.jsp("/WEB-INF/views/", ".jsp");
        registry.viewResolver(tilesViewResolver());
    }

    @Bean
    public MultipartResolver multipartResolver() {
        CommonsMultipartResolver resolver=new CommonsMultipartResolver();
        resolver.setDefaultEncoding("utf-8");
        resolver.setMaxUploadSize(500000000);
        resolver.setMaxInMemorySize(99999999);
        return resolver;
    }

    @Bean
    public RestTemplate restTemplate() {

        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        factory.setReadTimeout(45000);
        factory.setConnectTimeout(45000);

        HttpClient httpClient = HttpClients.custom()
                .disableCookieManagement()
                .setMaxConnTotal(120)
                .setMaxConnPerRoute(10)
                .build();

        factory.setHttpClient(httpClient);
        RestTemplate restTemplate = new RestTemplate(factory);
        restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));

        return restTemplate;
    }

    @Bean
    public MessageUtil messageUtil() throws Exception {
        MessageUtil messageUtil = new MessageUtil();
        messageUtil.setMessageSourceAccessor(messageSourceAccessor());
        return messageUtil;
    }

    @Bean
    public MessageSourceAccessor messageSourceAccessor() {
        return new MessageSourceAccessor(messageSource());
    }

    @Bean
    public ReloadableResourceBundleMessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		/* 여러개일경우
		String[] messageArray = new String[]{
			"messages"
		};
		*/
        messageSource.setBasenames("classpath:messages");
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setCacheSeconds(60);

        return messageSource;
    }

    @Bean
    public SessionLocaleResolver localeResolver() throws Exception{
        SessionLocaleResolver sessionLocaleResolver = new SessionLocaleResolver();
        sessionLocaleResolver.setDefaultLocale(new Locale("ko"));
        return sessionLocaleResolver;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
        registry.addInterceptor(pageInterceptor);
    }

    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor interceptor = new LocaleChangeInterceptor();
        interceptor.setParamName("lang");
        return interceptor;
    }
}
