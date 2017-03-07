package br.com.alois.aloismobile.application.util;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by victor on 2/28/17.
 * Class that contains commons utilities
 */

public class AloisUtils {
    /**
     * Method to encypt passwords using SHA1 encryption
     * @param password
     * @return
     */
    public static String encryptPassword(String password){
        return new String(Hex.encodeHex(DigestUtils.sha1(password)));
    }

    /**
     * Method to validate an email address
     * @param email
     * @return
     */
    public static boolean validateEmail(String email){
        Pattern pattern = Pattern.compile("^.+@.+\\..+$");
        Matcher matcher = pattern.matcher(email);

        return matcher.matches();
    }
}
