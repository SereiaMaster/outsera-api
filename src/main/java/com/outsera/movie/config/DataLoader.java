package com.outsera.movie.config;

import com.outsera.movie.service.ImportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

import java.io.IOException;

@Configuration
public class DataLoader {

    private static final Logger log = LoggerFactory.getLogger(DataLoader.class);
    private final ImportService importService;

    public DataLoader(
        ImportService importService
    ){
        this.importService = importService;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void loadDataAfterStart() {
        try {
            log.info("Inicio importação do CSV");
            long start = System.nanoTime();
            importService.importMovieCsvFromResource("Movielist.csv");
            long duration = (System.nanoTime() - start) / 1_000_000;
            log.info("CSV importado com sucesso em {}ms", duration);
        } catch (IOException e) {
            log.error("Erro ao importar CSV: {}", e.getMessage());
        }
    }

}
