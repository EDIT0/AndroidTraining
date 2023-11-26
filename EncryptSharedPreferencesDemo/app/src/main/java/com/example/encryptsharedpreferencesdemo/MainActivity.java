package com.example.encryptsharedpreferencesdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKeys;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.util.Base64;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class MainActivity extends AppCompatActivity {

    private final String BOOL = "Bool";
    private final String STRING = "String";
    private final String INTEGER = "Integer";

    KeyGenParameterSpec keyGenParameterSpec = null;
    String mainKeyAlias = null;
    SharedPreferences encryptedSharedPreferences = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            keyGenParameterSpec = MasterKeys.AES256_GCM_SPEC;
            mainKeyAlias = MasterKeys.getOrCreate(keyGenParameterSpec);

            encryptedSharedPreferences = EncryptedSharedPreferences.create(
                    "secure_prefs",
                    mainKeyAlias,
                    getApplicationContext(),
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            );

            @SuppressLint("CommitPrefEdits")
            SharedPreferences.Editor editor = encryptedSharedPreferences.edit();
            editor.putBoolean(BOOL, true);
            editor.putString(STRING, "Some normal string value");
            editor.putInt(INTEGER, 10);
            editor.commit();

            boolean boolValue = encryptedSharedPreferences.getBoolean(BOOL, false);
            String stringValue = encryptedSharedPreferences.getString(STRING, "Empty Data");
            int intValue = encryptedSharedPreferences.getInt(INTEGER, Integer.MIN_VALUE);

            Log.d(getClass().getSimpleName(), "boolValue: " + boolValue + "\nstringValue: " + stringValue + "\nintValue: " + intValue);
        } catch (GeneralSecurityException e) {
            Log.d(getClass().getSimpleName(), "GeneralSecurityException: " + e.getMessage());
        } catch (IOException e) {
            Log.d(getClass().getSimpleName(), "IOException: " + e.getMessage());
        }

        try {
//            KeyStoreHelper.generateAndStoreAESKey(getApplicationContext());
            boolean isNewKey = AESHelper.generateKey(AESHelper.alias);
            SecretKey secretKey = AESHelper.getKeyStoreKey(AESHelper.alias);

            String[] strArr = AESHelper.encByKeyStoreKey(secretKey, "0 Test Text 123");
            String encText = null;
            String ivText = null;
            for(int i=0;i<strArr.length;i++) {
                Log.d("CryptoAA", "strArr[" + i + "]: " + strArr[i]);
                encText = strArr[0];
                ivText = strArr[1];
            }

            String decText = AESHelper.decByKeyStoreKey(AESHelper.getKeyStoreKey(AESHelper.alias), encText, ivText);
            Log.d("CryptoAA", "decText: " + decText);

        } catch (Exception e) {

        }

        TextView tvValue = (TextView)findViewById(R.id.tvValue);
        tvValue.setOnClickListener(view -> {
            cryptoTest(tvValue);
        });
        cryptoTest(tvValue);

    }

    @SuppressLint("SetTextI18n")
    private void cryptoTest(TextView tvValue) {
        tvValue.setText("KEY_SELECTED_DOMAIN: " + EncryptedSharedPreferencesManager.getInstance()
                .getStringEncryptedPreferences(
                        getApplicationContext(),
                        "test_preferences",
                        EncryptedSharedPreferencesManager.KEY_SELECTED_DOMAIN,
                        null
                ) + "\nKEY_SAVED_ID: " + EncryptedSharedPreferencesManager.getInstance()
                .getIntEncryptedPreferences(
                        getApplicationContext(),
                        "test_preferences",
                        EncryptedSharedPreferencesManager.KEY_SAVED_ID,
                        0
                ) + "\nKEY_KEEP_MY_INFO: " + EncryptedSharedPreferencesManager.getInstance()
                .getFloatEncryptedPreferences(
                        getApplicationContext(),
                        "test_preferences",
                        EncryptedSharedPreferencesManager.KEY_KEEP_MY_INFO,
                        0f
                ) + "\nKEY_KEEP_LOGIN_INFO: " + EncryptedSharedPreferencesManager.getInstance()
                .getLongEncryptedPreferences(
                        getApplicationContext(),
                        "test_preferences",
                        EncryptedSharedPreferencesManager.KEY_KEEP_LOGIN_INFO,
                        0L
                ) + "\nKEY_KEEP_VERSION: " + EncryptedSharedPreferencesManager.getInstance()
                .getBooleanEncryptedPreferences(
                        getApplicationContext(),
                        "test_preferences",
                        EncryptedSharedPreferencesManager.KEY_KEEP_VERSION,
                        false
                )

        );
        EncryptedSharedPreferencesManager.getInstance()
                .setStringEncryptedPreferences(
                        getApplicationContext(),
                        "test_preferences",
                        EncryptedSharedPreferencesManager.KEY_SELECTED_DOMAIN,
                        "http://www.confitech.co.kr/" + System.currentTimeMillis()
                );
        EncryptedSharedPreferencesManager.getInstance()
                .setIntEncryptedPreferences(
                        getApplicationContext(),
                        "test_preferences",
                        EncryptedSharedPreferencesManager.KEY_SAVED_ID,
                        1231321321
                );
        EncryptedSharedPreferencesManager.getInstance()
                .setFloatEncryptedPreferences(
                        getApplicationContext(),
                        "test_preferences",
                        EncryptedSharedPreferencesManager.KEY_KEEP_MY_INFO,
                        20.103f
                );
        EncryptedSharedPreferencesManager.getInstance()
                .setLongEncryptedPreferences(
                        getApplicationContext(),
                        "test_preferences",
                        EncryptedSharedPreferencesManager.KEY_KEEP_LOGIN_INFO,
                        1013123123312L
                );
        EncryptedSharedPreferencesManager.getInstance()
                .setBooleanEncryptedPreferences(
                        getApplicationContext(),
                        "test_preferences",
                        EncryptedSharedPreferencesManager.KEY_KEEP_VERSION,
                        true
                );
    }
}