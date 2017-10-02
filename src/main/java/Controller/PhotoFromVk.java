package Controller;

import Model.Mem;
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
    private final String groupID = "-57846937";
    private final Integer COUNT = 100;
    private Integer index;

    private ArrayList<Mem>  memList = new ArrayList<Mem>();

    public ArrayList<Mem> getList()
    {
        return memList;
    }

    public void setPhotos() {
        memList.clear();

        try {
            URL url = new URL(
                    "https://api.vk.com/method/wall.get?v=5.68&owner_id=" + groupID + "&count=" + COUNT + "&access_token=" + VK_TOKEN
            );

            URLConnection urlCon = url.openConnection();

            InputStreamReader inputStreamReader = new InputStreamReader(urlCon.getInputStream());

            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String input = bufferedReader.readLine();

            parse(input);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Mem getMem() {
        return memList.get(new Random().nextInt(memList.size()));
    }

    private void parse(final String s) {

        //System.out.println(s);

        JSONObject obj = new JSONObject(s);
        JSONObject response  = obj.getJSONObject("response");

        JSONArray items = response.getJSONArray("items"); // Массив сайтов

        for (int i = 0; i < items.length(); i++) {
            JSONObject temp = items.getJSONObject(i);

            String post_type = temp.getString("post_type");
            //System.out.println("post_type -"+post_type);

            String text = temp.getString("text");
            //System.out.println("text -"+text);

            JSONArray attachments = temp.optJSONArray("attachments"); //Массив альбомов

            if (attachments != null && attachments.length()<2) {

                JSONObject temp2 = attachments.getJSONObject(0);
                    if (temp2.getString("type").equals("photo")) {
                        JSONObject photo = attachments.getJSONObject(0).getJSONObject("photo");
                        String photo_604 = photo.getString("photo_604");
                       // System.out.println("url  - "+photo_604);
                        if(!text.equals("")){
                            memList.add(new Mem(photo_604, text));
                        }else   memList.add(new Mem(photo_604, "null1"));
                    }
                    //else System.out.println("не фото");
                }
                //else System.out.println("lenght()>1");

            //System.out.println("------------------------------------");
        }
    }
}




