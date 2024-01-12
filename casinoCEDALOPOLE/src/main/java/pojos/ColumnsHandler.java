package pojos;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ColumnsHandler {
    private List<List<String>> columns;
    private Random random;

    public ColumnsHandler() {
        random = new Random();
        loadColumns();
    }




    private void loadColumns() {
        Gson gson = new Gson();
        try {

            columns = gson.fromJson(new FileReader("src/main/resources/columns.json"),
                    new TypeToken<List<List<String>>>(){}.getType());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public List<String> getRandomColumnSymbols(int columnIndex) {
        List<String> selectedSymbols = new ArrayList<>();
        int startPosition = random.nextInt(columns.get(0).size());

        for (int i = 0; i < 3; i++) {
            selectedSymbols.add(columns.get(columnIndex).get((startPosition + i) % columns.get(columnIndex).size()));
        }

        return selectedSymbols;
    }
}
