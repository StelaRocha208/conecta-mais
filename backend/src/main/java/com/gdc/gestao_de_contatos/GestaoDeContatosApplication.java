package com.gdc.gestao_de_contatos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication

public class GestaoDeContatosApplication {

	public static void main(String[] args) {
		SpringApplication.run(GestaoDeContatosApplication.class, args);
	}

	public String olamundo(){
		return "Olá Mundo";
	}

}
