package org.elsys.netprog.rest;

import java.math.BigInteger;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;






import org.json.JSONObject;

import com.mashape.unirest.http.JsonNode;


@Path("/game")
public class GameController {
	
	
	private static int len = 1;	
	static SecureRandom random = new SecureRandom();
    static byte[] bytes = new byte[len];
    
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
    

    static int chek = 0;
    static String hash;
    
	@POST
	@Path("/guess/{hashG}")
	@Produces(value={MediaType.APPLICATION_JSON})
	public Response showhash(@PathParam("hashG") String hashG , @FormParam("bytes") String originalInput) throws Exception{
		String encodedString = Base64.getUrlEncoder().encodeToString(bytes);
		if (encodedString.equals(originalInput) && hash.equals(hashG)){
			chek = 0;
			return Response.status(200).build();
		}else{
			return Response.status(406).build();
		}
	}
	
	@GET
	@Path("/hash")
	@Produces(value={MediaType.APPLICATION_JSON})
	public Response hash() {
		if(chek == 0){
			random.nextBytes(bytes);
			hash = getMd5(bytes);
			chek++;
		}
		JSONObject values = new JSONObject();
		values.put("length", len);
		values.put("hash", hash);
		return Response.status(200).entity(values.toString()).build();
	}
}
