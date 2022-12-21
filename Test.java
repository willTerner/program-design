import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class Test {
   static Map<String, Integer> map = new HashMap<>();
   public static void main(String[] args) {
      map.put("Test", 1);
      try{
         generateReport();
      }catch(Exception ex) {
         ex.printStackTrace();
      }
   }

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
