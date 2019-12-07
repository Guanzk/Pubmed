package com.searchproject.pubmed.config;

import java.io.*;

public class FileHelper {
    public static String readFileContent(String fileName) throws IOException {
        return readFileContent(new File(fileName));
    }

    public static String readFileContent(File file) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
        StringBuilder sb = new StringBuilder();
        char[] buf = new char[1024];
        while (true) {
            int n = reader.read(buf);
            if (n <= 0) {
                break;
            }
            sb.append(new String(buf, 0, n));
        }
        reader.close();
        return sb.toString();
    }

    public static void writeFileContent(String fileName, String content) throws IOException {
        writeFileContent(new File(fileName), content);
    }

    public static void writeFileContent(File file, String content) throws IOException {
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));
        writer.write(content);
        writer.close();
    }

    public static boolean exists(String file) {
        return new File(file).exists();
    }

}
