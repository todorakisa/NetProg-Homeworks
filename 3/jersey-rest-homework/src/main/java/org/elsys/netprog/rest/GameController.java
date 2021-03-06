package org.elsys.netprog.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;


@Path("/game")
public class GameController {
	
	private boolean flag = false;
	private List<BulsAndCows> games = new ArrayList<BulsAndCows>();
	
	@POST
	@Path("/startGame")
	@Produces(value={MediaType.APPLICATION_JSON})
	public Response startGame() throws URISyntaxException{
		String gamesId = UUID.randomUUID().toString();
		BulsAndCows obj = new BulsAndCows(gamesId,generator());
		games.add(obj);
		return Response.created(new URI("/games")).status(201).entity(gamesId).build();
	}
	
	@PUT
	@Path("/guess/{id}/{guess}")
	@Produces(value={MediaType.APPLICATION_JSON})
	public Response guess(@PathParam("id") String gameId, @PathParam("guess") String guess) throws Exception{
		if(findGame(gameId) == null)return Response.status(404).build();
		if(check(guess) == false) return Response.status(400).build();
		findGame(gameId).checkGame(guess);
		String zaqvka = findGame(gameId).returnjobj().toString();
		return Response.status(200).entity(zaqvka).build();
	}
	
	@GET
	@Path("/games")
	@Produces(value={MediaType.APPLICATION_JSON})
	public Response getGames() {
		JSONArray arr = new JSONArray();
		for ( BulsAndCows c : games){
			arr.add(c.returnjobj());
		}
		return Response.status(200).entity(arr.toString()).build();
	}
	
	public String generator(){
		while(true){
			String num = String.format("%04d", new Random().nextInt(10000));
			if(num == "10000") continue;
			if(Integer.parseInt(num)< 1000)continue;
			char a = num.toCharArray()[0];
			char b = num.toCharArray()[1];
			char c = num.toCharArray()[2];
			char z = num.toCharArray()[3];
			if(a == b || a == c || a == z) continue;
			if(b == c || b == z)continue;
			if(c == z)continue;
			return num;
		}
	}
	
	public boolean check(String num){
		if(num == "10000") return false;
		if(Integer.parseInt(num)< 1000)return false;
		char a = num.toCharArray()[0];
		char b = num.toCharArray()[1];
		char c = num.toCharArray()[2];
		char z = num.toCharArray()[3];
		if(a == b || a == c || a == z) return false;
		if(b == c || b == z)return false;
		if(c == z)return false;
		return true;
	}
	
	public BulsAndCows findGame(String id){
		for ( BulsAndCows c : games){
			if(c.getId().equals(id)) return c;
		}
		return null;
	}
	
}
