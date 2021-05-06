package cn.sqh;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.util.unit.DataSize;
import tk.mybatis.spring.annotation.MapperScan;

import javax.servlet.MultipartConfigElement;
//import tk.mybatis.spring.annotation.MapperScan;


@SpringBootApplication//包括有组件扫描等东西
// 扫描mybatis所有的业务mapper接口
@MapperScan("cn.sqh.dao")//mybatis官方的mapper扫描，扫描不到通用Mapper
//@MapperScan("cn.sqh.dao")//tk开头的mapper扫描，可以到扫描通用Mapper
public class Application extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(Application.class);
    }

    /*@Bean
    public MultipartConfigElement multipartConfigElement(){
        MultipartConfigFactory factory = new MultipartConfigFactory();
        //单个文件最大 5m 可以使用读取配置
        factory.setMaxFileSize(DataSize.parse("5120KB")); //KB,MB
        /// 设置总上传数据总大小 50m
        factory.setMaxRequestSize(DataSize.parse("512000KB"));
        return factory.createMultipartConfig();
    }*/

}
