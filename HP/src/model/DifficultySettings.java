package model;

/**
 * Created by Felix on 18.01.2016.
 */
public class DifficultySettings {

    int goblinStr;
    int goblinHPmax;
    int wolfStr;
    int wolfHPmax;
    int skelettStr;
    int skelettHPmax;
    int bossHPmax;
    int bossStr;

    public DifficultySettings(String difficulty){
        switch(difficulty){
            case "easy": goblinStr=1; goblinHPmax=10;
                        wolfStr=2; wolfHPmax=10;
                        skelettStr=0; skelettHPmax=10;
                        bossStr=4; bossHPmax=20;break;
            case "medium": goblinStr=2; goblinHPmax=15;
                        wolfStr=3; wolfHPmax=15;
                        skelettStr=0; skelettHPmax=15;
                        bossStr=6; bossHPmax=30;break;
            case "hard": goblinStr=3; goblinHPmax=20;
                        wolfStr=4; wolfHPmax=20;
                        skelettStr=0; skelettHPmax=20;
                         bossStr=8; bossHPmax=40;break;
        }
    }


    public int getGoblinHPmax() {
        return goblinHPmax;
    }

    public int getGoblinStr() {
        return goblinStr;
    }

    public int getSkelettHPmax() {
        return skelettHPmax;
    }

    public int getSkelettStr() {
        return skelettStr;
    }

    public int getWolfStr() {
        return wolfStr;
    }

    public int getWolfHPmax() {
        return wolfHPmax;
    }

    public int getBossHPmax() {
        return bossHPmax;
    }

    public int getBossStr() {
        return bossStr;
    }
}
