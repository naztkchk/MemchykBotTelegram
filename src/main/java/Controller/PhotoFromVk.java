package Controller;

import Model.Mem;
import com.sun.xml.internal.bind.v2.TODO;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;

public class PhotoFromVk {

    private final String VK_TOKEN = "4148042bb790a3e640fd6bb2d1a5129a39736fa4f9d91121f2985c347260a9a0db6ddc64eb834ad8da023";

    private String listOfGroup[] = {"-57846937", "-45745333", "-36775802", "-66678575"};
    private final Integer COUNT = 15;

    private ArrayList<Mem>  memList = new ArrayList<>();

    public void setPhotos() {

        memList.clear();

        try {

            for(int i = 0; i<listOfGroup.length; i++) {

                URLConnection urlCon = generateUrl(listOfGroup[i], COUNT, VK_TOKEN).openConnection();

                InputStreamReader inputStreamReader = new InputStreamReader(urlCon.getInputStream());

                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                String input = bufferedReader.readLine();

                parse(input);
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Mem getMem() {
        int index = new Random().nextInt(memList.size());
        return memList.get(index);
        //memList.remove(index);
    }

//    public boolean isEmptyList(){
//        System.out.println("Залишилось "+memList.size()+" мемів.");
//        if(memList.isEmpty()) {
//            return true;
//        }else{
//            return false;
//        }
//    }

//    public Integer getListSize(){
//        return memList.size();
//    }

    private void parse(final String s) {

        JSONObject obj = new JSONObject(s);
        JSONObject response  = obj.getJSONObject("response");

        JSONArray items = response.getJSONArray("items"); // Massive of items

        for (int i = 0; i < items.length(); i++) {
            JSONObject temp = items.getJSONObject(i);

            String text = temp.getString("text");

            JSONArray attachments = temp.optJSONArray("attachments"); //Massive of attachments

            if (attachments != null && attachments.length()<2) { //Only one attachment

                JSONObject temp2 = attachments.getJSONObject(0);

                if (temp2.getString("type").equals("photo")) {  //Only photo
                        JSONObject photo = attachments.getJSONObject(0).getJSONObject("photo");
                        String photo_604 = photo.getString("photo_604");
                        if(!text.equals("")){
                            memList.add(new Mem(photo_604, text));
                        }else   memList.add(new Mem(photo_604, "null1"));
                }
            }
        }
    }

    private URL generateUrl (String groupID, Integer count, String accessToken) throws MalformedURLException {
       return new  URL("https://api.vk.com/method/wall.get?v=5.68&owner_id=" + groupID + "&count=" + count + "&access_token=" +accessToken);
    }



}




