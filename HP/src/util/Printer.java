package util;

import network.game.GameInfo;
import network.messages.SpielerInfo;

import java.util.List;

/**
 * Created by Jenny on 18.01.2016.
 */
public class Printer {

    public static void printListSpieler(List<SpielerInfo> list){
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i).getName());
        }
    }

    public static void printList(List<GameInfo> list){
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i).getGameID());
        }
    }


}
