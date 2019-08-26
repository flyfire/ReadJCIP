package com.solarexsoft.jcip.juc;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by houruhou on 2019/8/26.
 * Desc:
 */
public class CreateMd {
    public static void main(String[] args) throws IOException {
        File summary = new File("summary.md");
        if (!summary.exists()) {
            summary.createNewFile();
        }
        FileOutputStream summaryOutput = new FileOutputStream(summary);
        summaryOutput.write("# summary\n\n".getBytes());
        summaryOutput.write("* [Introduction](readme.md)".getBytes());
        File solutions = new File("solutions");
        if (!solutions.exists()) {
            solutions.mkdir();
        }
        for (int i = 1; i <= 300; i++) {
            String ist = String.format("L%04d", i);
            String mdFileName = ist + ".md";
            String fName = String.format("solutions/%s.md",ist);
            File tmp = new File(mdFileName);
            if (!tmp.exists()) {
                tmp.createNewFile();
            }
            String str = String.format("\t*[%s](%s)\n", ist, fName);
            summaryOutput.write(str.getBytes());
        }
        summaryOutput.flush();
        summaryOutput.close();
    }
}
