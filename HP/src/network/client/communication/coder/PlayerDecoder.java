package network.client.communication.coder;

import com.sun.media.jfxmedia.events.NewFrameEvent;
import com.sun.media.jfxmedia.events.PlayerStateListener;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import model.ClientModel;
import model.entity.Monster;
import model.entity.Player;
import model.entity.monster.Boss;
import model.entity.monster.Goblin;
import model.entity.monster.Skelett;
import model.entity.monster.Wolf;
import model.entity.monster.enemiesHP.*;
import model.entity.player.*;
import model.entity.player.playersHP.*;
import model.util.Field;
import model.util.Position;
import network.messages.SpielerInfo;
import network.messages.Spielerposition;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Felix on 07.01.2016.
 */
public class PlayerDecoder {
    private List<Player> playerList = new LinkedList<Player>();
    private List<Monster> monsterList = new LinkedList<>();
    private ItemWeaponDecoder itemWeaponDecoder = new ItemWeaponDecoder();
    private ClientModel clientModel;
    private BooleanProperty youreDead = new SimpleBooleanProperty();
    private BooleanProperty bossDead = new SimpleBooleanProperty();

    public PlayerDecoder(SpielerInfo[] spielerInfos) {
        decodePlayers(spielerInfos);


    }

    public void doFirstSteps(ClientModel clientModel, SpielerInfo[] spielerInfos) {
        setModel(clientModel);
        playerUpdate(spielerInfos, clientModel);
        monsterUpdate(spielerInfos);
        setMonsterinField(clientModel);
        setIdent(spielerInfos);
        setModelMonster(clientModel);
    }

    public void floorChange(SpielerInfo[] spielerInfos, ClientModel clientModel) {
        playerList.clear();
        monsterList.clear();
        decodePlayers(spielerInfos);
        playerUpdate(spielerInfos, clientModel);
        setModel(clientModel);
        setModelMonster(clientModel);
        monsterUpdate(spielerInfos);

    }

    public void deletePlayer(String name) {
        for (Player player : playerList) {
            if (player.getNick().equals(name)) {
                clientModel.getCurrentFloor().getFieldAtPos(player.getPos()).setContent(Field.cnt.FLOOR);
                player.setAlive(false);
                //       playerList.remove(playerList.indexOf(player));
            }
        }
    }


    public void decodePlayers(SpielerInfo[] spielerInfos) {
        int i = 0;
        for (SpielerInfo spielerInfo : spielerInfos) {
            i++;
            switch (spielerInfo.getCharacter_class()) {
                case ("Warrior"): {
                    Warrior warrior = new Warrior();
                    warrior.setNick(spielerInfo.getName());
                    warrior.setIdent(getRightIdent(i));
                    playerList.add(warrior);

                    break;


                }
                case ("Student"): {
                    Student student = new Student();
                    student.setNick(spielerInfo.getName());
                    student.setIdent(getRightIdent(i));
                    playerList.add(student);

                    break;
                }
                case ("Tourist"): {
                    Tourist tourist = new Tourist();
                    tourist.setNick(spielerInfo.getName());
                    tourist.setIdent(getRightIdent(i));
                    playerList.add(tourist);

                    break;

                }
                case ("Mage"): {
                    Mage mage = new Mage();
                    mage.setNick(spielerInfo.getName());
                    mage.setIdent(getRightIdent(i));
                    playerList.add(mage);

                    break;
                }
                case ("Professor"): {
                    Professor professor = new Professor();
                    professor.setNick(spielerInfo.getName());
                    professor.setIdent(getRightIdent(i));
                    playerList.add(professor);

                    break;
                }
                case ("Ranger"): {
                    Ranger ranger = new Ranger();
                    ranger.setNick(spielerInfo.getName());
                    ranger.setIdent(getRightIdent(i));
                    playerList.add(ranger);

                    break;
                }
                case ("skelett"): {
                    Skelett skelett = new Skelett();
                    skelett.setNick(spielerInfo.getName());
                    monsterList.add(skelett);
                    break;

                }
                case ("goblin"): {
                    Goblin goblin = new Goblin();
                    goblin.setNick(spielerInfo.getName());
                    monsterList.add(goblin);
                    break;

                }
                case ("wolf"): {
                    Wolf wolf = new Wolf();
                    wolf.setNick(spielerInfo.getName());
                    monsterList.add(wolf);
                    break;
                }
                case ("boss"): {
                    Boss boss = new Boss();
                    boss.setNick(spielerInfo.getName());
                    monsterList.add(boss);
                    break;

                }
                case ("Dumbledore"): {
                    Dumbledore dumbledore = new Dumbledore();
                    dumbledore.setNick(spielerInfo.getName());
                    dumbledore.setIdent(getRightIdent(i));
                    playerList.add(dumbledore);
                    break;
                }
                case ("Ginny"): {
                    Ginny ginny = new Ginny();
                    ginny.setNick(spielerInfo.getName());
                    ginny.setIdent(getRightIdent(i));
                    playerList.add(ginny);
                    break;

                }
                case ("Hagrid"): {
                    Hagrid hagrid = new Hagrid();
                    hagrid.setNick(spielerInfo.getName());
                    hagrid.setIdent(getRightIdent(i));
                    playerList.add(hagrid);
                    break;


                }
                case ("Harry"): {
                    Harry harry = new Harry();
                    harry.setNick(spielerInfo.getName());
                    harry.setIdent(getRightIdent(i));
                    playerList.add(harry);
                    break;

                }
                case ("Hermine"): {
                    Hermine hermine = new Hermine();
                    hermine.setNick(spielerInfo.getName());
                    hermine.setIdent(getRightIdent(i));
                    playerList.add(hermine);
                    break;

                }
                case ("Luna"): {
                    Luna luna = new Luna();
                    luna.setNick(spielerInfo.getName());
                    luna.setIdent(getRightIdent(i));
                    playerList.add(luna);
                    break;

                }
                case ("Moody"): {
                    Moody moody = new Moody();
                    moody.setNick(spielerInfo.getName());
                    moody.setIdent(getRightIdent(i));
                    playerList.add(moody);
                    break;

                }
                case ("Neville"): {
                    Neville neville = new Neville();
                    neville.setNick(spielerInfo.getName());
                    neville.setIdent(getRightIdent(i));
                    playerList.add(neville);
                    break;


                }
                case ("Ron"): {
                    Ron ron = new Ron();
                    ron.setNick(spielerInfo.getName());
                    ron.setIdent(getRightIdent(i));
                    playerList.add(ron);
                    break;


                }
                case ("Snape"): {
                    Snape snape = new Snape();
                    snape.setNick(spielerInfo.getName());
                    snape.setIdent(getRightIdent(i));
                    playerList.add(snape);
                    break;

                }
                case ("bellatrix"): {
                    Bellatrix bellatrix = new Bellatrix();
                    bellatrix.setNick(spielerInfo.getName());
                    monsterList.add(bellatrix);
                    break;
                }
                case ("draco"): {
                    Draco draco = new Draco();
                    draco.setNick(spielerInfo.getName());
                    monsterList.add(draco);
                    break;
                }
                case ("lucius"): {
                    Lucius lucius = new Lucius();
                    lucius.setNick(spielerInfo.getName());
                    monsterList.add(lucius);
                    break;
                }
                case ("voldemort"): {
                    Voldemort voldemort = new Voldemort();
                    voldemort.setNick(spielerInfo.getName());
                    monsterList.add(voldemort);
                    break;
                }
                case ("dementor"):{
                    Dementor dementor = new Dementor();
                    dementor.setNick(spielerInfo.getName());
                    monsterList.add(dementor);
                }

            }
        }

    }

    public Field.cnt getRightIdent(int number) {
        switch (number) {
            case (1): {
                return Field.cnt.PLAYER1;
            }
            case (2): {
                return Field.cnt.PLAYER2;
            }
            case (3): {
                return Field.cnt.PLAYER3;
            }
            case (4): {
                return Field.cnt.PLAYER4;
            }
            case (5): {
                return Field.cnt.PLAYER5;
            }
            case (6): {
                return Field.cnt.PLAYER6;
            }
            case (7): {
                return Field.cnt.PLAYER7;
            }
            case (8): {
                return Field.cnt.PLAYER8;
            }
            default:
                return Field.cnt.WOLF;

        }
    }

    public void setMonsterinField(ClientModel clientModel) {
        for (Monster monster : clientModel.getMonsters()) {
            clientModel.getCurrentFloor().getFieldAtPos(monster.getPos()).setContent(monster.getIdent());
        }

    }

    public void playerUpdate(SpielerInfo[] spielerInfos, ClientModel clientModel) {
        this.clientModel = clientModel;
        for (SpielerInfo spielerInfo : spielerInfos) {
            for (Player player : playerList) {
                if (spielerInfo.getName().equals(player.getNick())) {
                    if (player.getHpcur() > 0) {
                        player.setPos(decodePosition(spielerInfo.getStats().getPlayer_positions()));
                        if (player.getHpcur() != spielerInfo.getStats().getHpcur()) {
                            player.setHpcur(spielerInfo.getStats().getHpcur());
                        }

                        player.setExp(spielerInfo.getStats().getExp());
                        setItem(player, spielerInfo);
                        player.setLevel(spielerInfo.getStats().getLevel());
                        player.setStr(spielerInfo.getStats().getStr());
                        player.setWis(spielerInfo.getStats().getWis());
                        player.setHpmax(spielerInfo.getStats().getHpmax());

                    } else {
                        player.setAlive(false);
                        if (player.equals(clientModel.getSelfPlayer())) {
                            youreDead.set(true);
                        }

                    }
                }
            }
        }
    }

    public void monsterUpdate(SpielerInfo[] spielerInfos) {
        for (SpielerInfo spielerInfo : spielerInfos) {
            for (Monster monster : monsterList) {
                if (spielerInfo.getName().equals(monster.getNick())) {
                    if (spielerInfo.getStats().getHpcur() > 0) {
                        monster.setDex(spielerInfo.getStats().getDex());
                        monster.setExp(spielerInfo.getStats().getExp());
                        monster.setHpcur(spielerInfo.getStats().getHpcur());
                        monster.setPos(decodePosition(spielerInfo.getStats().getPlayer_positions()));
                        monster.setLevel(spielerInfo.getStats().getLevel());
                        monster.setHpmax(spielerInfo.getStats().getHpmax());


                    } else {
                        if (monster.getPos().getX() == 0 && monster.getPos().getY() == 0) {

                        } else {
                            monster.setAlive(false);
                            if (!monster.isOnDoor()) {
                                clientModel.getCurrentFloor().getFieldAtPos(monster.getPos()).setContent(Field.cnt.FLOOR);
                            } else {
                                clientModel.getCurrentFloor().getFieldAtPos(monster.getPos()).setContent(Field.cnt.DOOROPEN);
                            }
                            if (monster.getIdent().equals(Field.cnt.BOSS)) {
                                bossDead.set(true);
                            } else if (monster.getIdent().equals(Field.cnt.VOLDEMORT)) {
                                bossDead.set(true);
                            }

                            monster.setPos(new Position(0, 0));
                        }
                    }
                }
            }
        }
    }

    public void setIdent(SpielerInfo[] spielerInfos) {
        for (SpielerInfo spielerInfo : spielerInfos) {
            for (Player player : playerList) {
                if (spielerInfo.getName().equals(player.getNick())) {
                    clientModel.getCurrentFloor().getFieldAtPos(player.getPos()).setContent(player.getIdent());
                }

            }
        }
    }


    public Position decodePosition(Spielerposition spielerposition) {
        Position position = new Position(spielerposition.getX(), spielerposition.getY());
        return position;
    }

    public void setItem(Player player, SpielerInfo spielerInfo) {

        if (!spielerInfo.getItems().equals("") && !spielerInfo.getItems().equals(null)) {
            player.setPotion(itemWeaponDecoder.decodeItemWeapon(spielerInfo.getItems()));
        } else {
            player.setPotion(null);
        }
        if (!spielerInfo.getWeapon().equals("") && !spielerInfo.getWeapon().equals(null)) {
            player.setWeapon(itemWeaponDecoder.decodeItemWeapon(spielerInfo.getWeapon()));
        } else {
            player.setWeapon(null);
        }
    }

    public void setModel(ClientModel clientModel) {
        for (Player player : playerList) {
            player.setClientModel(clientModel);
        }
    }

    public void setModelMonster(ClientModel clientModel) {
        for (Monster monster : monsterList) {
            monster.setClientModel(clientModel);
        }
    }


    public List<Player> getPlayerList() {
        return playerList;
    }

    public void setPlayerList(List<Player> playerList) {
        this.playerList = playerList;
    }

    public ItemWeaponDecoder getItemWeaponDecoder() {
        return itemWeaponDecoder;
    }

    public void setItemWeaponDecoder(ItemWeaponDecoder itemWeaponDecoder) {
        this.itemWeaponDecoder = itemWeaponDecoder;
    }

    public List<Monster> getMonsterList() {
        return monsterList;
    }

    public void setMonsterList(List<Monster> monsterList) {
        this.monsterList = monsterList;
    }

    public boolean getYoureDead() {
        return youreDead.get();
    }

    public BooleanProperty youreDeadProperty() {
        return youreDead;
    }

    public void setYoureDead(boolean youreDead) {
        this.youreDead.set(youreDead);
    }

    public boolean getBossDead() {
        return bossDead.get();
    }

    public BooleanProperty bossDeadProperty() {
        return bossDead;
    }

    public void setBossDead(boolean bossDead) {
        this.bossDead.set(bossDead);
    }
}
