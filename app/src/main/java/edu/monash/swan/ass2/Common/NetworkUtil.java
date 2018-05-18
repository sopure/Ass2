package edu.monash.swan.ass2.Common;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

import edu.monash.swan.ass2.Bean.Friendship;
import edu.monash.swan.ass2.Bean.Student;

public class NetworkUtil {
    private final static String StudentFacadeREST = "http://172.16.120.106:8080/Ass1/webresources/com.pojo.student/";
    private final static String FriendshipFacadeREST = "http://172.16.120.106:8080/Ass1/webresources/com.pojo.friendship/";

    public static String SendGet(String url) {
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
                    String sid = js.getString("sid");
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
                    student = new Student(sid,firstName, surname, dob, gender, course, studyMode, address, suburb, nationality, language, favouriteSport, favouriteMovie, favouriteUnit, currentJob, email, password);
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return student;
    }
    public static String Create(String entity){
        String surl = StudentFacadeREST + "createStu";
        HttpURLConnection httpURLConnection;
        String result = "";
        OutputStreamWriter out = null;
        BufferedReader reader = null;
        try{
            URL url = new URL(surl);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("connection", "keep-alive");
            httpURLConnection.setUseCaches(false);//设置不要缓存
            httpURLConnection.setInstanceFollowRedirects(true);
            httpURLConnection.setRequestProperty("Content-Type", "application/json");
            httpURLConnection.connect();
            out = new OutputStreamWriter(httpURLConnection.getOutputStream());
            out.write(entity);
            out.flush();
            //读取响应
            reader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            String lines;
            while ((lines = reader.readLine()) != null) {
                lines = new String(lines.getBytes(), "utf-8");
                result+=lines;
            }
            reader.close();
            // 断开连接
            httpURLConnection.disconnect();
        }catch (Exception e){
            System.out.println("发送 POST 请求出现异常！"+e);
            e.printStackTrace();
        }
        finally {
            try{
                if(out!=null){
                    out.close();
                }
                if(reader!=null){
                    reader.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result;
    }


    public static String getId(String myId) {
        final String methodPath = "findByMonashEmail/" + myId;
//initialise
        URL url = null;
        HttpURLConnection conn = null;
        String textResult = "";
//Making HTTP request
        try {
            url = new URL(StudentFacadeREST + methodPath);
//open the connection
            conn = (HttpURLConnection) url.openConnection();
//set the timeout
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
//set the connection method to GET
            conn.setRequestMethod("GET");
//add http headers to set your response type to json
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
//Read the response
            Scanner inStream = new Scanner(conn.getInputStream());
//read the input steream and store it as string
            while (inStream.hasNextLine()) {
                textResult += inStream.nextLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
        Log.d("RestClient", "获取到的学生数据是：" + textResult);
        return textResult;
    }
    public static Bitmap getHttpBitmap(String url){
        URL myFileURL;
        Bitmap bitmap=null;
        try{
            myFileURL = new URL(url);
            //获得连接
            HttpURLConnection conn=(HttpURLConnection)myFileURL.openConnection();
            //设置超时时间为6000毫秒，conn.setConnectionTiem(0);表示没有时间限制
            conn.setConnectTimeout(6000);
            //连接设置获得数据流
            conn.setDoInput(true);
            //不使用缓存
            conn.setUseCaches(false);
            //这句可有可无，没有影响
            //conn.connect();
            //得到数据流
            InputStream is = conn.getInputStream();
            //解析得到图片
            bitmap = BitmapFactory.decodeStream(is);
            //关闭数据流
            is.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return bitmap;
    }

    public static int updateProfile(Integer id, Student student) {
        URL url = null;
        HttpURLConnection conn = null;
        final String methodPath =  String.valueOf(id) ;
        try {
            Gson gson = new Gson();
            String studentJson = gson.toJson(student);
            url = new URL(StudentFacadeREST + methodPath);
            conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("PUT");
            conn.setDoOutput(true);
            conn.setFixedLengthStreamingMode(studentJson.getBytes().length);
            conn.setRequestProperty("Content-Type", "application/json");
            PrintWriter out = new PrintWriter(conn.getOutputStream());
            out.print(studentJson);
            out.close();
            Log.i("error", new Integer(conn.getResponseCode()).toString());
            return conn.getResponseCode();
        } catch (Exception e) {
            e.printStackTrace();
            return 400;
        } finally {
            conn.disconnect();
        }
    }

    public static int createFriendship(Friendship fsp) {
        //initialise
        URL url = null;
        HttpURLConnection conn = null;
        final String methodPath = "";
        try {
            Gson gson = new Gson();
            String stringCourseJson = gson.toJson(fsp);
            url = new URL(FriendshipFacadeREST + methodPath);
//open the connection
            conn = (HttpURLConnection) url.openConnection();
//set the timeout
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
//set the connection method to POST
            conn.setRequestMethod("POST");
//set the output to true
            conn.setDoOutput(true);
//set length of the data you want to send
            conn.setFixedLengthStreamingMode(stringCourseJson.getBytes().length);
//add HTTP headers
            conn.setRequestProperty("Content-Type", "application/json");
//Send the POST out
            PrintWriter out = new PrintWriter(conn.getOutputStream());
            out.print(stringCourseJson);
            out.close();
            Log.i("error", new Integer(conn.getResponseCode()).toString());
            return conn.getResponseCode();
        } catch (Exception e) {
            e.printStackTrace();
            return 400;
        } finally {
            conn.disconnect();
        }
    }
    public static String getFriendship1(String myId) {
        final String methodPath = "findBySid/" + myId;
//initialise
        URL url = null;
        HttpURLConnection conn = null;
        String textResult = "";
//Making HTTP request
        try {
            url = new URL(FriendshipFacadeREST + methodPath);
//open the connection
            conn = (HttpURLConnection) url.openConnection();
//set the timeout
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
//set the connection method to GET
            conn.setRequestMethod("GET");
//add http headers to set your response type to json
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
//Read the response
            Scanner inStream = new Scanner(conn.getInputStream());
//read the input steream and store it as string
            while (inStream.hasNextLine()) {
                textResult += inStream.nextLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
        Log.d("RestClient", "获取到的friendship1数据是：" + textResult);
        return textResult;
    }

    public static String getFriendship2(String myId) {
        final String methodPath = "findByFid/" + myId;
//initialise
        URL url = null;
        HttpURLConnection conn = null;
        String textResult = "";
//Making HTTP request
        try {
            url = new URL(FriendshipFacadeREST + methodPath);
//open the connection
            conn = (HttpURLConnection) url.openConnection();
//set the timeout
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
//set the connection method to GET
            conn.setRequestMethod("GET");
//add http headers to set your response type to json
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
//Read the response
            Scanner inStream = new Scanner(conn.getInputStream());
//read the input steream and store it as string
            while (inStream.hasNextLine()) {
                textResult += inStream.nextLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
        Log.d("RestClient", "获取到的friendship2数据是：" + textResult);
        return textResult;
    }


    public static void
    deleteFriendship(Integer id){
        final String methodPath ="removeById/";
        URL url = null;
        HttpURLConnection conn = null;
        String txtResult="";
// Making HTTP request
        try {
            url = new URL(FriendshipFacadeREST + methodPath);
//open the connection
            conn = (HttpURLConnection) url.openConnection();
//set the connection method to GET
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
            conn.setRequestProperty("Accept", "application/json");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            JSONObject friendship = new JSONObject();
            friendship.put("id",id);
            DataOutputStream os =new DataOutputStream(conn.getOutputStream());
            String param =friendship.toString();
            os.writeBytes(param);


            os.flush();
            final Integer STATUS = conn.getResponseCode();
            if(STATUS==200||STATUS==204)
                System.out.println("success!");
            Log.i("delete_log",new Integer(conn.getResponseCode()).toString());
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
    }


    public static String searchFriends(String myId, String query) {
        final String methodPath = "findByAnyAttribute/" + myId+ "/" + query;
//initialise
        URL url = null;
        HttpURLConnection conn = null;
        String textResult = "";
//Making HTTP request
        try {
            url = new URL(StudentFacadeREST + methodPath);
//open the connection
            conn = (HttpURLConnection) url.openConnection();
//set the timeout
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
//set the connection method to GET
            conn.setRequestMethod("GET");
//add http headers to set your response type to json
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
//Read the response
            Scanner inStream = new Scanner(conn.getInputStream());
//read the input steream and store it as string
            while (inStream.hasNextLine()) {
                textResult += inStream.nextLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
        Log.d("RestClient", "匹配到的同好是：" + textResult);
        return textResult;
    }
}
