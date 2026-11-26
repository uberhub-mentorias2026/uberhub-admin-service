package iftm.microservice.admin_service.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;

@Slf4j
@Configuration
public class FirebaseConfig {

    @Value("${firebase.config.path:serviceAccountKey.json}")
    private String firebaseConfigPath;

    @PostConstruct
    public void inicializarFirebase() {
        try {
            if (!FirebaseApp.getApps().isEmpty()) {
                return;
            }

            ClassPathResource resource = new ClassPathResource(firebaseConfigPath);

            if (!resource.exists()) {
                log.warn("Arquivo '{}' O Firebase não será inicializado agora.", firebaseConfigPath);
                return;
            }

            try (InputStream serviceAccount = resource.getInputStream()) {
                FirebaseOptions options = FirebaseOptions.builder()
                        .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                        .build();

                FirebaseApp.initializeApp(options);
                log.info("Firebase Admin SDK inicializado com sucesso!");
            }
        } catch (IOException e) {
            log.error("Erro ao carregar credenciais do Firebase ({}): {}", firebaseConfigPath, e.getMessage());
        }
    }
}