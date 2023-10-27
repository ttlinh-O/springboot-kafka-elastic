package com.example.playground;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.iv.RandomIvGenerator;

public class PlaygroundApplication {

    public static void main(String[] args) {
        StandardPBEStringEncryptor standardPBEStringEncryptor = new StandardPBEStringEncryptor();
        standardPBEStringEncryptor.setPassword("Demo_Pwd!2020");
        standardPBEStringEncryptor.setAlgorithm("PBEWITHHMACSHA512ANDAES_256");
        standardPBEStringEncryptor.setIvGenerator(new RandomIvGenerator());
//        String result = standardPBEStringEncryptor.encrypt("springCloud_Pwd!");
        String result = standardPBEStringEncryptor.encrypt("test1234");
        System.out.println(result);
        System.out.println("--------------------------------");
        System.out.println(standardPBEStringEncryptor.decrypt(result));
    }

}
