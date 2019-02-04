package util;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import network.messages.SpielInfo;
import network.messages.SpielerInfo;
import network.server.connection.threads.ServerHandler;

public class ArrayConverter {

	public static String[] nickArray( List<ServerHandler> nicks ) {

		int arraySize = nicks.size();
		String[] result = new String[arraySize];

		for (int i = 0; i < nicks.size(); i++) {

			String nick = nicks.get(i).getNick();
			result[i] = nick;
		}

		return result;
	}

	public static SpielerInfo[] spielerInfoToArray( List<SpielerInfo> players ) {

		int arraySize = players.size();
		SpielerInfo[] result = new SpielerInfo[arraySize];

		for (int i = 0; i < players.size(); i++) {

			String name = players.get(i).getName();
			String character_class = players.get(i).getCharacter_class();
			int x = players.get(i).getStats().getPlayer_positions().getX();
			int y = players.get(i).getStats().getPlayer_positions().getY();

			SpielerInfo spielerinfo = new SpielerInfo(name, character_class, x, y);
			result[i] = spielerinfo;
		}

		return result;
	}

	public static SpielInfo[] spielInfoToArray( List<SpielInfo> games ) {

		int arraySize = games.size();
		SpielInfo[] result = new SpielInfo[arraySize];

		if( arraySize == 0 ) {
			return null;
		} else {
			for (int i = 0; i < games.size(); i++) {
				SpielInfo x;
				
				int playersCount = games.get(i).getPlayersCount();
				int gameID = games.get(i).getGameID();
				String state = games.get(i).getGamestate();
				SpielerInfo[] player = games.get(i).getPlayers();
				int depth = games.get(i).getDepth();
				int diff = games.get(i).getDifficulty();
				
				x = new SpielInfo(gameID, state, player, depth, diff);
				x.setPlayersCount(playersCount);
				result[i] = x;
			}
		}

		return result;
	}

	public static ObservableList<SpielerInfo> ArrayToSpielerInfo( SpielerInfo[] players ) {

		ObservableList<SpielerInfo> result = FXCollections.observableArrayList();
		int length = players.length;
		if( length == 0) {
			return null;
		} else {
			for (int i = 0; i < length; i++) {
				SpielerInfo info;

				String name = players[i].getName();
				String char_class = players[i].getCharacter_class();
				int x = players[i].getStats().getPlayer_positions().getX();
				int y = players[i].getStats().getPlayer_positions().getY();

				info = new SpielerInfo(name, char_class, x, y);

				result.add(info);

			}
			return result;
		}
	}

	public static ObservableList<SpielInfo> ArrayToSpielInfo( SpielInfo[] games ) {

		ObservableList<SpielInfo> result = FXCollections.observableArrayList();

		for (int i = 0; i < games.length; i++) {
			result.add(games[i]);
		}

		return result;
	}
}
