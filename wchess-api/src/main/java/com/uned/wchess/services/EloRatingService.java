package com.uned.wchess.services;

import java.util.HashMap;

public interface EloRatingService {
	HashMap<Integer, Integer> calculateMultiplayer(HashMap<Integer, Integer> usersRating, int userIdWinner);
	int calculate2PlayersRating(int player1Rating, int player2Rating, String outcome);
}