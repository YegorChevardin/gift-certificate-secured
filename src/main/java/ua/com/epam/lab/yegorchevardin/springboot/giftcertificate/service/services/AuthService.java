package ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.service.services;

import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.web.dtos.User;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.web.dtos.security.JwtRequest;
import ua.com.epam.lab.yegorchevardin.springboot.giftcertificate.web.dtos.security.JwtResponse;

/**
 * Interface of the service
 * for defining methods to register and login and update account
 * @author yegorchevardin
 * @version 0.0.1
 */
public interface AuthService {
     /**
     * Method for log in into existing account
     */
     JwtResponse login(JwtRequest jwtRequest);

     /**
      * Method for registering new user account in the system
      */
     User register(User user);

     /**
      * Method for updating new user account in the system
      */
     User updateAccount(User user);

     /**
      * Method for getting current user account
      */
     User findCurrentAccount();

     /**
      * Method for current account deletion
      */
     void deleteAccount();
}
