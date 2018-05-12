package edu.monash.swan.ass2.Common;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class RestClient {
    private final static String StudentFacadeREST = "http://192.168.191.1:8080/Ass1/webresources/com.pojo.student/";

    private static String SendGet(String url) {
        // 定义一个字符串用来存储网页内容
        String result = "";
        // 定义一个缓冲字符输入流
        BufferedReader in = null;
        try {
            // 将string转成url对象
            URL realUrl = new URL(url);
            // 初始化一个链接到那个url的连接
            URLConnection connection = realUrl.openConnection();
            // 开始实际的连接
            connection.connect();
            // 初始化 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            // 用来临时存储抓取到的每一行的数据
            String line;
            while ((line = in.readLine()) != null) {
                //遍历抓取到的每一行并将其存储到result里面
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }
    public static Student findByEmail(String email){
        Student student = null;
        String url = StudentFacadeREST + "findByEmail/" + email;
        String res = SendGet(url);
        if(!res.equals("")){
            JSONArray jsonArray;
            try {
                jsonArray = new JSONArray(res);
            } catch (Exception e) {
                jsonArray = null;
            }
            if(jsonArray != null) {
                try{
                    JSONObject js = (JSONObject)jsonArray.get(0);
                    String firstName = js.getString("firstnm");
                    String surname = js.getString("surname");
                    String dob = js.getString("dob");
                    String gender = js.getString("gender");
                    String course = js.getString("course");
                    String studyMode = js.getString("studyMode");
                    String address = js.getString("address");
                    String suburb = js.getString("suburb");
                    String nationality = js.getString("nationality");
                    String language = js.getString("language");
                    String favouriteSport = js.getString("favouriteSport");
                    String favouriteMovie = js.getString("favouriteMovie");
                    String favouriteUnit = js.getString("favouriteUnit");
                    String currentJob = js.getString("currentJob");
                    String password = js.getString("password");
                    student = new Student(firstName, surname, dob, gender, course, studyMode, address, suburb, nationality, language, favouriteSport, favouriteMovie, favouriteUnit, currentJob, email, password);
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return student;
    }
    public static String Create(String entity){
        String surl = StudentFacadeREST + "create";
        HttpURLConnection httpURLConnection;
        String result = "";
        OutputStream os = null;
        try{
            URL url = new URL(surl);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setConnectTimeout(5000);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("ser-Agent", "Fiddler");
            httpURLConnection.setRequestProperty("Content-Type", "application/json");
            os = httpURLConnection.getOutputStream();
            os.write(entity.getBytes());
            os.flush();

        }catch (Exception e){
            e.printStackTrace();
        }
        finally {
            try {
                if (os != null) {
                    os.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }
}
