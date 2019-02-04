package network.client.communication.coder;

import model.ClientModel;
import model.entity.Entity;
import model.entity.Monster;
import model.entity.Player;
import model.util.Direction;
import model.util.Field;
import model.util.Position;
import network.messages.SpielzugMessage;

import java.util.List;

/**
 * Created by Adrian on 08.01.2016.
 */
public class MoveDecoder {

    ClientModel clientModel;
    private boolean stairDown =false ;

    public MoveDecoder(){

    }


    public void decodeMoves(SpielzugMessage[] spielzugMessages, List<Player> playerList, ClientModel clientModel) {
        this.clientModel = clientModel;
        for (SpielzugMessage spielZugMessage : spielzugMessages) {
            for (Player player : playerList) {
                if (spielZugMessage.getNick().equals(player.getNick())) {
                    switch (spielZugMessage.getAction()) {
                        case "move": {
                            chooseDirection(player,spielZugMessage.getDirection());

                            break;
                        }

                        case "attackMa": {
                            player.clientAttackma(spielZugMessage.getDirection());
                            break;//view Methode
                        }
                        case "attackIce":{
                            player.clientAttackIce(spielZugMessage.getDirection());
                            break;

                        }
                        case "attackWind":{
                            player.clientAttackWind(spielZugMessage.getDirection());
                            break;

                        }
                        case "attackRa": {
                            player.clientAttackra(spielZugMessage.getDirection());
                            //view Methode
                            break;
                        }
                        case "attackMe": {
                            player.clientAttackme(spielZugMessage.getDirection());
                            break;
                        }
                        case "use": {
                            if (stairDownCheck(player,spielZugMessage)) {
                                stairDown = true;
                            }
                            chooseUseDirection(player,spielZugMessage.getDirection());
                            break;
                        }
                        case "wait": {
                            //view Methode
                            break;
                        }
                    }
                }
            }


        }

    }
    public void chooseDirection(Entity player,String s){
        switch (s){

            case "a":{
                player.moveClient(Entity.direction.WEST);
                break;
            }
            case "w":{
                player.moveClient(Entity.direction.NORTH);
                break;
            }
            case "s":{
                player.moveClient(Entity.direction.SOUTH);
                break;
            }
            case "d":{
                player.moveClient(Entity.direction.EAST);
                break;
            }
        }
    }
    public Entity.direction getDirection(String s){
        switch (s){

            case "a":{
                return Entity.direction.WEST;
            }
            case "w":{
                return Entity.direction.NORTH;
            }
            case "s":{
                return Entity.direction.SOUTH;
            }
            case "d":{
                return Entity.direction.EAST;
            }
            default:
                return Entity.direction.NORTH;
        }
    }

    public void chooseUseDirection(Entity player,String s){
        switch (s){

            case "a":{
                player.useClient(Entity.direction.WEST);
                break;
            }
            case "w":{
                player.useClient(Entity.direction.NORTH);
                break;
            }
            case "s":{
                player.useClient(Entity.direction.SOUTH);
                break;
            }
            case "d":{
                player.useClient(Entity.direction.EAST);
                break;
            }
        }
    }
    public void decodeMonsterMoves(SpielzugMessage[] spielzugMessages, List<Monster> monsterList) {
        for (SpielzugMessage spielZugMessage : spielzugMessages) {
            for (Monster monster : monsterList) {
                if (spielZugMessage.getNick().equals(monster.getNick())) {


                    switch (spielZugMessage.getAction()) {
                        case "move": {
                            chooseDirection(monster,spielZugMessage.getDirection());
                            break;
                        }
                        case "attackMa": {
                            //view Methode
                            break;
                        }
                        case "attackRa": {
                            //view Methode
                            break;

                        }
                        case "attackMe": {
                            monster.setEntityDir(getDirection(spielZugMessage.getDirection()));
                            monster.clientAttack();
                            break;

                        }
                        case "use": {
                            break;



                        }
                        case "wait": {
                            //view Methode
                            break;

                        }
                    }
                }
            }


        }

    }

    public boolean stairDownCheck(Player player, SpielzugMessage message) {
        boolean result = false;

        if ((clientModel.getCurrentFloor().getFieldAtPos(player.calcNextField(message.getDirection())).getContent()).equals(Field.cnt.STAIRDOWN)) {
            result = true;
        }


        return result;


    }

    public ClientModel getClientModel() {
        return clientModel;
    }

    public void setClientModel(ClientModel clientModel) {
        this.clientModel = clientModel;
    }

    public boolean isStairDown() {
        return stairDown;
    }

    public void setStairDown(boolean stairDown) {
        this.stairDown = stairDown;
    }
}