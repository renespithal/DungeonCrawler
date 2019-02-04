package view.design.scenes.multiplayerGame;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import view.design.scenes.singleplayerLobby.SingleplayerGameLobbyPresenter;

import java.io.File;

/**
 * Created by Felix on 19.01.2016.
 */
public class ImageHolder2 {

    Image pictureBackground = new Image((getClass().getResource("/images/backBackground.jpg").toExternalForm()),120, 90, true,true);
    ImagePattern pictureBackgroundPattern = new ImagePattern(pictureBackground);
    Image levelBackground = new Image((getClass().getResource("/images/levelBackground.png").toExternalForm()),500, 208, true,true);
    ImagePattern levelBackgroundPattern = new ImagePattern(levelBackground);

    Image background = new Image((getClass().getResource("/images/buttonBackground.jpg").toExternalForm()), 1920, 1080, true, true);
    ImagePattern backgroundPattern = new ImagePattern(background);

    Image backgroundGO = new Image((getClass().getResource("/images/backgroundGO.jpg").toExternalForm()), 1920, 1080, true, true);
    ImagePattern backgroundGOPattern = new ImagePattern(backgroundGO);

    Image miniMapBackground = new Image((getClass().getResource("/images/map.png").toExternalForm()), 650, 300, true, true);
    ImagePattern miniMapBackgroundPattern = new ImagePattern(miniMapBackground);
    Image miniMapSmallBackground = new Image((getClass().getResource("/images/mapSmall.png").toExternalForm()), 650, 300, true, true);
    ImagePattern miniMapSmallBackgroundPattern = new ImagePattern(miniMapSmallBackground);

    Image sideBackground = new Image((getClass().getResource("/images/SideBackground.jpg").toExternalForm()), 1920, 1080, true, true);
    ImagePattern sideBackgroundPattern = new ImagePattern(sideBackground);


    Image buttonBackground= new Image((getClass().getResource("/images/buttonBackground.jpg").toExternalForm()), 1920, 1080, true, true);
    ImagePattern buttonBackgroundPattern = new ImagePattern(buttonBackground);


    Image exitButton = new Image((getClass().getResource("/images/ExitButton.png").toExternalForm()), 600,456, true, true);
    ImagePattern exitButtonPattern = new ImagePattern(exitButton);
    Image exitButtonSelected = new Image((getClass().getResource("/images/ExitButtonSelected.png").toExternalForm()), 600,456, true, true);
    ImagePattern exitButtonPatternSelected= new ImagePattern(exitButtonSelected);

    Image showStats = new Image((getClass().getResource("/images/showStats.png").toExternalForm()), 150,25, true, true);
    ImagePattern showStatsPattern = new ImagePattern(showStats);
    Image hideStats = new Image((getClass().getResource("/images/hideStats.png").toExternalForm()), 150,25, true, true);
    ImagePattern hideStatsPattern = new ImagePattern(hideStats);

    Image str = new Image((getClass().getResource("/images/strButton.png").toExternalForm()), 40,40, true, true);
    ImagePattern strPattern = new ImagePattern(str);
    Image dex = new Image((getClass().getResource("/images/dexButton.png").toExternalForm()), 40, 40, true, true);
    ImagePattern dexPattern = new ImagePattern(dex);
    Image wis = new Image((getClass().getResource("/images/wisButton.png").toExternalForm()), 40, 40, true, true);
    ImagePattern wisPattern = new ImagePattern(wis);
    Image health = new Image("images/Health.png", 40, 40, true, true);
    ImagePattern healthPattern = new ImagePattern(health);
    Image classPic = new Image("images/ClassPic.png", 40, 40, true, true);
    ImagePattern classPicPattern = new ImagePattern(classPic);

    Image moveButton = new Image((getClass().getResource("/images/Move3.png").toExternalForm()), 510,80,true,true);
    ImagePattern moveButtonPattern = new ImagePattern(moveButton);
    Image useButton = new Image((getClass().getResource("/images/Use5.png").toExternalForm()), 510,80,true,true);
    ImagePattern useButtonPattern = new ImagePattern(useButton);


    Image submitButtonLargeSelected = new Image((getClass().getResource("/images/SubmitButtonLargeSelected.png").toExternalForm()), 510,80,true,true);
    ImagePattern submitButtonLargePatternSelected = new ImagePattern(submitButtonLargeSelected);
    Image meleeButtonLargeSelected = new Image((getClass().getResource("/images/MeleeButtonLargeSelected.png").toExternalForm()), 510,80,true,true);
    ImagePattern meleeButtonLargePatternSelected = new ImagePattern(meleeButtonLargeSelected);
    Image rangeButtonSmallSelected = new Image((getClass().getResource("/images/RangeButtonSmallSelected.png").toExternalForm()), 255,80,true,true);
    ImagePattern rangeButtonSmallPatternSelected = new ImagePattern(rangeButtonSmallSelected);
    Image magicButtonSmallSelected = new Image((getClass().getResource("/images/MagicButtonSmallSelected.png").toExternalForm()), 255,80,true,true);
    ImagePattern magicButtonSmallPatternSelected = new ImagePattern(magicButtonSmallSelected);
    Image useButtonSmallSelected = new Image((getClass().getResource("/images/UseButtonSmallSelected.png").toExternalForm()), 255,80,true,true);
    ImagePattern useButtonSmallPatternSelected = new ImagePattern(useButtonSmallSelected);
    Image moveButtonSmallSelected = new Image((getClass().getResource("/images/MoveButtonSmallSelected.png").toExternalForm()), 255,80,true,true);
    ImagePattern moveButtonSmallPatternSelected = new ImagePattern(moveButtonSmallSelected);
    Image meleeButtonSmallSelected = new Image((getClass().getResource("/images/MeleeButtonSmallSelected.png").toExternalForm()), 255,100,true,true);
    ImagePattern meleeButtonSmallPatternSelected = new ImagePattern(meleeButtonSmallSelected);
    Image chatButton = new Image((getClass().getResource("/images/chatImage.png").toExternalForm()), 255,100,true,true);
    ImagePattern chatButtonPattern = new ImagePattern(chatButton);

    Image meleeButtonLarge = new Image((getClass().getResource("/images/meleeButton.png").toExternalForm()), 500,500,true,true);
    ImagePattern meleeButtonLargePattern = new ImagePattern(meleeButtonLarge);
    Image submitButtonLarge = new Image((getClass().getResource("/images/SubmitButtonLarge.png").toExternalForm()), 500,500,true,true);
    ImagePattern submitButtonLargePattern = new ImagePattern(submitButtonLarge);
    Image rangeButtonSmall = new Image((getClass().getResource("/images/rangeButton.png").toExternalForm()), 500,500,true,true);
    ImagePattern rangeButtonSmallPattern = new ImagePattern(rangeButtonSmall);
    Image magicButtonSmall = new Image((getClass().getResource("/images/magicButton.png").toExternalForm()), 500,500,true,true);
    ImagePattern magicButtonSmallPattern = new ImagePattern(magicButtonSmall);
    Image useButtonSmall = new Image((getClass().getResource("/images/useButton.png").toExternalForm()), 500,500,true,true);
    ImagePattern useButtonSmallPattern = new ImagePattern(useButtonSmall);
    Image moveButtonSmall = new Image((getClass().getResource("/images/moveButton.png").toExternalForm()), 500,500,true,true);
    ImagePattern moveButtonSmallPattern = new ImagePattern(moveButtonSmall);
    Image meleeButtonSmall = new Image((getClass().getResource("/images/MeleeButtonSmall.png").toExternalForm()), 500,500,true,true);
    ImagePattern meleeButtonSmallPattern = new ImagePattern(meleeButtonSmall);


    Image infoButton = new Image((getClass().getResource("/images/infoButton.png").toExternalForm()), 100, 100, true, true);
    ImagePattern infoButtonPattern = new ImagePattern(infoButton);
    Image info190 = new Image((getClass().getResource("/images/Tutorial190.gif").toExternalForm()), 190, 100, true, true);
    ImagePattern info190Pattern = new ImagePattern(info190);
    Image info300 = new Image((getClass().getResource("/images/Tutorial300.gif").toExternalForm()), 300, 158, true, true);
    ImagePattern info300Pattern = new ImagePattern(info300);
    Image info500 = new Image((getClass().getResource("/images/Tutorial500.gif").toExternalForm()), 500, 263, true, true);
    ImagePattern info500Pattern = new ImagePattern(info500);

    Image warriorFace = new Image((getClass().getResource("/images/WarriorFace.png").toExternalForm()), 110, 110 ,true, true);
    ImagePattern warriorFacePattern = new ImagePattern(warriorFace);
    Image mageFace = new Image((getClass().getResource("/images/MageFace.png").toExternalForm()), 110, 110 ,true, true);
    ImagePattern mageFacePattern = new ImagePattern(mageFace);
    Image rangerFace = new Image((getClass().getResource("/images/RangerFace.png").toExternalForm()), 110, 110 ,true, true);
    ImagePattern rangerFacePattern = new ImagePattern(rangerFace);
    Image profFace = new Image((getClass().getResource("/images/ProfFace.png").toExternalForm()), 110, 110 ,true, true);
    ImagePattern profFacePattern = new ImagePattern(profFace);
    Image studentFace = new Image((getClass().getResource("/images/StudentFace.png").toExternalForm()), 110, 110 ,true, true);
    ImagePattern studentFacePattern = new ImagePattern(studentFace);
    Image touristFace = new Image((getClass().getResource("/images/TouristFace.png").toExternalForm()), 110, 110 ,true, true);
    ImagePattern touristFacePattern = new ImagePattern(touristFace);
    Image emptyFace = new Image((getClass().getResource("/images/EmptyFace.png").toExternalForm()),110, 110, true, true);
    ImagePattern emptyFacePattern = new ImagePattern(emptyFace);

    Image imageFloor = new Image((getClass().getResource("/images/Floor.png").toExternalForm()), 32, 32, true, true);
    ImagePattern floorPattern = new ImagePattern(imageFloor);
    Image imageWall = new Image((getClass().getResource("/images/Wall.png").toExternalForm()), 32, 32, true, true);
    ImagePattern wallPattern = new ImagePattern(imageWall);
    Image imageStairDown = new Image((getClass().getResource("/images/StairDown.png").toExternalForm()), 32, 32, true, true);
    ImagePattern stairDownPattern = new ImagePattern(imageStairDown);


    Image imageWarriorLeft = new Image((getClass().getResource("/images/WarriorLeft.png").toExternalForm()), 32, 32, true, true);
    ImagePattern warriorLeftPattern = new ImagePattern(imageWarriorLeft);
    Image imageWarriorRight = new Image((getClass().getResource("/images/WarriorRight.png").toExternalForm()), 32, 32, true, true);
    ImagePattern warriorRightPattern = new ImagePattern(imageWarriorRight);
    Image imageWarriorUp = new Image((getClass().getResource("/images/WarriorTop.png").toExternalForm()), 32, 32, true, true);
    ImagePattern warriorUpPattern = new ImagePattern(imageWarriorUp);
    Image imageWarriorDown = new Image((getClass().getResource("/images/Warrior.png").toExternalForm()), 32, 32, true, true);
    ImagePattern warriorDownPattern = new ImagePattern(imageWarriorDown);
    Image gifWarriorFront = new Image((getClass()).getResource("/images/WarriorFrontSide.gif").toExternalForm(), 32, 32, true, true);
    ImagePattern gifWarriorFrontPattern = new ImagePattern(gifWarriorFront);
    Image gifWarriorLeft = new Image((getClass()).getResource("/images/WarriorLeftSide.gif").toExternalForm(), 32, 32, true, true);
    ImagePattern gifWarriorLeftPattern = new ImagePattern(gifWarriorLeft);
    Image gifWarriorRight = new Image((getClass()).getResource("/images/WarriorRightSide.gif").toExternalForm(), 32, 32, true, true);
    ImagePattern gifWarriorRightPattern = new ImagePattern(gifWarriorRight);
    Image gifWarriorBack = new Image((getClass()).getResource("/images/WarriorBackSide.gif").toExternalForm(), 32, 32, true, true);
    ImagePattern gifWarriorBackPattern = new ImagePattern(gifWarriorBack);


    Image imageRangerLeft = new Image((getClass().getResource("/images/RangerLeft.png").toExternalForm()), 32, 32, true, true);
    ImagePattern rangerLeftPattern = new ImagePattern(imageRangerLeft);
    Image imageRangerRight = new Image((getClass().getResource("/images/RangerRight.png").toExternalForm()), 32, 32, true, true);
    ImagePattern rangerRightPattern = new ImagePattern(imageRangerRight);
    Image imageRangerUp = new Image((getClass().getResource("/images/RangerTop.png").toExternalForm()), 32, 32, true, true);
    ImagePattern rangerUpPattern = new ImagePattern(imageRangerUp);
    Image imageRangerDown = new Image((getClass().getResource("/images/Ranger.png").toExternalForm()), 32, 32, true, true);
    ImagePattern rangerDownPattern = new ImagePattern(imageRangerDown);
    Image gifRangerFront = new Image((getClass()).getResource("/images/RangerFrontSide.gif").toExternalForm(), 32, 32, true, true);
    ImagePattern gifRangerFrontPattern = new ImagePattern(gifRangerFront);
    Image gifRangerLeft = new Image((getClass()).getResource("/images/RangerLeftSide.gif").toExternalForm(), 32, 32, true, true);
    ImagePattern gifRangerLeftPattern = new ImagePattern(gifRangerLeft);
    Image gifRangerRight = new Image((getClass()).getResource("/images/RangerRightSide.gif").toExternalForm(), 32, 32, true, true);
    ImagePattern gifRangerRightPattern = new ImagePattern(gifRangerRight);
    Image gifRangerBack = new Image((getClass()).getResource("/images/RangerBackSide.gif").toExternalForm(), 32, 32, true, true);
    ImagePattern gifRangerBackPattern = new ImagePattern(gifRangerBack);


    Image imageMageLeft = new Image((getClass().getResource("/images/MageLeft.png").toExternalForm()), 32, 32, true, true);
    ImagePattern mageLeftPattern = new ImagePattern(imageMageLeft);
    Image imageMageRight = new Image((getClass().getResource("/images/MageRight.png").toExternalForm()), 32, 32, true, true);
    ImagePattern mageRightPattern = new ImagePattern(imageMageRight);
    Image imageMageUp = new Image((getClass().getResource("/images/MageTop.png").toExternalForm()), 32, 32, true, true);
    ImagePattern mageUpPattern = new ImagePattern(imageMageUp);
    Image imageMageDown = new Image((getClass().getResource("/images/Mage.png").toExternalForm()), 32, 32, true, true);
    ImagePattern mageDownPattern = new ImagePattern(imageMageDown);
    Image gifMageFront = new Image((getClass()).getResource("/images/MageFrontSide.gif").toExternalForm(), 32, 32, true, true);
    ImagePattern gifMageFrontPattern = new ImagePattern(gifMageFront);
    Image gifMageLeft = new Image((getClass()).getResource("/images/MageLeftSide.gif").toExternalForm(), 32, 32, true, true);
    ImagePattern gifMageLeftPattern = new ImagePattern(gifMageLeft);
    Image gifMageRight = new Image((getClass()).getResource("/images/MageRightSide.gif").toExternalForm(), 32, 32, true, true);
    ImagePattern gifMageRightPattern = new ImagePattern(gifMageRight);
    Image gifMageBack = new Image((getClass()).getResource("/images/MageBackSide.gif").toExternalForm(), 32, 32, true, true);
    ImagePattern gifMageBackPattern = new ImagePattern(gifMageBack);


    Image imageTouristLeft = new Image((getClass().getResource("/images/TouristLeft.png").toExternalForm()), 32, 32, true, true);
    ImagePattern touristLeftPattern = new ImagePattern(imageTouristLeft);
    Image imageTouristRight = new Image((getClass().getResource("/images/TouristRight.png").toExternalForm()), 32, 32, true, true);
    ImagePattern touristRightPattern = new ImagePattern(imageTouristRight);
    Image imageTouristUp = new Image((getClass().getResource("/images/TouristTop.png").toExternalForm()), 32, 32, true, true);
    ImagePattern touristUpPattern = new ImagePattern(imageTouristUp);
    Image imageTouristDown = new Image((getClass().getResource("/images/Tourist.png").toExternalForm()), 32, 32, true, true);
    ImagePattern touristDownPattern = new ImagePattern(imageTouristDown);
    Image gifTouristFront = new Image((getClass()).getResource("/images/TouristFrontSide.gif").toExternalForm(), 32, 32, true, true);
    ImagePattern gifTouristFrontPattern = new ImagePattern(gifTouristFront);
    Image gifTouristLeft = new Image((getClass()).getResource("/images/TouristLeftSide.gif").toExternalForm(), 32, 32, true, true);
    ImagePattern gifTouristLeftPattern = new ImagePattern(gifTouristLeft);
    Image gifTouristRight = new Image((getClass()).getResource("/images/TouristRightSide.gif").toExternalForm(), 32, 32, true, true);
    ImagePattern gifTouristRightPattern = new ImagePattern(gifTouristRight);
    Image gifTouristBack = new Image((getClass()).getResource("/images/TouristBackSide.gif").toExternalForm(), 32, 32, true, true);
    ImagePattern gifTouristBackPattern = new ImagePattern(gifTouristBack);

    Image imageStudentLeft = new Image((getClass().getResource("/images/StudentLeft.png").toExternalForm()), 32, 32, true, true);
    ImagePattern studentLeftPattern = new ImagePattern(imageStudentLeft);
    Image imageStudentRight = new Image((getClass().getResource("/images/StudentRight.png").toExternalForm()), 32, 32, true, true);
    ImagePattern studentRightPattern = new ImagePattern(imageStudentRight);
    Image imageStudentUp = new Image((getClass().getResource("/images/StudentTop.png").toExternalForm()), 32, 32, true, true);
    ImagePattern studentUpPattern = new ImagePattern(imageStudentUp);
    Image imageStudentDown = new Image((getClass().getResource("/images/Student.png").toExternalForm()), 32, 32, true, true);
    ImagePattern studentDownPattern = new ImagePattern(imageStudentDown);
    Image gifStudentFront = new Image((getClass().getResource("/images/StudentFrontSide.gif").toExternalForm()), 32, 32, true, true);
    ImagePattern gifStudentFrontPattern = new ImagePattern(gifStudentFront);
    Image gifStudentLeft = new Image((getClass().getResource("/images/StudentLeftSide.gif").toExternalForm()), 32, 32, true, true);
    ImagePattern gifStudentLeftPattern = new ImagePattern(gifStudentLeft);
    Image gifStudentRight = new Image((getClass().getResource("/images/StudentRightSide.gif").toExternalForm()), 32, 32, true, true);
    ImagePattern gifStudentRightPattern = new ImagePattern(gifStudentRight);
    Image gifStudentBack = new Image((getClass().getResource("/images/StudentBackSide.gif").toExternalForm()), 32, 32, true, true);
    ImagePattern gifStudentBackPattern = new ImagePattern(gifStudentBack);

    Image imageProfessorLeft = new Image((getClass().getResource("/images/ProfLeft.png").toExternalForm()), 32, 32, true, true);
    ImagePattern professorLeftPattern = new ImagePattern(imageProfessorLeft);
    Image imageProfessorRight = new Image((getClass().getResource("/images/ProfRight.png").toExternalForm()), 32, 32, true, true);
    ImagePattern professorRightPattern = new ImagePattern(imageProfessorRight);
    Image imageProfessorUp = new Image((getClass().getResource("/images/ProfTop.png").toExternalForm()), 32, 32, true, true);
    ImagePattern professorUpPattern = new ImagePattern(imageProfessorUp);
    Image imageProfessorDown = new Image((getClass().getResource("/images/Prof.png").toExternalForm()), 32, 32, true, true);
    ImagePattern professorDownPattern = new ImagePattern(imageProfessorDown);
    Image gifProfFront = new Image((getClass()).getResource("/images/ProfFrontSide.gif").toExternalForm(), 32, 32, true, true);
    ImagePattern gifProfFrontPattern = new ImagePattern(gifProfFront);
    Image gifProfLeft = new Image((getClass()).getResource("/images/ProfLeftSide.gif").toExternalForm(), 32, 32, true, true);
    ImagePattern gifProfLeftPattern = new ImagePattern(gifProfLeft);
    Image gifProfRight = new Image((getClass()).getResource("/images/ProfRightSide.gif").toExternalForm(), 32, 32, true, true);
    ImagePattern gifProfRightPattern = new ImagePattern(gifProfRight);
    Image gifProfBack = new Image((getClass()).getResource("/images/ProfBackSide.gif").toExternalForm(), 32, 32, true, true);
    ImagePattern gifProfBackPattern = new ImagePattern(gifProfBack);


    Image imageSkeleton = new Image((getClass().getResource("/images/Skeleton.png").toExternalForm()), 32, 32, true, true);
    ImagePattern skeletonPattern = new ImagePattern(imageSkeleton);
    Image imageGoblin = new Image((getClass().getResource("/images/Goblin.png").toExternalForm()), 32, 32, true, true);
    ImagePattern goblinPattern = new ImagePattern(imageGoblin);
    Image imageWolf = new Image((getClass().getResource("/images/Wolf.png").toExternalForm()), 32, 32, true, true);
    ImagePattern wolfPattern = new ImagePattern(imageWolf);
    Image imageBoss = new Image((getClass().getResource("/images/FinalBoss.png").toExternalForm()), 32, 32, true, true);
    ImagePattern bossPattern = new ImagePattern(imageBoss);

    Image imageChestOpened = new Image((getClass().getResource("/images/ChestOpened.png").toExternalForm()), 32, 32, true, true);
    ImagePattern chestOpenedPattern = new ImagePattern(imageChestOpened);
    Image imageChestClosed = new Image((getClass().getResource("/images/ChestClosed.png").toExternalForm()), 32, 32, true, true);
    ImagePattern chestClosedPattern = new ImagePattern(imageChestClosed);
    Image imageDoorOpened = new Image((getClass().getResource("/images/DoorOpened.png").toExternalForm()), 32, 32, true, true);
    ImagePattern doorOpenedPattern = new ImagePattern(imageDoorOpened);
    Image imageDoorClosed = new Image((getClass().getResource("/images/DoorClosed.png").toExternalForm()), 32, 32, true, true);
    ImagePattern doorClosedPattern = new ImagePattern(imageDoorClosed);

    Image imageSword = new Image((getClass().getResource("/images/Sword.png").toExternalForm()), 32, 32, true, true);
    ImagePattern swordPattern = new ImagePattern(imageSword);
    Image imageAxe = new Image((getClass().getResource("/images/Axe.png").toExternalForm()), 32, 32, true, true);
    ImagePattern axePattern = new ImagePattern(imageAxe);
    Image imageSpear = new Image((getClass().getResource("/images/Spear.png").toExternalForm()), 32, 32, true, true);
    ImagePattern spearPattern = new ImagePattern(imageSpear);
    Image imageSmallPotion = new Image((getClass().getResource("/images/SmallPotion.png").toExternalForm()), 32, 32, true, true);
    ImagePattern smallPotionPattern = new ImagePattern(imageSmallPotion);
    Image imageBigPotion= new Image((getClass().getResource("/images/BigPotion.png").toExternalForm()), 32, 32, true, true);
    ImagePattern bigPotionPattern = new ImagePattern(imageBigPotion);


    Image attackMa = new Image((getClass().getResource("/images/AttackMa.png").toExternalForm()), 32, 32, true,true);
    ImagePattern attackMaPattern = new ImagePattern(attackMa);
    Image attackRaDown = new Image((getClass().getResource("/images/AttackRaDown.png").toExternalForm()), 32, 32, true, true);
    ImagePattern attackRaDownPattern = new ImagePattern(attackRaDown);
    Image attackRaUp = new Image((getClass().getResource("/images/AttackRaUp.png").toExternalForm()), 32, 32, true, true);
    ImagePattern attackRaUpPattern = new ImagePattern(attackRaUp);
    Image attackRaRight = new Image((getClass().getResource("/images/AttackRaRight.png").toExternalForm()), 32, 32, true, true);
    ImagePattern attackRaRightPattern = new ImagePattern(attackRaRight);
    Image attackRaLeft = new Image((getClass().getResource("/images/AttackRaLeft.png").toExternalForm()), 32, 32, true, true);
    ImagePattern attackRaLeftPattern = new ImagePattern(attackRaLeft);
    Image attackMe = new Image((getClass().getResource("/images/AttackMe.png").toExternalForm()), 32, 32, true, true);
    ImagePattern attackMePattern = new ImagePattern(attackMe);
    Image attackWolf = new Image((getClass().getResource("/images/AttackWolf.png").toExternalForm()), 32, 32, true, true);
    ImagePattern attackWolfPattern = new ImagePattern(attackWolf);
    Image attackGoblin = new Image((getClass().getResource("/images/AttackWolf.png").toExternalForm()), 32,32 ,true, true);
    ImagePattern attackGoblinPattern = new ImagePattern(attackGoblin);
    Image attackFinalBoss = new Image((getClass().getResource("/images/AttackFinalBoss.png").toExternalForm()), 32,32,true,true);
    ImagePattern attackFinalBossPattern = new ImagePattern(attackFinalBoss);


    Image windMagic = new Image((getClass().getResource("/images/WindMagic2.png").toExternalForm()), 32, 32, true, true);
    ImagePattern windMagicPattern = new ImagePattern(windMagic);
    Image iceMagic = new Image((getClass().getResource("/images/IceMagic.png").toExternalForm()), 32, 32, true, true);
    ImagePattern iceMagicPattern = new ImagePattern(iceMagic);
    Image healMagic  = new Image((getClass().getResource("/images/HealMagic.png").toExternalForm()), 32, 32, true, true);
    ImagePattern healMagicPattern = new ImagePattern(healMagic);

    Image gifSwordSlash = new Image((getClass().getResource("/images/swordSlash.gif").toExternalForm()), 32, 32, true,true);
    ImagePattern gifSwordSlashPattern = new ImagePattern(gifSwordSlash);
    Image gifFinalBossSlash = new Image((getClass().getResource("/images/FinalBossSlash.gif").toExternalForm()), 32, 32, true,true);
    ImagePattern gifFinalBossSlashPattern = new ImagePattern(gifFinalBossSlash);
    Image gifFireMagic = new Image((getClass().getResource("/images/FireMagic2.gif").toExternalForm()), 32, 32, true,true);
    ImagePattern gifFireMagicPattern = new ImagePattern(gifFireMagic);


    Image fireMagicBG = new Image((getClass().getResource("/images/FireMagicWhiteBG.png").toExternalForm()), 32, 32, true, true);
    ImagePattern fireMagicBGPattern = new ImagePattern(fireMagicBG);
    Image windMagicBG = new Image((getClass().getResource("/images/WindMagicWhiteBG.png").toExternalForm()), 32, 32, true, true);
    ImagePattern windMagicBGPattern = new ImagePattern(windMagicBG);
    Image iceMagicBG = new Image((getClass().getResource("/images/IceMagicWhiteBG.png").toExternalForm()), 32, 32, true, true);
    ImagePattern iceMagicBGPattern = new ImagePattern(iceMagicBG);



    public ImageHolder2(){

    }
}
