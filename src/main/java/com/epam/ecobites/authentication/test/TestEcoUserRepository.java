package com.epam.ecobites.authentication.test;

import com.epam.ecobites.authentication.service.EcoUserDetails;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class TestEcoUserRepository {
    private final List<EcoUserDetails> ecoUserDetailsList = new ArrayList<>();

    public TestEcoUserRepository() {
        ecoUserDetailsList.add(new EcoUserDetails("test-username", "$2a$10$miG0610NIIiniWNjn.g1H.XmNDBNt37UIyVcVyftF3KU.DmbF4FKm")); // password: "test-password"
    }

    public EcoUserDetails getUser(String username) {
        for (EcoUserDetails ecoUserDetails : ecoUserDetailsList) {
            if (username.equals(ecoUserDetails.getUsername())) {
                return ecoUserDetails;
            }
        }
        return null;
    }

    public void addUser(Integer id, String username, String email, String password, String imageUrl) {
        ecoUserDetailsList.add(new EcoUserDetails(username, password));
    }
}
