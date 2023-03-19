package com.example.demo;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class DemoApplication {

	@Value("${esourcing.username}")
	String username;

	@Value("${esourcing.password}")
	String password;

	@Autowired
	MongoTemplate mongoTemplate;
	
	@GetMapping("/")
	public String home() {
		return "Hello World!" + username + " " + password + " " + mongoTemplate.getDb().getName() + "  end";
	}
	
	@GetMapping("/createFile")
	public boolean createFile() throws IOException {
		Date now = new Date();
		String format1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm", Locale.ENGLISH).format(now);
		String data =  "Hello World!" + username + " " + password + " " + format1;
		Path path = Paths.get("/mnt/" + format1);
		Files.write(path, data.getBytes(StandardCharsets.UTF_8));
		return Boolean.TRUE;
	}
	
	@GetMapping("/getFile/{file}")
	public List<String> getFile(@PathVariable String file) throws IOException {
		Path path = Paths.get("/mnt/" + file);
		return Files.readAllLines(path);
	}

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}

