package com.example.logcollectordemo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipManager {

    public void makeZipFiles(File sourceDirectory, File zipFile) throws IOException {
        try (
                FileOutputStream fos = new FileOutputStream(zipFile);
                ZipOutputStream zos = new ZipOutputStream(fos)
        ) {
            addFilesToZip("", sourceDirectory, zos);
        }
    }

    private void addFilesToZip(String path, File sourceDirectory, ZipOutputStream zos) throws IOException {
        for (File file : sourceDirectory.listFiles()) {
            if (file.isDirectory()) {
                addFilesToZip(path + file.getName() + File.separator, file, zos);
            } else {
                addFileToZip(path, file, zos);
            }
        }
    }

    private void addFileToZip(String path, File file, ZipOutputStream zos) throws IOException {
        String zipEntryPath = path + file.getName();
        ZipEntry zipEntry = new ZipEntry(zipEntryPath);

        zos.putNextEntry(zipEntry);

        try (FileInputStream fis = new FileInputStream(file)) {
            byte[] buffer = new byte[1024];
            int length;
            while ((length = fis.read(buffer)) > 0) {
                zos.write(buffer, 0, length);
            }
        }

        zos.closeEntry();
    }

}
