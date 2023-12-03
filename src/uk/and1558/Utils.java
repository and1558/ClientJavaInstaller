package uk.and1558;

import java.awt.Component;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;

import javax.swing.ProgressMonitorInputStream;

public class Utils {

    public static Utils INSTANCE = new Utils();
    int fileSizeOnline;
    
    public Utils() {
        this.fileSizeOnline = 0;
    }
    
    public static InputStream getStreamFromUrl(final String url) throws IOException {
        final URLConnection connection = new URL(url).openConnection();
        connection.setRequestProperty("User-Agent", "InstallClient/1.0");
        connection.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
        connection.setDoOutput(true);
        final int contentLength = connection.getContentLength();
        if (contentLength != -1) {
            Utils.INSTANCE.fileSizeOnline = contentLength;
        }
        return connection.getInputStream();
    }
    
    public static void deleteDirectory(final File dir) {
        if (dir.exists()) {
            File[] listFiles;
            for (int length = (listFiles = dir.listFiles()).length, i = 0; i < length; ++i) {
                final File file = listFiles[i];
                if (file.isDirectory()) {
                    deleteDirectory(file);
                }
                else {
                    file.delete();
                }
            }
        }
    }
    
    public static String readFile(final File fileIn) throws IOException {
        final FileReader fileReader = new FileReader(fileIn);
        final BufferedReader bread = new BufferedReader(fileReader);
        final StringBuilder sb = new StringBuilder();
        String currLine;
        while ((currLine = bread.readLine()) != null && !currLine.startsWith("#")) {
            sb.append(currLine);
        }
        bread.close();
        fileReader.close();
        return sb.toString();
    }
    
    public static void writeFile(final String text, final File file) throws IOException {
        if (file.exists()) {
            file.delete();
        }
        final PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(file, true)));
        writer.println(text);
        writer.close();
    }
    
    public static void writeFile(final InputStream input, final File file) throws IOException {
        if (file.exists()) {
            file.delete();
        }
        final FileOutputStream output = new FileOutputStream(file);
        final byte[] buffer = new byte[8192];
        final ProgressMonitorInputStream progressInput = new ProgressMonitorInputStream(null, file.getPath(), input);
        progressInput.getProgressMonitor().setMillisToDecideToPopup(1);
        progressInput.getProgressMonitor().setMillisToPopup(1);
        progressInput.getProgressMonitor().setMaximum(Utils.INSTANCE.fileSizeOnline);
        int bytesRead;
        while ((bytesRead = progressInput.read(buffer)) != -1) {
            output.write(buffer, 0, bytesRead);
        }
        progressInput.close();
        output.close();
    }
}
