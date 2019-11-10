package org.mplywacz.demo;
/*
Author: BeGieU
Date: 14.10.2019
*/

import org.mplywacz.demo.model.AppUser;
import org.mplywacz.demo.repositories.AppUserRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class BootStrap implements CommandLineRunner {
    private final AppUserRepo appUserRepo;

    public BootStrap(AppUserRepo appUserRepo) {
        this.appUserRepo = appUserRepo;
    }

    @Override
    public void run(String... args) throws Exception {
        int[] size = new int[1];
        appUserRepo.findAll().forEach(au -> size[0]++);
        if (size[0] == 0) {
            appUserRepo.save(new AppUser(
                    "Adam",
                    "Kamilczyk",
                    "adm",
                    "adm",
                    "ADMIN"
            ));
            appUserRepo.save(new AppUser(
                    "Kamil",
                    "Badura",
                    "usr",
                    "usr",
                    "USER"
            ));
        }
    }
}
