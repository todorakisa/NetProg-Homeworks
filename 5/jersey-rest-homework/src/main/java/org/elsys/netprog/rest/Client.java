package org.elsys.netprog.rest;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.GetRequest;
import com.mashape.unirest.request.HttpRequest;
import com.mashape.unirest.request.HttpRequestWithBody;

public class Client {
	
	public static String getMd5(byte[] input) 
    { 
        try { 
        	
            MessageDigest md = MessageDigest.getInstance("MD5"); 
            byte[] messageDigest = md.digest(input); 
            BigInteger no = new BigInteger(1, messageDigest); 
            String hashtext = no.toString(16); 
            while (hashtext.length() < 32) { 
                hashtext = "0" + hashtext; 
            } 
            return hashtext; 
        }catch (NoSuchAlgorithmException e) { 
            throw new RuntimeException(e); 
        } 
    } 
	
	public static void main(String [] args)
	{
			try{
				HttpRequest request = Unirest.get("http://localhost:8080/jersey-rest-homework/game/hash");
				HttpResponse<JsonNode> jsonResponse = request.asJson();
				JSONObject myObj = jsonResponse.getBody().getObject();
				
				String serverHash = myObj.getString("hash");
				int len = myObj.getInt("length");

				byte[] bytes = new byte[len];
				SecureRandom random = new SecureRandom();
				
				System.out.println("Starting the game");
				while(true){
					random.nextBytes(bytes);
					String hash = getMd5(bytes);
					if(hash.equals(serverHash)){
						String encodedString = Base64.getUrlEncoder().encodeToString(bytes);
						HttpResponse<String> request2 = Unirest.post("http://localhost:8080/jersey-rest-homework/game/guess/" +  hash )
								.field("bytes", encodedString)
								.asString();
						int status = request2.getStatus();
						if(status == 200){
							System.out.println("guessed the hash: " + hash + " and the imput: " + bytes);
							break;
						}else {
							System.out.println("didn't guess becouse of server error: " + status);
						}
						break;
					}else{
						System.out.println("didn't guess the imput");
					}
				}
			}catch (UnirestException e) {
				e.printStackTrace();
			}
	}
}
