
package movie_world;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import java.util.ArrayList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;


public class JsonParser {
    public static void main(String[] args) {
       String api = "980d96176457a6e65b8bc282bcadccd4";
        try {
            HttpResponse<String> response = Unirest.get("https://api.themoviedb.org/3/search/person?api_key="+api+"&language=en-US&query=sunjay%20dutt&page=1&include_adult=false")
                    // .header("X-RapidAPI-Key", "428f196363msha24cec2880b4c8ep1f5db2jsnd5360ba1cf43")
                    // .header("X-RapidAPI-Host", "cricbuzz-cricket.p.rapidapi.com")
                    .asString();
            
            if(response.getStatus()==200){
                String body = response.getBody();
          //      System.out.println(body);
                
                
          //      ArrayList<String> arrayList = new ArrayList<>();
          JSONParser parser = new JSONParser();
          JSONObject mainobj = (JSONObject)parser.parse(body);
          JSONArray array =  (JSONArray)mainobj.get("results");
            
                for (int i = 0; i < array.size(); i++) {
                    JSONObject jo = (JSONObject)array.get(i);
                    long id = (long)jo.get("id");
                    int id1 = (int)id;
                    String name =(String)jo.get("name");
                    System.out.println(name);
                    Double popularity = (Double) jo.get("popularity");
                    System.out.println(popularity);
                    String known_for =(String) jo.get("known_for_department");
                    System.out.println(known_for);
                   
                    JSONArray allMovies = (JSONArray)jo.get("known_for");
                    for (int j = 0; j < allMovies.size(); j++) {
                        JSONObject joo = (JSONObject)allMovies.get(j);
                        String moviename = (String)joo.get("title");
                        System.out.println(moviename);
                        
                        
                    }
                    
                  
                }
                    
           
            }
           
            
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }
    
}
