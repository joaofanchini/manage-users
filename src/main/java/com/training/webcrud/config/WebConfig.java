package com.training.webcrud.config;

import com.training.webcrud.config.controllers.IndexController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import java.nio.charset.StandardCharsets;
import java.util.Locale;

@EnableWebMvc
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Bean
    @Description("Configuração de carregamento de arquivos de retorno, retornando arquivos HTML. Com estas configurações " +
            "se retornarmos uma string, ele irá assumir que é uma view")
    public ClassLoaderTemplateResolver templateResolver() {
        ClassLoaderTemplateResolver classLoaderTemplateResolver = new ClassLoaderTemplateResolver();
        classLoaderTemplateResolver.setCharacterEncoding(StandardCharsets.UTF_8.name());
        classLoaderTemplateResolver.setPrefix("/webapp/WEB-INF/templates/");
        classLoaderTemplateResolver.setSuffix(".html");
        classLoaderTemplateResolver.setTemplateMode(TemplateMode.HTML);

        return classLoaderTemplateResolver;
    }

    @Bean
    @Profile("develop")
    @Description("Complemento para profile de develop")
    public ClassLoaderTemplateResolver templateResolverComplementDevConfig(ClassLoaderTemplateResolver classLoaderTemplateResolver) {
        classLoaderTemplateResolver.setCacheable(false);

        return classLoaderTemplateResolver;
    }

    @Bean
    @Description("Adicionando resolver de template à engine do Spring")
    public SpringTemplateEngine springTemplateEngine(){
        SpringTemplateEngine springTemplateEngine = new SpringTemplateEngine();
        springTemplateEngine.setTemplateResolver(templateResolver());

        return springTemplateEngine;
    }

    @Bean
    @Description("Adicionando configuração da engine de template ao thymeleaf")
    public ViewResolver viewResolver(){
        ThymeleafViewResolver thymeleafViewResolver = new ThymeleafViewResolver();
        thymeleafViewResolver.setCharacterEncoding(StandardCharsets.UTF_8.name());
        thymeleafViewResolver.setTemplateEngine(springTemplateEngine());

        return thymeleafViewResolver;
    }

    @Bean
    @Description("Configuração para o uso de internacionalização. Pega o locale através do " +
            "envio do header Accept-Language")
    public LocaleResolver resolver() {
        AcceptHeaderLocaleResolver acceptHeaderLocaleResolver = new AcceptHeaderLocaleResolver();
        acceptHeaderLocaleResolver.setDefaultLocale(new Locale("pt_BR"));

        return acceptHeaderLocaleResolver;
    }

    @Override
    @Description("Configuração para quando encontrar o caminho /css no arquivo html, " +
            "ele entrar dentro da pasta que está no resource por exemplo")
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/css/**").addResourceLocations("/css/");
        registry.addResourceHandler("/images/**").addResourceLocations("/images/");
        registry.addResourceHandler("/js/**").addResourceLocations("/js/");
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("index");
    }
}
