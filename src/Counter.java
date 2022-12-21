//package src;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;



public class Counter {

//  TO DO: 需要自己定义合适的成员变量，来记录每一个class中GOTO语句被trigger的次数。
    static Map<String, Integer> map = new HashMap<>();
    static {
//        System.out.println(outputDir);
//        在Java执行结束之后会自动调用Counter.generateReport(),将结果写入文件。文件写入过程需要自己实现。
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                Counter.generateReport();
            } catch (IOException e) {
                System.err.println("Could not write report to file: " + e);
            }
        }));
    }

    /**
     * reports the goto statement count
     */
    public static synchronized void recordGoto(String name) {
        //  TO DO : 定义recordGoto函数, 来统计每个类中Goto语句被trigger的次数
        map.put(name, map.getOrDefault(name, 0) + 1);
    }

    /**
     * reports the counter content.
     */
    public static void generateReport() throws IOException {
        // TO DO : 将插装统计内容写入文件。please write the results to the file "report/result.txt"
        FileWriter writer = new FileWriter("report/result.txt");
        for(Entry<String, Integer> entry: map.entrySet()) {
            writer.append(entry.getKey() + "\t" + entry.getValue() + "\n");
        }
        writer.flush();
        writer.close();
    }
}