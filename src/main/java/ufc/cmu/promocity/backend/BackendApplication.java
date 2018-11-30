package ufc.cmu.promocity.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import ufc.cmu.promocity.backend.context.*;


/**
 * Aplicacao Principal do SpringBoot que chama todos os servicos em forma standalone
 * @author armandosoaressousa
 *
 */
@SpringBootApplication
public class BackendApplication {
	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);	
	}
}
