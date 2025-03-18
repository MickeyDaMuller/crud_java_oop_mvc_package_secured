package com.mickeymuller.util;

import java.security.*;
import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import javax.net.ssl.*;
import java.io.*;

public class JavaSecurityClass {

    // 1. Generate RSA Key Pair
    public static KeyPair generateRSAKeyPair() throws Exception {
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
        keyPairGen.initialize(2048);
        return keyPairGen.generateKeyPair();
    }

    // 2. Encrypt with RSA
    public static byte[] encryptRSA(byte[] data, PublicKey publicKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return cipher.doFinal(data);
    }

    // 3. Decrypt with RSA
    public static byte[] decryptRSA(byte[] data, PrivateKey privateKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return cipher.doFinal(data);
    }

    // 4. Generate AES Key
    public static SecretKey generateAESKey() throws Exception {
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(256);
        return keyGen.generateKey();
    }

    // 5. Encrypt with AES
    public static byte[] encryptAES(byte[] data, SecretKey key) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        return cipher.doFinal(data);
    }

    // 6. Decrypt with AES
    public static byte[] decryptAES(byte[] data, SecretKey key) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, key);
        return cipher.doFinal(data);
    }

    // 7. Load a Certificate from File
    public static Certificate loadCertificate(String certPath) throws Exception {
        CertificateFactory certFactory = CertificateFactory.getInstance("X.509");
        try (FileInputStream fis = new FileInputStream(certPath)) {
            return certFactory.generateCertificate(fis);
        }
    }

    // 8. Establish a Secure TLS Connection
    public static void establishSecureTLSConnection(String host, int port) throws Exception {
        SSLSocketFactory factory = (SSLSocketFactory) SSLSocketFactory.getDefault();
        try (SSLSocket socket = (SSLSocket) factory.createSocket(host, port)) {
            socket.startHandshake();
            System.out.println("TLS handshake successful with " + host);
        }
    }

    public static void main(String[] args) {
        try {
            // RSA Example
            KeyPair keyPair = generateRSAKeyPair();
            String message = "Secure Message";
            byte[] encryptedRSA = encryptRSA(message.getBytes(), keyPair.getPublic());
            byte[] decryptedRSA = decryptRSA(encryptedRSA, keyPair.getPrivate());
            System.out.println("Decrypted RSA Message: " + new String(decryptedRSA));

            // AES Example
            SecretKey aesKey = generateAESKey();
            byte[] encryptedAES = encryptAES(message.getBytes(), aesKey);
            byte[] decryptedAES = decryptAES(encryptedAES, aesKey);
            System.out.println("Decrypted AES Message: " + new String(decryptedAES));

            // TLS Connection Example
            establishSecureTLSConnection("www.mickeymuller.com", 443);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
