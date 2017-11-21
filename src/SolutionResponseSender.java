import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class SolutionResponseSender {

    public void sendSolution(int gameId, String dragon){
        HttpURLConnection connection = null;
        String apiUrl = "http://www.dragonsofmugloar.com/api/game/" + gameId + "/solution";

        try {
            URL url = new URL(apiUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("PUT");
            connection.setDoOutput(true);
            connection.setRequestProperty("content-type","application/json; charset=utf-8");

            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(connection.getOutputStream());
            outputStreamWriter.write(dragon);
            outputStreamWriter.flush();
            outputStreamWriter.close();

            int status = connection.getResponseCode();
            if(status == 200){
                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line + "\n");
                }
                br.close();

                System.out.println(sb.toString());

                if(sb.toString().contains("\"status\":\"Victory\"")){
                    DragonParty.nrOfWins++;
                } else if((sb.toString().contains("\"status\":\"Defeat\""))){
                    DragonParty.nrOfLosses++;
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(connection !=null){
                connection.disconnect();
            }
        }
    }
}
