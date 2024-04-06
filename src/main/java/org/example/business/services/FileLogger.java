package org.example.business.services;

import org.example.config.Configuration;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class FileLogger {
    private final BufferedWriter bufferedWriter;

    public FileLogger(Configuration config) throws IOException {
        var folder = "logs/threads-" + config.threadCount() + "/";
        var fileName = "data-pub" + config.publicationCount() + "-sub" + config.subscriptionCount() + ".log";
        var dir = new File(folder);
        dir.mkdirs();
        var tmp = new File(dir, fileName);
        tmp.createNewFile();
        var fw = new FileWriter(tmp, false);
        this.bufferedWriter = new BufferedWriter(fw);
    }

    public FileLogger log(String content) throws IOException {
        bufferedWriter.write(content);
        return this;
    }

    public <T> FileLogger logList(List<T> list) throws IOException {
        for (var item : list) {
            bufferedWriter.write(item.toString());
            bufferedWriter.newLine();
        }
        return this;
    }

    public FileLogger logTime(long start, long end) throws IOException {
        var elapsed = end - start;
        var dateFormat = new SimpleDateFormat("mm:ss.SSS");
        var result = new Date(elapsed);
        bufferedWriter.write(dateFormat.format(result));
        return this;
    }

    public FileLogger newLine() throws IOException {
        bufferedWriter.newLine();
        return this;
    }

    public void close() throws IOException {
        bufferedWriter.close();
    }
}
