package com.example.logcollectordemo;

import android.content.Context;
import android.os.Build;
import android.util.Log;

//import com.orhanobut.logger.AndroidLogAdapter;
//import com.orhanobut.logger.FormatStrategy;
//import com.orhanobut.logger.Logger;
//import com.orhanobut.logger.PrettyFormatStrategy;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.lang.reflect.Array;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

//public class LoggerCollector {
//
//    private static String TAG = "LogCollector";
//
//    private final static String LOG = "log";
//    public final static String INFOCONN = "infoconn";
//    public final static String LOG_FILE_HEADER_DIGITALKEY = "digitalkey";
//
//    private static File rootDir = null;
//    private static File logDir = null;
//    private static File logFile = null;
//
//    /**
//     * Log Tag
//     * */
//    private static String TAG_ERROR = "Error";
//    private static String TAG_WARN = "Warn";
//    private static String TAG_INFO = "Info";
//    private static String TAG_DEBUG = "Debug";
//    private static String TAG_VERBOSE = "Verbose";
//
//    /**
//     * Line
//     * */
//    private static final char TOP_LEFT_CORNER = '┌';
//    private static final char BOTTOM_LEFT_CORNER = '└';
//    private static final char MIDDLE_CORNER = '├';
//    private static final char HORIZONTAL_LINE = '│';
//    private static final String DOUBLE_DIVIDER = "────────────────────────────────────────────────────────";
//    private static final String SINGLE_DIVIDER = "┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄";
//    private static final String TOP_BORDER = TOP_LEFT_CORNER + DOUBLE_DIVIDER + DOUBLE_DIVIDER;
//    private static final String BOTTOM_BORDER = BOTTOM_LEFT_CORNER + DOUBLE_DIVIDER + DOUBLE_DIVIDER;
//    private static final String MIDDLE_BORDER = MIDDLE_CORNER + SINGLE_DIVIDER + SINGLE_DIVIDER;
//
//    /**
//     * 로그 저장을 위한 디렉토리 만들기
//     *
//     * @Param context Context
//     * @Param dirName 디렉토리명
//     * */
//    public static void makeLogDir(Context context, String dirName) {
//        rootDir = context.getExternalCacheDir();
//
//        if(rootDir != null) {
//            logDir = new File(rootDir, LOG + "/" + dirName);
//            if(!logDir.exists()) {
//                logDir.mkdirs();
//            }
//        }
//    }
//
//    /**
//     * 로그가 기록될 파일 생성 및 1주 지난 파일 삭제
//     *
//     * @Param fileName 로그 파일명
//     * */
//    public static void settingLogFile(String fileName) {
//        if(rootDir != null && logDir != null) {
//            logFile = new File(logDir, fileName + "_" + getSelectionDate(0) + ".log");
//            deletePassedOneWeekFile();
//            if(!logFile.exists()) {
//                try {
//                    logFile.createNewFile();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        } else {
//            // TODO 상위 디렉토리가 없을 경우
//        }
//    }
//
//    public static AndroidLogAdapter androidLogAdapter = null;
//    public static void settingLogger() {
//        if(androidLogAdapter == null) {
//            FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
//                    .showThreadInfo(false)  // (Optional) Whether to show thread info or not. Default true
//                    .methodCount(3)         // (Optional) How many method line to show. Default 2
////                    .methodOffset(7)        // (Optional) Hides internal method calls up to offset. Default 5
////                .logStrategy(customLog) // (Optional) Changes the log strategy to print out. Default LogCat
////                    .tag("My custom tag")   // (Optional) Global tag for every log. Default PRETTY_LOGGER
//                    .build();
//            androidLogAdapter = new AndroidLogAdapter(formatStrategy);
//            Logger.addLogAdapter(androidLogAdapter);
//        }
//    }
//
//    public static void d(Context context, boolean isRecordLog, boolean isShowLogcat, String message) {
//        if(logFile == null || !logFile.exists() || isEmpty(message)) return;
//        settingLogger();
//
//
//        if(BuildConfig.DEBUG) {
//            baseLogForm(TAG_DEBUG, false, isRecordLog, isShowLogcat, message);
//        } else {
//            baseLogForm(TAG_DEBUG, true, isRecordLog, isShowLogcat, message);
//        }
//    }
//
//    private static void baseLogForm(String tag, boolean isRelease, boolean isRecordLog, boolean isShowLogcat, String message) {
//        if(!isRecordLog && !isShowLogcat) return;
//
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS z", Locale.getDefault());
//        String time = sdf.format(new Date());
////        message = message.replace("\n", "\n" + HORIZONTAL_LINE + "  ");
//        String mode = "";
//        if(isRelease) {
//            mode = "Release";
//        } else {
//            mode = "Debug";
//        }
//        String logText =
//                "" + TOP_BORDER
//                        + "\n" + HORIZONTAL_LINE + "  " + "[" + time + "]"
//                        + "\n" + HORIZONTAL_LINE + "  " + "Tag: " + tag
//                        + "\n" + HORIZONTAL_LINE + "  " + "Mode: " + mode
//                        + "\n" + MIDDLE_BORDER
//                        + "\n" + HORIZONTAL_LINE + "  " + message
//                        + "\n" + BOTTOM_BORDER;
//
//        if(isShowLogcat) {
//            if(tag.equals(TAG_DEBUG)) {
//                Logger.d(logText);
//            } else if(tag.equals(TAG_ERROR)) {
//                Logger.e(logText);
//            } else if(tag.equals(TAG_INFO)) {
//                Logger.i(logText);
//            } else if(tag.equals(TAG_WARN)) {
//                Logger.w(logText);
//            } else if(tag.equals(TAG_VERBOSE)) {
//                Logger.v(logText);
//            }
//        }
//
//        if(isRecordLog) {
//            writeLog(logText);
//        }
//    }
//
//    private static void writeLog(String logText) {
//        try {
//            FileOutputStream fos = new FileOutputStream(logFile, true);
//            OutputStreamWriter osw = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
//            osw.write(logText + "\n\n");
//            osw.close();
//            fos.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * 1주 지난 파일이 있다면 삭제
//     * 파일 형식: 파일네임_날짜.log
//     * */
//    private static void deletePassedOneWeekFile() {
//        if (rootDir != null && logDir != null && logDir.exists()) {
//            File[] fileList = logDir.listFiles();
//
//            for (File file : fileList) {
//                // 파일 이름 얻기
//                String fileName = file.getName();
//
//                // 파일에서 날짜만 빼서 dateString에 저장
//                String startString = "_";
////                String endString = " .log";
//                int startIndex = fileName.lastIndexOf(startString);
//                int endIndex = fileName.length();
//                String dateString = fileName.substring(startIndex + startString.length(), endIndex);
//
//                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
////                String lastModifiedDateString = dateFormat.format(new Date(file.lastModified()));
////                Log.d("TAG", "파일 이름: " + file.getName() + ", 수정 날짜: " + lastModifiedDateString);
//
//                try {
//                    // 날짜 문자열 비교를 위해 Date로 변경
//                    Date madeDate = dateFormat.parse(dateString);
//                    Date today = dateFormat.parse(LogCollector.getTodayDate());
//
//                    // 일수 차이 계산
//                    long diffInMilliseconds = Math.abs(madeDate.getTime() - today.getTime());
//                    long diffInDays = diffInMilliseconds / (24 * 60 * 60 * 1000);
//                    Log.d("TAG", "몇일 차이 날까요? " + diffInDays);
//                    if(diffInDays > 7 && file.exists()) {
//                        // 8일째 되는 것 이므로 삭제
//                        file.delete();
//                    }
//                } catch (ParseException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }
//
//
//    /**
//     * 오늘 날짜
//     * */
//    public static String getTodayDate() {
//        Date currentDate = new Date();
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
//        return dateFormat.format(currentDate);
//    }
//
//    public static String getSelectionDate(int date) {
//        Calendar calendar = Calendar.getInstance();
//        calendar.add(Calendar.DAY_OF_YEAR, date);
//
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
//
//        return dateFormat.format(calendar.getTime());
//    }
//
//    /**
//     * String 객체가 비어있는지 검사한다.
//     *
//     * @param charSeq
//     * @return boolean
//     */
//    public static boolean isEmpty(CharSequence charSeq) {
//        return charSeq == null || charSeq.length() == 0;
//    }
//
//
//
//
//
//
//
//
//
//
//
////    private static String lineTracer() {
////        int level = 4;
////        StackTraceElement[] traces;
////        traces = Thread.currentThread().getStackTrace();
////        return ("at " + traces[level] + " ");
////    }
////
////    private static ArrayList<String> getClassAndMethod(String message) {
////        StackTraceElement ste = Thread.currentThread().getStackTrace()[4];
//////        StringBuffer sb = new StringBuffer();
//////        sb.append(" [ ");
//////        sb.append(ste.getFileName().replace(".java", ""));
//////        sb.append(" :: ");
//////        sb.append(ste.getMethodName());
//////        sb.append(" ] ");
//////        sb.append(message);
////        ArrayList<String> arrClassAndMethod = new ArrayList<>();
////        arrClassAndMethod.add(ste.getFileName());
////        arrClassAndMethod.add(ste.getMethodName());
////        return arrClassAndMethod;
////    }
////
////    public static void logPrintOut(Context context, String logMsg) {
////        //resources.getSystem().getString(R.string.execMode);
////        if("debug".equals("debug") == true) {
////            Log.d("MSG:::By-LogUtil:::", buildLogMsg(logMsg));
////        }
////    }
//}
