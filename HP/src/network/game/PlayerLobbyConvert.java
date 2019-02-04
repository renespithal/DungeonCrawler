package network.game;

import model.entity.Monster;
import model.entity.Player;
import network.messages.SpielerInfo;
import network.messages.Spielerwerte;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Felix on 19.01.2016.
 */
public class PlayerLobbyConvert {


    public PlayerLobbyConvert() {


    }


    public SpielerInfo[] infoConvert(List<Player> playerList, List<Monster> monsterList) {

        int x = playerList.size();
        int y = monsterList.size();
        SpielerInfo[] info = new SpielerInfo[x + y];

        for (int i = 0; i < x; i++) {

            info[i] = playerConvert(playerList.get(i));

        }
        for (int j = x; j < x + y; j++) {

            info[j] = monsterConvert(monsterList.get(j - x));
        }
        return info;
        //TODO könnte sein;
    }


    public SpielerInfo playerConvert(Player player) {
        Spielerwerte spielerwerte = new Spielerwerte(player.getPos().getX(), player.getPos().getY());
        System.out.println("................");
        System.out.println(player.getDex());
        System.out.println("................");
        spielerwerte.setDex(player.getDex());
        System.out.println("................");
        System.out.println(spielerwerte.getDex());
        System.out.println("................");

        System.out.println("OOOO................");
        System.out.println(player.getKlasse());
        System.out.println("OOOO................");
        spielerwerte.setExp((int) player.getExp());
        spielerwerte.setHpcur((int) player.getHpcur());
        spielerwerte.setHpmax((int) player.getHpmax());
        spielerwerte.setLevel(player.getLevel());
        spielerwerte.setStr(player.getStr());
        spielerwerte.setWis(player.getWis());
        SpielerInfo spielerInfo = new SpielerInfo(player.getNick(), player.getKlasse(), player.getPos().getX(), player.getPos().getY());
        spielerInfo.setStats(spielerwerte);
        if (player.getPotion() != null) {
            spielerInfo.setItems(player.getPotion().getName());
        }
        else{
            spielerInfo.setItems("");
        }
        if (player.getWeapon() != null) {
            spielerInfo.setWeapon(player.getWeapon().getName());
        }
        else{
            spielerInfo.setWeapon("");
        }


        return spielerInfo;
    }

    public SpielerInfo monsterConvert(Monster player) {

        Spielerwerte spielerwerte = new Spielerwerte(player.getPos().getX(), player.getPos().getY());
        spielerwerte.setDex(player.getDex());
        spielerwerte.setExp((int) player.getExp());
        spielerwerte.setHpcur((int) player.getHpcur());
        spielerwerte.setHpmax((int) player.getHpmax());
        spielerwerte.setLevel(player.getLevel());
        spielerwerte.setStr(player.getStr());
        spielerwerte.setWis(player.getWis());
        SpielerInfo spielerInfo = new SpielerInfo(player.getNick(), player.getKlasse(), player.getPos().getX(), player.getPos().getY());
        spielerInfo.setStats(spielerwerte);

        return spielerInfo;
    }


}
