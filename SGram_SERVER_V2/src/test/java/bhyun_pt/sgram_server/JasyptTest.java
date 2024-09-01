package bhyun_pt.sgram_server;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.junit.jupiter.api.Test;

public class JasyptTest {
    @Test
    public void jasyptTest() {

        //given
        String password = "";
        StandardPBEStringEncryptor jasypt = new StandardPBEStringEncryptor();
        jasypt.setPassword(password);
        jasypt.setAlgorithm("PBEWITHMD5ANDDES");

        //when
        String encryptedText = jasypt.encrypt("");
        String decryptedText = jasypt.decrypt(encryptedText);

        System.out.println(encryptedText);
        System.out.println(decryptedText);

    }
}
