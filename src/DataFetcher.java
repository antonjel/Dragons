import org.json.JSONObject;
import org.json.XML;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class DataFetcher {

    public Game getGameData(){
        String gameApiUrl = "http://www.dragonsofmugloar.com/api/game";
        String data = requestData(gameApiUrl);
        Game game = getDataFromJson(data);
        return game;
    }

    public String getWeatherData(int gameId){
        String weatherApiUrl = "http://www.dragonsofmugloar.com/weather/api/report/" + gameId;
        String data = requestData(weatherApiUrl);
        String weather = getDataFromXml(data);
        return weather;
    }

    private String requestData(String apiUrl){
        HttpURLConnection connection = null;
        try {
            URL url = new URL(apiUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            int status = connection.getResponseCode();
            if(status == 200){
                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line + "\n");
                }
                br.close();
                return sb.toString();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(connection != null){
                connection.disconnect();
            }
        }
        return null;
    }

    private Game getDataFromJson(String jsonString){
        Game gameInfo;
        JSONObject game = new JSONObject(jsonString);
        JSONObject knight = game.getJSONObject("knight");
        gameInfo = new Game(
                game.getInt("gameId"),
                knight.getString("name"),
                knight.getInt("attack"),
                knight.getInt("armor"),
                knight.getInt("agility"),
                knight.getInt("endurance")
        );
        return gameInfo;
    }

    private String getDataFromXml(String xmlString){
        JSONObject object = XML.toJSONObject(xmlString);
        JSONObject weatherReport = object.getJSONObject("report");
        String weatherCode = weatherReport.getString("code");
        return weatherCode;
    }
}
