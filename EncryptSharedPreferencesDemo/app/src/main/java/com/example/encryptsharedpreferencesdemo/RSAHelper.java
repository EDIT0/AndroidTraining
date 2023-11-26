package com.example.encryptsharedpreferencesdemo;

import android.content.Context;
import android.security.KeyPairGeneratorSpec;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.util.Base64;
import android.util.Log;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Calendar;

import javax.crypto.Cipher;
import javax.security.auth.x500.X500Principal;


/**
 * RSA
 * */
public class RSAHelper {

    public static final String ALIAS = "keykey";
    public static final String ANDROID_KEYSTORE = "AndroidKeyStore";
    public static final String MODE = "AES/ECB/PKCS5Padding";

    public static void init(Context context) {
        try {
            KeyStore keyStore = KeyStore.getInstance(ANDROID_KEYSTORE);
            keyStore.load(null);

            if (!keyStore.containsAlias(ALIAS)) {
                /**
                 * API LEVEL 23 이상
                 * */
                KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(
                        KeyProperties.KEY_ALGORITHM_RSA, ANDROID_KEYSTORE);
                keyPairGenerator.initialize(
                        new KeyGenParameterSpec.Builder(
                                ALIAS,
                                KeyProperties.PURPOSE_DECRYPT)
                                .setDigests(KeyProperties.DIGEST_SHA256, KeyProperties.DIGEST_SHA512)
                                .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_RSA_PKCS1)
                                .build());

                KeyPair keyPair = keyPairGenerator.generateKeyPair();
                Cipher cipher = Cipher.getInstance(MODE);
                cipher.init(Cipher.DECRYPT_MODE, keyPair.getPrivate());

                /**
                 * API LEVEL 22 이하
                 * */
//                Calendar start = Calendar.getInstance();
//                Calendar end = Calendar.getInstance();
//                end.add(Calendar.YEAR, Integer.MAX_VALUE);
//
//                KeyPairGeneratorSpec spec = new KeyPairGeneratorSpec.Builder(context)
//                        .setAlias(ALIAS)
//                        .setSubject(new X500Principal("CN=" + ALIAS))
//                        .setSerialNumber(BigInteger.ONE)
//                        .setStartDate(start.getTime())
//                        .setEndDate(end.getTime())
//                        .build();
//
//                KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(KeyProperties.KEY_ALGORITHM_RSA, ANDROID_KEYSTORE);
//                keyPairGenerator.initialize(spec);
//                KeyPair keyPair = keyPairGenerator.generateKeyPair();
//                Cipher cipher = Cipher.getInstance(MODE);
//                cipher.init(Cipher.DECRYPT_MODE, keyPair.getPrivate());
            }
        } catch (Exception e) {
            Log.d("CryptoAA", "init Error: " + e);
        }

    }

    public static String encryptData(String data) {
        String encryptedData = "";

        try {
            KeyStore keyStore = KeyStore.getInstance(ANDROID_KEYSTORE);
            keyStore.load(null);

            PublicKey publicKey = keyStore.getCertificate(ALIAS).getPublicKey(); // 키스토어의 공개키 반환
            Log.d("CryptoAA", "PublicKey: " + publicKey);

            Cipher cipher = Cipher.getInstance(MODE); // 운용모드/패딩 셋업
            cipher.init(Cipher.ENCRYPT_MODE, publicKey); // 암호화 준비

            byte[] encrypted = cipher.doFinal(data.getBytes(StandardCharsets.UTF_8)); // 매개변수로 받은 데이터 암호화

            encryptedData = new String(Base64.encode(encrypted, Base64.DEFAULT), StandardCharsets.UTF_8);


        } catch (Exception e) {
            e.printStackTrace();
        }
        return encryptedData;
    }

    public static String decryptData(String encryptedData) {
        String decryptedData = "";

        try {
            KeyStore keyStore = KeyStore.getInstance(ANDROID_KEYSTORE);
            keyStore.load(null);

            PrivateKey privateKey = (PrivateKey) keyStore.getKey(ALIAS, null);
            Log.d("CryptoAA", "PrivateKey: " + privateKey);

            Cipher cipher = Cipher.getInstance(MODE); // 운용모드/패딩 셋업
            cipher.init(Cipher.DECRYPT_MODE, privateKey);

            // 암호화된 인코딩 데이터 -> 디코딩
            byte[] byteStr = Base64.decode(encryptedData.getBytes(StandardCharsets.UTF_8), Base64.DEFAULT);
            // 디코딩된 암호문 -> 복호화 후 문자열 변환
            decryptedData = new String(cipher.doFinal(byteStr));


        } catch (Exception e) {
            e.printStackTrace();
        }

        return decryptedData;
    }
}