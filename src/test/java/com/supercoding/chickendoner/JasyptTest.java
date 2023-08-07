package com.supercoding.chickendoner;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.junit.jupiter.api.Test;

class JasyptTest {

    @Test
    void jasypt() {
        String url = "jdbc:mysql://chikendoner.cqg4abmviyex.ap-northeast-2.rds.amazonaws.com:3306";
        String port = "3306";
        String username = "root";
        String password = "chikendoner123";

        System.out.println("jasyptEncoding(url) = " + jasyptEncoding(url));
        System.out.println("jasyptEncoding(username) = " + jasyptEncoding(username));
        System.out.println("jasyptEncoding(password) = " + jasyptEncoding(password));
        System.out.println("jasyptEncoding(port) = " + jasyptEncoding(port));
    }

    public String jasyptEncoding(String value) {

        String key = "superCoding";
        StandardPBEStringEncryptor pbeEnc = new StandardPBEStringEncryptor();
        pbeEnc.setAlgorithm("PBEWithMD5AndDES");
        pbeEnc.setPassword(key);
        return pbeEnc.encrypt(value);
    }

}
