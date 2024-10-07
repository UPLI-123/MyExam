package cn.lch.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import javax.net.ssl.SSLException;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.*;

/**
 * @param :
  * @return
 * @author
 * @description 封装好的face++ 工具类
 * @date  2022年4月12日19:28:54
 */
public class FaceUntils {
    private static HashMap<String, String> map = new HashMap<>();
    static {
        // 读取配置文件当中的内容
        Properties pro = new Properties() ;
        // 将文件读入到输入流中
        InputStream resourceAsStream = FaceUntils.class.getResourceAsStream("/api.properties");
        try {
            pro.load(resourceAsStream);
            map.put("api_key", pro.getProperty("api_key"));
            map.put("api_secret", pro.getProperty("api_secret"));
            map.put("display_name",pro.getProperty("display_name")) ;
            map.put("outer_id",pro.getProperty("display_name")) ;
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("配置文件读取错误");
        }
    }
//    获取人脸库信息的工具
    public static boolean getSetDetail(){
        String url = "https://api-cn.faceplusplus.com/facepp/v3/faceset/getdetail";
        HashMap<String, byte[]> byteMap = new HashMap<>();
        try {
            byte[] bacd = post(url, map, null);
            String str = new String(bacd);
            if(str.indexOf("error_message")!=-1){
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  true ;
    }


//     创建人脸库的API
    public static boolean createFaceset(){
        String url = "https://api-cn.faceplusplus.com/facepp/v3/faceset/create";
        HashMap<String, byte[]> byteMap = new HashMap<>();
        try {
            byte[] bacd = post(url, map, null);
            String str = new String(bacd);
            if(str.indexOf("error_message")!=-1){
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  true ;
    }
    //  创建人脸比对库
    public static boolean compare(String face_token1,String face_token2){
        String url = "https://api-cn.faceplusplus.com/facepp/v3/compare";
        HashMap<String, byte[]> byteMap = new HashMap<>();
        try {
//             将两个人脸比对的参数添加进去
            map.put("face_token1",face_token1) ;
            map.put("face_token2",face_token2) ;
            byte[] bacd = post(url, map, null);
            String str = new String(bacd);
            System.out.println(str);
            if(str.indexOf("error_message")!=-1){
                return false;
            }
            JSONObject jsonObject = JSONObject.parseObject(str);
            JSONObject thresholdsObj = (JSONObject) jsonObject.get("thresholds");
            double le5 = thresholdsObj.getDoubleValue("1e-5");
            System.out.println(le5);
//            JSONArray resArr = (JSONArray) jsonObject.get("results");
//            double confidence = (double) jsonObj.get("confidence");
            double confidence = jsonObject.getDoubleValue("confidence");
            System.out.println(confidence);
            if(confidence>=le5){
                    return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  false ;
    }

//     人脸搜索功能
    public static boolean search(String faceToken){
        String url = "https://api-cn.faceplusplus.com/facepp/v3/search";
        HashMap<String, byte[]> byteMap = new HashMap<>();
        map.put("face_token",faceToken) ;
        try {
            byte[] bacd = post(url, map, null);
            String str = new String(bacd);
            if(str.indexOf("error_message")!=-1){
                return false;
            }
            JSONObject jsonObject = JSONObject.parseObject(str);
            JSONObject thresholdsObj = (JSONObject) jsonObject.get("thresholds");
            double le5 = thresholdsObj.getDoubleValue("le-5");
            JSONArray resArr = (JSONArray) jsonObject.get("results");
            if(resArr!=null && resArr.size()>=1){
                JSONObject jobj = (JSONObject) resArr.get(0);
                double confidence = jobj.getDoubleValue("confidence");
                if(confidence>=le5){
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return  false ;
    }


//     获得人脸
    public static boolean addFace(String facetoken){
        boolean setDetail = getSetDetail(); // 先去查询是否有指定的人脸集合，如果没有就创建
        if(!setDetail){
            boolean faceset = createFaceset();
            if(!faceset){
                return false;
            }

        }
        String url = "https://api-cn.faceplusplus.com/facepp/v3/faceset/addface";
        HashMap<String, byte[]> byteMap = new HashMap<>();
        map.put("face_tokens",facetoken) ;
        try {
            byte[] bacd = post(url, map, null);
            String str = new String(bacd);
            if(str.indexOf("error_message")!=-1){
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  true ;
    }

//     人脸检测
    public static String getFaceToken(File file){
        byte[] buff = getBytesFromFile(file);
        String url = "https://api-cn.faceplusplus.com/facepp/v3/detect" ;
        HashMap<String, byte[]> byteMap = new HashMap<>();
        byteMap.put("image_file", buff);
        try{
            byte[] bacd = post(url, map, byteMap);
            String str = new String(bacd);
            if(str.indexOf("error_message")!=-1){
                System.out.println("发生了错误");
                return null ;
            }
//            将字符串解析成json 串
            JSONObject jsonObj = JSONObject.parseObject(str);
            int face_num = jsonObj.getIntValue("face_num");
            if(face_num == 1){
                JSONArray faces = (JSONArray) jsonObj.get("faces");
                JSONObject face = (JSONObject) faces.get(0);
                String face_token = face.getString("face_token");
//                System.out.println(face_token);/
                return face_token ;
            }
        }catch (Exception e) {
            e.printStackTrace();
        }

        return null ;
    }

    public static String getFaceToken(byte[] buff){
        String url = "https://api-cn.faceplusplus.com/facepp/v3/detect" ;
        HashMap<String, byte[]> byteMap = new HashMap<>();
        byteMap.put("image_file", buff);
        try{
            byte[] bacd = post(url, map, byteMap);
            String str = new String(bacd);
            if(str.indexOf("error_message")!=-1){
                System.out.println("发生了错误");
                return null ;
            }
//            将字符串解析成json 串
            JSONObject jsonObj = JSONObject.parseObject(str);
            int face_num = jsonObj.getIntValue("face_num");
            if(face_num == 1){
                JSONArray faces = (JSONArray) jsonObj.get("faces");
                JSONObject face = (JSONObject) faces.get(0);
                String face_token = face.getString("face_token");
//                System.out.println(face_token);/
                return face_token ;
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null ;
    }

    public static void main(String[] args) throws Exception{

        File file = new File("C:\\Users\\17894\\Desktop\\mmexport1632380018622.jpg");
        System.out.println(getSetDetail());
    }

//      java 调用face++ 的接口文档

    private final static int CONNECT_TIME_OUT = 30000;
    private final static int READ_OUT_TIME = 50000;
    private static String boundaryString = getBoundary();
    protected static byte[] post(String url, HashMap<String, String> map, HashMap<String, byte[]> fileMap) throws Exception {
        HttpURLConnection conne;
        URL url1 = new URL(url);
        conne = (HttpURLConnection) url1.openConnection();
        conne.setDoOutput(true);
        conne.setUseCaches(false);
        conne.setRequestMethod("POST");
        conne.setConnectTimeout(CONNECT_TIME_OUT);
        conne.setReadTimeout(READ_OUT_TIME);
        conne.setRequestProperty("accept", "*/*");
        conne.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundaryString);
        conne.setRequestProperty("connection", "Keep-Alive");
        conne.setRequestProperty("user-agent", "Mozilla/4.0 (compatible;MSIE 6.0;Windows NT 5.1;SV1)");
        DataOutputStream obos = new DataOutputStream(conne.getOutputStream());
        Iterator iter = map.entrySet().iterator();
        while(iter.hasNext()){
            Map.Entry<String, String> entry = (Map.Entry) iter.next();
            String key = entry.getKey();
            String value = entry.getValue();
            obos.writeBytes("--" + boundaryString + "\r\n");
            obos.writeBytes("Content-Disposition: form-data; name=\"" + key
                    + "\"\r\n");
            obos.writeBytes("\r\n");
            obos.writeBytes(value + "\r\n");
        }
        if(fileMap != null && fileMap.size() > 0){
            Iterator fileIter = fileMap.entrySet().iterator();
            while(fileIter.hasNext()){
                Map.Entry<String, byte[]> fileEntry = (Map.Entry<String, byte[]>) fileIter.next();
                obos.writeBytes("--" + boundaryString + "\r\n");
                obos.writeBytes("Content-Disposition: form-data; name=\"" + fileEntry.getKey()
                        + "\"; filename=\"" + encode(" ") + "\"\r\n");
                obos.writeBytes("\r\n");
                obos.write(fileEntry.getValue());
                obos.writeBytes("\r\n");
            }
        }
        obos.writeBytes("--" + boundaryString + "--" + "\r\n");
        obos.writeBytes("\r\n");
        obos.flush();
        obos.close();
        InputStream ins = null;
        int code = conne.getResponseCode();
        try{
            if(code == 200){
                ins = conne.getInputStream();
            }else{
                ins = conne.getErrorStream();
            }
        }catch (SSLException e){
            e.printStackTrace();
            return new byte[0];
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buff = new byte[4096];
        int len;
        while((len = ins.read(buff)) != -1){
            baos.write(buff, 0, len);
        }
        byte[] bytes = baos.toByteArray();
        ins.close();
        return bytes;
    }
    private static String getBoundary() {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for(int i = 0; i < 32; ++i) {
            sb.append("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789_-".charAt(random.nextInt("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789_".length())));
        }
        return sb.toString();
    }
    private static String encode(String value) throws Exception{
        return URLEncoder.encode(value, "UTF-8");
    }

    public static byte[] getBytesFromFile(File f) {
        if (f == null) {
            return null;
        }
        try {
            FileInputStream stream = new FileInputStream(f);
            ByteArrayOutputStream out = new ByteArrayOutputStream(1000);
            byte[] b = new byte[1000];
            int n;
            while ((n = stream.read(b)) != -1)
                out.write(b, 0, n);
            stream.close();
            out.close();
            return out.toByteArray();
        } catch (IOException e) {
        }
        return null;
    }
}
