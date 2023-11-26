package com.example.encryptsharedpreferencesdemo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.security.keystore.KeyGenParameterSpec;
import android.util.Log;

import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKeys;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Map;

public class EncryptedSharedPreferencesManager {

    /**
     * 데이터 Key
     * */
    public static final String KEY_SELECTED_DOMAIN = "KEY_SELECTED_DOMAIN";
    public static final String KEY_SAVED_ID = "KEY_SAVED_ID";
    public static final String KEY_KEEP_MY_INFO = "KEY_KEEP_MY_INFO";
    public static final String KEY_KEEP_LOGIN_INFO = "KEY_KEEP_LOGIN_INFO";
    public static final String KEY_KEEP_VERSION = "KEY_KEEP_VERSION";
    public static final String KEY_KEEP_CURRENT_VEHICLE = "KEY_KEEP_CURRENT_VEHICLE";
    public static final String KEY_FCM_TOKEN = "KEY_FCM_TOKEN";
    public static final String KEY_KEEP_VEHICLE_LIST = "KEY_KEEP_VEHICLE_LIST";
    public static final String KEY_SAVED_PW = "KEY_SAVED_PW";
    public static final String KEY_FCM_SAVED_VERSION = "KEY_FCM_SAVED_VERSION";
    public static final String KEY_FCM_LATEST_TOPIC = "KEY_FCM_LATEST_TOPIC";
    public static final String KEY_REMOTE_CONTROL_KEY_LIST = "KEY_REMOTE_CONTROL_KEY_LIST";
    public static final String KEY_REMOTE_CONTROL_KEY_USER = "USER#";
    public static final String KEY_KEEP_REMOTE_CONTROL_KEY = "KEY_KEEP_REMOTE_CONTROL_KEY";
    public static String PREF_WIDGET_SETTING_TRANSPARENCY = "widget_transparency";

    /**
     * API LEVEL 23 이상 변수
     * */
    private final String KEY_SP_MIGRATION = "KEY_SP_MIGRATION";
    private int currentVersion = 1;
    private KeyGenParameterSpec keyGenParameterSpec = null;
    private String mainKeyAlias = null;
    private SharedPreferences encryptedSharedPreferences = null;
    private String _fileName = "";

    private static class SingletonHelper {
        private static final EncryptedSharedPreferencesManager INSTANCE = new EncryptedSharedPreferencesManager();
    }

    public static EncryptedSharedPreferencesManager getInstance() {
        return SingletonHelper.INSTANCE;
    }

    /**
     * Shared Preferences 사용하기 위한 셋팅
     * @param context Context
     * @param fileName S.P xml 파일 네임
     * */
    private void setInitialize(Context context, String fileName) {
        if(encryptedSharedPreferences == null || !_fileName.equals(fileName)) {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            _fileName = fileName;

            /**
             * **기존 S.P에서 E.S.P로 Migration 할 때만 사용되는 코드**
             * Migration 전에 initEncryptedSharedPreferences()를 호출하여 E.S.P를 초기화하면 xml 파일 내에 암호화 관련 값이 들어가서
             * 기존 xml 내에 있는 key, value를 읽어 오는데 문제가 된다.
             * */
//                initEncryptedSharedPreferences(context, _fileName);
            SharedPreferences sp = context.getSharedPreferences(_fileName, Context.MODE_PRIVATE);
            int savedVersion = sp.getInt(KEY_SP_MIGRATION, Integer.MIN_VALUE);
            if(currentVersion > savedVersion) {
                migrateToEncryptedSharedPreferences(context, _fileName);
            }

            /**
             * Migration하지 않을 경우 바로 초기화
             * */
            initEncryptedSharedPreferences(context, fileName);
//                initUnEncryptedSharedPreferences(context, _fileName);
//            }
        }
//        else if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
//            // RSA
//            RSAHelper.init(context);
//            _fileName = fileName;
//            encryptedSharedPreferences = context.getSharedPreferences(_fileName, Context.MODE_PRIVATE);
//        }
    }

    public SharedPreferences getPreferences(Context context, String fileName) {
        setInitialize(context, fileName);
        return encryptedSharedPreferences;
    }

    public SharedPreferences.Editor getPreferencesEditor(Context context, String fileName) {
        setInitialize(context, fileName);
        return encryptedSharedPreferences.edit();
    }

    /**
     * Shared Preferences Key 삭제
     * @param context Context
     * @param fileName S.P xml 파일 네임
     * @param key S.P Key
     * */
    public void removeEncryptedPreferences(Context context, String fileName, String key) {
        setInitialize(context, fileName);

        SharedPreferences esp = getPreferences(context, fileName);
        SharedPreferences.Editor editor = esp.edit();
        editor.remove(key);
        editor.commit();
    }

    /**
     * Shared Preferences 데이터 저장
     * API LEVEL 23 이상은 Encrypted Shared Preferences 사용하고 23 미만은 자체 암호화 사용
     * -> API LEVEL Min 26 이상으로 변경하면서 23 미만 대응 불필요
     * @param context Context
     * @param fileName S.P xml 파일 네임
     * @param key S.P Key
     * @param value S.P Value
     * */
    public void setStringEncryptedPreferences(Context context, String fileName, String key, String value) {
        setInitialize(context, fileName);

        // RSA
//        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M && value instanceof String) {
//            try {
//                value = RSAHelper.encryptData((String) value);
//            } catch (Exception e) {
//                Logg.d("setEncryptedPreferences() Error: " + e.getMessage());
//            }
//        }

        SharedPreferences esp = getPreferences(context, fileName);
        @SuppressLint("CommitPrefEdits")
        SharedPreferences.Editor editor = esp.edit();
        editor.putString(key, (String) value);
        editor.commit();
    }

    public void setIntEncryptedPreferences(Context context, String fileName, String key, int value) {
        setInitialize(context, fileName);

        SharedPreferences esp = getPreferences(context, fileName);
        @SuppressLint("CommitPrefEdits")
        SharedPreferences.Editor editor = esp.edit();
        editor.putInt(key, (int) value);
        editor.commit();
    }

    public void setBooleanEncryptedPreferences(Context context, String fileName, String key, boolean value) {
        setInitialize(context, fileName);

        SharedPreferences esp = getPreferences(context, fileName);
        @SuppressLint("CommitPrefEdits")
        SharedPreferences.Editor editor = esp.edit();
        editor.putBoolean(key, (boolean) value);
        editor.commit();
    }

    public void setFloatEncryptedPreferences(Context context, String fileName, String key, float value) {
        setInitialize(context, fileName);

        SharedPreferences esp = getPreferences(context, fileName);
        @SuppressLint("CommitPrefEdits")
        SharedPreferences.Editor editor = esp.edit();
        editor.putFloat(key, value);
        editor.commit();
    }

    public void setLongEncryptedPreferences(Context context, String fileName, String key, long value) {
        setInitialize(context, fileName);

        SharedPreferences esp = getPreferences(context, fileName);
        @SuppressLint("CommitPrefEdits")
        SharedPreferences.Editor editor = esp.edit();
        editor.putLong(key, value);
        editor.commit();
    }

    /**
     * Shared Preferences 데이터 가져오기
     * API LEVEL 23 이상은 Encrypted Shared Preferences 사용하고 23 미만은 자체 복호화 사용
     * -> API LEVEL Min 26 이상으로 변경하면서 23 미만 대응 불필요
     * @param context Context
     * @param fileName S.P xml 파일 네임
     * @param key S.P Key
     * */
    public String getStringEncryptedPreferences(Context context, String fileName, String key, String defaultValue) {
        setInitialize(context, fileName);

        SharedPreferences esp = getPreferences(context, fileName);
        String value = esp.getString(key, defaultValue);

        // RSA
//        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
//            try {
//                value = RSAHelper.decryptData((String) value);
//            } catch (Exception e) {
//                Logg.d("getStringEncryptedPreferences() Error: " + e.getMessage());
//            }
//        }

        return value;
    }

    public boolean getBooleanEncryptedPreferences(Context context, String fileName, String key, boolean defaultValue) {
        setInitialize(context, fileName);
        SharedPreferences esp = getPreferences(context, fileName);
        return esp.getBoolean(key, defaultValue);
    }

    public int getIntEncryptedPreferences(Context context, String fileName, String key, int defaultValue) {
        setInitialize(context, fileName);
        SharedPreferences esp = getPreferences(context, fileName);
        return esp.getInt(key, defaultValue);
    }

    public float getFloatEncryptedPreferences(Context context, String fileName, String key, float defaultValue) {
        setInitialize(context, fileName);
        SharedPreferences esp = getPreferences(context, fileName);
        return esp.getFloat(key, defaultValue);
    }

    public long getLongEncryptedPreferences(Context context, String fileName, String key, long defaultValue) {
        setInitialize(context, fileName);
        SharedPreferences esp = getPreferences(context, fileName);
        return esp.getLong(key, defaultValue);
    }

    /**
     * Migration
     * 기존 S.P -> 암호화 E.S.P
     * */
    private void migrateToEncryptedSharedPreferences(Context context, String fileName){
//        if (!encryptedSharedPreferences.getAll().isEmpty()) {
        copyTo(context, fileName);
//        }
    }

    private void copyTo(Context context, String fileName) {
        final String TYPE_OF_STRING = "String";
        final String TYPE_OF_INTEGER = "Integer";
        final String TYPE_OF_BOOLEAN = "Boolean";
        final String TYPE_OF_FLOAT = "Float";
        final String TYPE_OF_LONG = "Long";

        // 기존 S.P로 초기화
        initUnEncryptedSharedPreferences(context, fileName);
        SharedPreferences.Editor editor = encryptedSharedPreferences.edit();

        ArrayList<SharedPreferencesMigrationModel> spInfoList = new ArrayList<>();
        Map<String, ?> allEntries = encryptedSharedPreferences.getAll();

        // 기존 값들을 저장하고 삭제
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();

            if (value instanceof String) {
                spInfoList.add(new SharedPreferencesMigrationModel(key, value, TYPE_OF_STRING));
            } else if (value instanceof Integer) {
                spInfoList.add(new SharedPreferencesMigrationModel(key, value, TYPE_OF_INTEGER));
            } else if (value instanceof Boolean) {
                spInfoList.add(new SharedPreferencesMigrationModel(key, value, TYPE_OF_BOOLEAN));
            } else if (value instanceof Float) {
                spInfoList.add(new SharedPreferencesMigrationModel(key, value, TYPE_OF_FLOAT));
            } else if (value instanceof Long) {
                spInfoList.add(new SharedPreferencesMigrationModel(key, value, TYPE_OF_LONG));
            }
            editor.remove(key);
            editor.commit();
        }

        // E.S.P로 초기화
        initEncryptedSharedPreferences(context, fileName);
        SharedPreferences.Editor encryptEditor = encryptedSharedPreferences.edit();
        // 저장한 기존 값들을 암호화 하여 저장
        for(int i=0;i<spInfoList.size();i++) {
            EncryptedSharedPreferencesManager.SharedPreferencesMigrationModel model = spInfoList.get(i);
            if(model.objectType.equals(TYPE_OF_STRING)) {
                encryptEditor.putString(model.key, (String) model.value);
            } else if(model.objectType.equals(TYPE_OF_INTEGER)) {
                encryptEditor.putInt(model.key, (int) model.value);
            } else if(model.objectType.equals(TYPE_OF_BOOLEAN)) {
                encryptEditor.putBoolean(model.key, (boolean) model.value);
            } else if(model.objectType.equals(TYPE_OF_FLOAT)) {
                encryptEditor.putFloat(model.key, (float) model.value);
            } else if(model.objectType.equals(TYPE_OF_LONG)) {
                encryptEditor.putLong(model.key, (long) model.value);
            }
            encryptEditor.commit();
        }

        // 기존 S.P로 초기화
        initUnEncryptedSharedPreferences(context, fileName);
        @SuppressLint("CommitPrefEdits")
        SharedPreferences.Editor unEncryptEditor = encryptedSharedPreferences.edit();
        unEncryptEditor.putInt(KEY_SP_MIGRATION, currentVersion);
        unEncryptEditor.commit();
    }

    /**
     * Encrypted Shared Preferences로 초기화
     * */
    private void initEncryptedSharedPreferences(Context context, String fileName) {
        try {
            keyGenParameterSpec = MasterKeys.AES256_GCM_SPEC;
            mainKeyAlias = MasterKeys.getOrCreate(keyGenParameterSpec);

            encryptedSharedPreferences = EncryptedSharedPreferences.create(
                    fileName,
                    mainKeyAlias,
                    context,
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            );
        } catch (GeneralSecurityException e) {
            Log.d(getClass().getSimpleName(), "setInitialize() GeneralSecurityException Error: " + e.getMessage());
        } catch (IOException e) {
            Log.d(getClass().getSimpleName(), "setInitialize() IOException Error: " + e.getMessage());
        } catch (Exception e) {
            Log.d(getClass().getSimpleName(),  "setInitialize() Exception Error: " + e.getMessage());
        }
    }

    /**
     * 기존 Shared Preferences로 초기화
     * */
    private void initUnEncryptedSharedPreferences(Context context, String fileName) {
        encryptedSharedPreferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
    }

    /**
     * Migration 할 때 사용되는 Data Model
     * */
    private class SharedPreferencesMigrationModel {
        private String key;
        private Object value;
        private String objectType;

        public SharedPreferencesMigrationModel(String key, Object value, String objectType) {
            this.key = key;
            this.value = value;
            this.objectType = objectType;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public void setValue(Object value) {
            this.value = value;
        }

        public void setObjectType(String objectType) {
            this.objectType = objectType;
        }

        public String getKey() {
            return key;
        }

        public Object getValue() {
            return value;
        }

        public String getObjectType() {
            return objectType;
        }
    }
}


