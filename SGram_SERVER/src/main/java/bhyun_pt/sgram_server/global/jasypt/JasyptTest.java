package bhyun_pt.sgram_server.global.jasypt;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

public class JasyptTest {

    void set() {
        String url = "db_url";
        String username = "db_username";
        String password = "db_password";

        jasyptEncoding(url);
        jasyptEncoding(username);
        jasyptEncoding(password);
    }

    public String jasyptEncoding(String value) {
        String key = "my_jasypt_key";
        StandardPBEStringEncryptor pbeEnc = new StandardPBEStringEncryptor();
        pbeEnc.setAlgorithm("PBEWithMD5AndDES");
        pbeEnc.setPassword(key);
        return pbeEnc.encrypt(value);
    }
}
