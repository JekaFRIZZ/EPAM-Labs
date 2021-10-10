package com.epam.esm.configuration;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.GiftCertificateTagDao;
import com.epam.esm.dao.TagDao;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.TagService;
import com.epam.esm.validator.GiftCertificateValidator;
import com.epam.esm.validator.TagValidator;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import javax.sql.DataSource;
import java.nio.charset.StandardCharsets;

@Configuration
@ComponentScan("com.epam.esm")
@EnableWebMvc
@PropertySource("classpath:database/db_init.properties")
@EnableTransactionManagement
public class AppConfig implements WebMvcConfigurer {

    private static final String LOCALE = "locale";
    private static final String BASENAME = "i18n/language";

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public TagDao tagDao(JdbcTemplate jdbcTemplate) {
        return new TagDao(jdbcTemplate);
    }

    @Bean
    public GiftCertificateDao giftCertificateDao(JdbcTemplate jdbcTemplate) {
        return new GiftCertificateDao(jdbcTemplate);
    }

    @Bean
    public TagService tagService(JdbcTemplate jdbcTemplate) {
        return new TagService(tagDao(jdbcTemplate),
                giftCertificateTagDao(jdbcTemplate),
                tagValidator());
    }

    @Bean
    public GiftCertificateTagDao giftCertificateTagDao(JdbcTemplate jdbcTemplate) {
        return new GiftCertificateTagDao(jdbcTemplate, tagDao(jdbcTemplate));
    }

    @Bean
    public GiftCertificateService giftCertificateService(JdbcTemplate jdbcTemplate) {
        return new GiftCertificateService(giftCertificateDao(jdbcTemplate),
                giftCertificateTagDao(jdbcTemplate),
                tagService(jdbcTemplate),
                giftCertificateValidator());
    }

    @Bean
    public TagValidator tagValidator() {
        return new TagValidator();
    }

    @Bean
    public GiftCertificateValidator giftCertificateValidator() {
        return new GiftCertificateValidator();
    }

    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename(BASENAME);
        messageSource.setDefaultEncoding(StandardCharsets.UTF_8.name());
        return messageSource;
    }

    @Bean
    public LocaleResolver localeResolver() {
        return new CookieLocaleResolver();
    }

    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
        localeChangeInterceptor.setParamName(LOCALE);
        return localeChangeInterceptor;
    }

    @Bean
    public PlatformTransactionManager transactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
    }
}
