package cn.blue;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@MapperScan("cn.blue.mapper")
//事务管理器

@EnableTransactionManagement
public class SpringbootDemo1004Application {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootDemo1004Application.class, args);
	}

}
