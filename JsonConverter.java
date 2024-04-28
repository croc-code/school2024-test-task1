import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonConverter {
    private final String fileName;
    private final Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeDeserializer())
            .registerTypeAdapter(Status.class, new StatusDeserializer())
            .create();

    public JsonConverter(String fileName) {
        this.fileName = fileName;
    }

    public List<Purchase> getPurchasesFromJson() {
        FileReader reader = null;
        try {
            reader = new FileReader(fileName);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        Type type = new TypeToken<List<Purchase>>(){}.getType();
        return gson.fromJson(reader, type);
    }
    public String convertMonthsToJson(List<String> purchases) {
        Map<String, List<String>> listMap = new HashMap<>();
        listMap.put("months", purchases);
        return gson.toJson(listMap);
    }
}
