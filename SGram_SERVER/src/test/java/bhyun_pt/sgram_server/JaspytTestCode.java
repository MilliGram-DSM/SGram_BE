package bhyun_pt.sgram_server;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.junit.jupiter.api.Test;

public class JaspytTestCode {

    @Test
    void set() {
        String url = "db_url";
        String username = "db_username";
        String password = "db_password";

        System.out.println(jasyptEncoding(url));
        System.out.println(jasyptEncoding(username));;
        System.out.println(jasyptEncoding(password));;
    }

    public String jasyptEncoding(String value) {
        String key = "my_jasypt_key";
        StandardPBEStringEncryptor pbeEnc = new StandardPBEStringEncryptor();
        pbeEnc.setAlgorithm("PBEWithMD5AndDES");
        pbeEnc.setPassword(key);
        return pbeEnc.encrypt(value);
    }
}
