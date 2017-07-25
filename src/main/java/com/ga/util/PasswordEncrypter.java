package com.ga.util;

import javax.annotation.PostConstruct;
// import javax.ejb.Singleton;
// import javax.ejb.Startup;
// import javax.inject.Inject;
        import org.springframework.security.crypto.bcrypt.BCrypt;

public class PasswordEncrypter {

	@PostConstruct
    private void startup() {
    }
    
	/*Encrypt Password*/
	public static String generateSecuredPasswordHash(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt(10));
    }
    
	/*Compare Password*/
	public static boolean checkPassword(String enteredPwd,
                                        String encryptedPasswordFromDB) {
        return BCrypt.checkpw(enteredPwd, encryptedPasswordFromDB);
    }
}
