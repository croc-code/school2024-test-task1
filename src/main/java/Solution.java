import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Solution {

    private HashMap<Integer, Float> map = new HashMap<>();

    //Главный метод, который возвращает отчет в виде json строки
    public String GetReport(String path){
        InputData[] input =  Deserialize(path);
        List<Integer> months = new ArrayList<>(12);

        //Заполнение HashMap
        // key = month , value = total
        Integer month;
        for (InputData obj: input){
                if (obj.status.equals("COMPLETED")){
                   month = LocalDateTime.parse(obj.ordered_at,
                           DateTimeFormatter.ISO_LOCAL_DATE_TIME).getMonthValue();
                   if (map.containsKey(month)){
                       map.put(month, map.get(month)+Float.parseFloat(obj.total));
                   } else {
                       map.put(month, Float.parseFloat(obj.total));
                   }
                }
        }

        //Поиск маскимальных значений в HashMap
        Integer maxKey = 0;
        for (Integer key :map.keySet()){
            if (maxKey == 0){
                maxKey = key;
            } else {
                if (map.get(key) > map.get(maxKey)) {
                    maxKey = key;
                }
            }
        }
        months.add(maxKey);
        for (Integer key : map.keySet()){
            if(key != maxKey){
                if (map.get(key) - map.get(maxKey) == 0){
                    months.add(key);
                }
            }
        }

        //Сборка итоговой строки
        Collections.sort(months);
        String output = "{\"months\": [";
        int i = 0;
        for(int number : months){
                i++;
                output += "«"  + Month.of(number).toString().toLowerCase() + "»";
                if (i!=months.size()){
                    output +=", ";
                }
        }
        output+="]}";

        return output;
    }



    //Read from Json
    private InputData[] Deserialize(String path){
        ObjectMapper objectMapper = new ObjectMapper();
        InputData[] inputList;
        try {
            inputList = objectMapper.readValue(new File(path), InputData[].class);
            return inputList;
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error in read file");
            return null;
        }

    }
}
class InputData {
    public String user_id;
    public String ordered_at;
    public String status;
    public String total;
}

