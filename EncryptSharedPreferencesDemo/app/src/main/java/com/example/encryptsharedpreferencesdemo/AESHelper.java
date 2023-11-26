package com.example.encryptsharedpreferencesdemo;

import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.util.Base64;
import android.util.Log;

import java.security.KeyStore;
import java.security.SecureRandom;
import java.util.Enumeration;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;

public class AESHelper {

    public static final String alias = "aes_key";

    // 키스토어 키 존재 여부 확인
    public static boolean isExistKey(String alias) throws Exception {
        KeyStore keyStore = KeyStore.getInstance("AndroidKeyStore");
        keyStore.load(null);
        Enumeration<String> aliases = keyStore.aliases();
        while (aliases.hasMoreElements()) {
            String nextAlias = aliases.nextElement();
            if (nextAlias.equals(alias)) {
                return true;
            }
        }
        return false;
    }

    // 키스토어 키 생성
    public static boolean generateKey(String alias) throws Exception {
        if(!isExistKey(alias)) {
            // AndroidKeyStore에서 사용할 AES 키를 생성하기 위한 KeyGenerator 객체를 생성 / AES 알고리즘 / AndroidKeyStore
            final KeyGenerator keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore");
            // AES 키를 생성할 때 필요한 파라미터 설정
            // KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT 이 키를 암호화와 복호화 목적으로 사용할 수 있음을 나타냄
            final KeyGenParameterSpec keyGenParameterSpec = new KeyGenParameterSpec.Builder(alias,
                    KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT)
                    .setBlockModes(KeyProperties.BLOCK_MODE_GCM) // GCM (Galois/Counter Mode) 사용 / GCM은 데이터 무결성 검증 기능과 함께 암호화를 제공하는 모드
                    .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE) // 패딩 지정 여기서는 패딩을 사용하지 않음을 나타냅니다.
                    .build();
            // 파라미터들을 기반으로 KeyGenerator 초기화 / 이 때, 지정된 파라미터들을 사용하여 안전한 키 생성 환경을 설정합니다.
            keyGenerator.init(keyGenParameterSpec);
            // 설정된 파라미터들을 기반으로 실제 AES 키를 생성하고 AndroidKeyStore에 저장
            keyGenerator.generateKey();
            Log.d("CryptoAA", "키 생성 완료");
            return true;
        } else {
            Log.d("CryptoAA", "이미 키가 존재합니다.");
            return false;
        }
    }

    // 키스토어 키 조회
    public static SecretKey getKeyStoreKey(String alias) throws Exception {
        KeyStore keyStore = KeyStore.getInstance("AndroidKeyStore");
        keyStore.load(null);
        final KeyStore.SecretKeyEntry secretKeyEntry = (KeyStore.SecretKeyEntry) keyStore.getEntry(alias, null);
        return secretKeyEntry.getSecretKey();
    }

    // 키스토어 키로 AES256 암호화
    public static String[] encByKeyStoreKey(SecretKey secretKey, String plainText) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] enc = cipher.doFinal(plainText.getBytes());
        byte[] iv = cipher.getIV();
        String encText = Base64.encodeToString(enc, 0);
        String ivText = Base64.encodeToString(iv, 0);

        String[] result = new String[2];
        result[0] = encText;
        result[1] = ivText;
        return result;
    }

    // 키스토어 키로 AES256 복호화
    public static String decByKeyStoreKey(SecretKey secretKey, String encText, String iv) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        GCMParameterSpec spec = new GCMParameterSpec(128, Base64.decode(iv, 0));
        cipher.init(Cipher.DECRYPT_MODE, secretKey, spec);
        byte[] dec = cipher.doFinal(Base64.decode(encText, 0));  // 확인 필요
        return new String(dec);
    }
}
