package com.example.demo;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

//@ComponentScan
@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {
		ClassLoader classLoader = DemoApplication.class.getClassLoader();

		File file = new File(Objects.requireNonNull(classLoader.getResource("newServiceAccountKey.json")).getFile());

		FileInputStream serviceAccount = new FileInputStream(file.getAbsolutePath());

		FirebaseOptions options = new FirebaseOptions.Builder()
				.setCredentials(GoogleCredentials.fromStream(serviceAccount))
				.setDatabaseUrl("https://course-work-1e9b3-default-rtdb.firebaseio.com")
				.build();

		// Проверка на существование FirebaseApp с именем "DEFAULT"
		if (FirebaseApp.getApps().isEmpty()) {
			FirebaseApp.initializeApp(options);
		}

		SpringApplication.run(DemoApplication.class, args);

		CRUDService crudService = new CRUDService();
		CRUD existingCrud = crudService.getCRUD("user_11");
		existingCrud.getRecordIds().add(4321);  // Добавление нового идентификатора
		existingCrud.getRecordIds().remove(0);  // Удаление существующего идентификатора

		crudService.updateCRUD(existingCrud);
	}

}
