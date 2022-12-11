import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.util.HashMap;
import java.util.List;

public class JSONTest {

    public static String test(){

        JSONObject obj = new JSONObject();

        for (int i = 0; i < 2; i++) {
            JSONArray array = new JSONArray();
            for (int j = 0; j < 10; j++) {
                array.add("Obiekt"+j);
            }
            obj.put("Group "+ i, array);
        }
        return obj.toJSONString();
    }



    public static void main(String[] args) {
        String testSTR = test();

        HashMap<String, List<String>> list = new HashMap<>();

        JSONObject obj = (JSONObject) JSONValue.parse(testSTR);
        if(obj != null){
            for(Object test : obj.keySet()){
                JSONArray test1 = (JSONArray) obj.get(test);
                for(Object str : test1){
                    System.out.println(test + ": " + str);
                }
            }
        }



        System.out.println(testSTR);




    }


}
