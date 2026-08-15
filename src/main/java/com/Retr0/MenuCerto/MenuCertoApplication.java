package com.Retr0.MenuCerto;

import com.Retr0.MenuCerto.Controller.ControladorFood;
import com.Retr0.MenuCerto.Service.ConsumoApi;
import com.Retr0.MenuCerto.Service.convertedados;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MenuCertoApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(MenuCertoApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        var consumindo = new ConsumoApi();
        var conversor = new convertedados();

        ControladorFood aplicação = new ControladorFood();
        aplicação.Principal();
    }
}
