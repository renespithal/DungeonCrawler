package view.designHP.scenes.singleplayerGameHP;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;

/**
 * Created by Felix on 19.01.2016.
 */
public class ImageHolderHP {

	Image pictureBackground = new Image((getClass().getResource("/images/backBackground.jpg").toExternalForm()), 120, 90, true, true);
	ImagePattern pictureBackgroundPattern = new ImagePattern(pictureBackground);
	Image levelBackground = new Image((getClass().getResource("/images/levelBackground.png").toExternalForm()), 500, 208, true, true);
	ImagePattern levelBackgroundPattern = new ImagePattern(levelBackground);

	Image mainBackground = new Image((getClass().getResource("/hp/media/background/gameScene.jpg").toExternalForm()), 1920, 1080, true, true);
	ImagePattern mainBackgroundPattern = new ImagePattern(mainBackground);
	
	Image background = new Image((getClass().getResource("/images/buttonBackground.jpg").toExternalForm()), 1920, 1080, true, true);
	ImagePattern backgroundPattern = new ImagePattern(background);

	Image backgroundGO = new Image((getClass().getResource("/images/backgroundGO.jpg").toExternalForm()), 1920, 1080, true, true);
	ImagePattern backgroundGOPattern = new ImagePattern(backgroundGO);
	// Image backgroundGW = new
	// Image((getClass().getResource("/images/gameWonBackground.png").toExternalForm()),
	// 1920, 1080, true, true);
	// ImagePattern backgroundGWPattern = new ImagePattern(backgroundGW);

	Image miniMapBackground = new Image((getClass().getResource("/hp/media/background/maraudersMap.jpg").toExternalForm()), 650, 300, true, true);
	ImagePattern miniMapBackgroundPattern = new ImagePattern(miniMapBackground);
	Image miniMapSmallBackground = new Image((getClass().getResource("/hp/media/mapButton.png").toExternalForm()), 250, 100, true, true);
	ImagePattern miniMapSmallBackgroundPattern = new ImagePattern(miniMapSmallBackground);

	Image showStats = new Image((getClass().getResource("/images/showStats.png").toExternalForm()), 150, 25, true, true);
	ImagePattern showStatsPattern = new ImagePattern(showStats);
	Image hideStats = new Image((getClass().getResource("/images/hideStats.png").toExternalForm()), 150, 25, true, true);
	ImagePattern hideStatsPattern = new ImagePattern(hideStats);

	Image str = new Image((getClass().getResource("/images/strButton.png").toExternalForm()), 40, 40, true, true);
	ImagePattern strPattern = new ImagePattern(str);
	Image dex = new Image((getClass().getResource("/images/dexButton.png").toExternalForm()), 40, 40, true, true);
	ImagePattern dexPattern = new ImagePattern(dex);
	Image wisSelected = new Image((getClass().getResource("/images/wisButtonSelected.png").toExternalForm()), 40, 40, true, true);
	ImagePattern wisSelectedPattern = new ImagePattern(wisSelected);
	Image wis = new Image((getClass().getResource("/images/wisButton.png").toExternalForm()), 40, 40, true, true);
	ImagePattern wisPattern = new ImagePattern(wis);
	Image health = new Image("images/Health.png", 40, 40, true, true);
	ImagePattern healthPattern = new ImagePattern(health);
	Image classPic = new Image("images/ClassPic.png", 40, 40, true, true);
	ImagePattern classPicPattern = new ImagePattern(classPic);

	Image strButtonSelected = new Image((getClass().getResource("/images/strButtonSelected.png").toExternalForm()), 40, 40, true, true);
	ImagePattern strButtonSelectedPattern = new ImagePattern(strButtonSelected);
	Image dexButtonSelected = new Image((getClass().getResource("/images/dexButtonSelected.png").toExternalForm()), 40, 40, true, true);
	ImagePattern dexButtonSelectedPattern = new ImagePattern(dexButtonSelected);
	Image moveButton = new Image((getClass().getResource("/hp/media/move_1.png").toExternalForm()), 510, 80, true, true);
	ImagePattern moveButtonPattern = new ImagePattern(moveButton);
	Image useButton = new Image((getClass().getResource("/hp/media/use_1.png").toExternalForm()), 510, 80, true, true);
	ImagePattern useButtonPattern = new ImagePattern(useButton);
	Image moveButtonSelected = new Image((getClass().getResource("/hp/media/move.png").toExternalForm()), 80, 80, true, true);
	ImagePattern moveButtonSelectedPattern = new ImagePattern(moveButtonSelected);
	Image useButtonSelected = new Image((getClass().getResource("/hp/media/use.png").toExternalForm()), 80, 80, true, true);
	ImagePattern useButtonSelectedPattern = new ImagePattern(useButtonSelected);

	Image submitButtonLargeSelected = new Image((getClass().getResource("/images/SubmitButtonLargeSelected.png").toExternalForm()), 510, 80, true,
			true);
	ImagePattern submitButtonLargePatternSelected = new ImagePattern(submitButtonLargeSelected);
	Image meleeButtonLargeSelected = new Image((getClass().getResource("/images/MeleeButtonLargeSelected.png").toExternalForm()), 510, 80, true, true);
	ImagePattern meleeButtonLargePatternSelected = new ImagePattern(meleeButtonLargeSelected);
	Image rangeButtonSmallSelected = new Image((getClass().getResource("/images/RangeButtonSmallSelected.png").toExternalForm()), 255, 80, true, true);
	ImagePattern rangeButtonSmallPatternSelected = new ImagePattern(rangeButtonSmallSelected);
	Image magicButtonSmallSelected = new Image((getClass().getResource("/images/MagicButtonSmallSelected.png").toExternalForm()), 255, 80, true, true);
	ImagePattern magicButtonSmallPatternSelected = new ImagePattern(magicButtonSmallSelected);
	Image useButtonSmallSelected = new Image((getClass().getResource("/images/UseButtonSmallSelected.png").toExternalForm()), 255, 80, true, true);
	ImagePattern useButtonSmallPatternSelected = new ImagePattern(useButtonSmallSelected);
	Image moveButtonSmallSelected = new Image((getClass().getResource("/images/MoveButtonSmallSelected.png").toExternalForm()), 255, 80, true, true);
	ImagePattern moveButtonSmallPatternSelected = new ImagePattern(moveButtonSmallSelected);
	Image meleeButtonSmallSelected = new Image((getClass().getResource("/images/MeleeButtonSmallSelected.png").toExternalForm()), 255, 100, true,
			true);
	ImagePattern meleeButtonSmallPatternSelected = new ImagePattern(meleeButtonSmallSelected);

	Image meleeButtonLarge = new Image((getClass().getResource("/images/meleeButton.png").toExternalForm()), 500, 500, true, true);
	ImagePattern meleeButtonLargePattern = new ImagePattern(meleeButtonLarge);
	Image submitButtonLarge = new Image((getClass().getResource("/images/SubmitButtonLarge.png").toExternalForm()), 500, 500, true, true);
	ImagePattern submitButtonLargePattern = new ImagePattern(submitButtonLarge);
	Image rangeButtonSmall = new Image((getClass().getResource("/images/rangeButton.png").toExternalForm()), 500, 500, true, true);
	ImagePattern rangeButtonSmallPattern = new ImagePattern(rangeButtonSmall);
	Image magicButtonSmall = new Image((getClass().getResource("/images/magicButton.png").toExternalForm()), 500, 500, true, true);
	ImagePattern magicButtonSmallPattern = new ImagePattern(magicButtonSmall);
	Image useButtonSmall = new Image((getClass().getResource("/images/useButton.png").toExternalForm()), 500, 500, true, true);
	ImagePattern useButtonSmallPattern = new ImagePattern(useButtonSmall);
	Image moveButtonSmall = new Image((getClass().getResource("/images/moveButton.png").toExternalForm()), 500, 500, true, true);
	ImagePattern moveButtonSmallPattern = new ImagePattern(moveButtonSmall);
	Image meleeButtonSmall = new Image((getClass().getResource("/images/MeleeButtonSmall.png").toExternalForm()), 500, 500, true, true);
	ImagePattern meleeButtonSmallPattern = new ImagePattern(meleeButtonSmall);

	// Images für die Portraits
	Image harryFace = new Image((getClass().getResource("/images/HarryFace.png").toExternalForm()), 110, 110, true, true);
	ImagePattern harryFacePattern = new ImagePattern(harryFace);
	Image ronFace = new Image((getClass().getResource("/images/RonFace.png").toExternalForm()), 110, 110, true, true);
	ImagePattern ronFacePattern = new ImagePattern(ronFace);
	Image hermineFace = new Image((getClass().getResource("/images/HermineFace.png").toExternalForm()), 110, 110, true, true);
	ImagePattern hermineFacePattern = new ImagePattern(hermineFace);
	Image hagridFace = new Image((getClass().getResource("/images/HagridFace.png").toExternalForm()), 110, 110, true, true);
	ImagePattern hagridFacePattern = new ImagePattern(hagridFace);
	Image dumbledoreFace = new Image((getClass().getResource("/images/DumbledoreFace.png").toExternalForm()), 110, 110, true, true);
	ImagePattern dumbledoreFacePattern = new ImagePattern(dumbledoreFace);
	Image ginnyFace = new Image((getClass().getResource("/images/GinnyFace.png").toExternalForm()), 110, 110, true, true);
	ImagePattern ginnyFacePattern = new ImagePattern(ginnyFace);
	Image nevilleFace = new Image((getClass().getResource("/images/NevilleFace.png").toExternalForm()), 110, 110, true, true);
	ImagePattern nevilleFacePattern = new ImagePattern(nevilleFace);
	Image moodyFace = new Image((getClass().getResource("/images/MoodyFace.png").toExternalForm()), 110, 110, true, true);
	ImagePattern moodyFacePattern = new ImagePattern(moodyFace);
	Image snapeFace = new Image((getClass().getResource("/images/SnapeFace.png").toExternalForm()), 110, 110, true, true);
	ImagePattern snapeFacePattern = new ImagePattern(snapeFace);
	Image emptyFace = new Image((getClass().getResource("/images/EmptyFace.png").toExternalForm()), 110, 110, true, true);
	ImagePattern emptyFacePattern = new ImagePattern(emptyFace);

	Image imageFloor = new Image((getClass().getResource("/images/Floor.png").toExternalForm()), 32, 32, true, true);
	ImagePattern floorPattern = new ImagePattern(imageFloor);
	Image imageWall = new Image((getClass().getResource("/images/WallHP.png").toExternalForm()), 32, 32, true, true);
	ImagePattern wallPattern = new ImagePattern(imageWall);
	Image imageStairDown = new Image((getClass().getResource("/images/StairDown.png").toExternalForm()), 32, 32, true, true);
	ImagePattern stairDownPattern = new ImagePattern(imageStairDown);

	// HARRY GIFs
	Image gifHarryFront = new Image((getClass()).getResource("/images/HarryDownSide.gif").toExternalForm(), 32, 32, true, true);
	ImagePattern gifHarryFrontPattern = new ImagePattern(gifHarryFront);
	Image gifHarryLeft = new Image((getClass()).getResource("/images/HarryLeftSide.gif").toExternalForm(), 32, 32, true, true);
	ImagePattern gifHarryLeftPattern = new ImagePattern(gifHarryLeft);
	Image gifHarryRight = new Image((getClass()).getResource("/images/HarryRightSide.gif").toExternalForm(), 32, 32, true, true);
	ImagePattern gifHarryRightPattern = new ImagePattern(gifHarryRight);
	Image gifHarryBack = new Image((getClass()).getResource("/images/HarryUpSide.gif").toExternalForm(), 32, 32, true, true);
	ImagePattern gifHarryBackPattern = new ImagePattern(gifHarryBack);

	// RON GIFs
	Image gifRonFront = new Image((getClass()).getResource("/images/RonDownSide.gif").toExternalForm(), 32, 32, true, true);
	ImagePattern gifRonFrontPattern = new ImagePattern(gifRonFront);
	Image gifRonLeft = new Image((getClass()).getResource("/images/RonLeftSide.gif").toExternalForm(), 32, 32, true, true);
	ImagePattern gifRonLeftPattern = new ImagePattern(gifRonLeft);
	Image gifRonRight = new Image((getClass()).getResource("/images/RonRightSide.gif").toExternalForm(), 32, 32, true, true);
	ImagePattern gifProfRightPattern = new ImagePattern(gifRonRight);
	Image gifRonBack = new Image((getClass()).getResource("/images/RonUpSide.gif").toExternalForm(), 32, 32, true, true);
	ImagePattern gifProfBackPattern = new ImagePattern(gifRonBack);

	// HERMINE GIFs
	Image gifHermineFront = new Image((getClass()).getResource("/images/HermineDownSide.gif").toExternalForm(), 32, 32, true, true);
	ImagePattern gifHermineFrontPattern = new ImagePattern(gifHermineFront);
	Image gifHermineLeft = new Image((getClass()).getResource("/images/HermineLeftSide.gif").toExternalForm(), 32, 32, true, true);
	ImagePattern gifHermineLeftPattern = new ImagePattern(gifHermineLeft);
	Image gifHermineRight = new Image((getClass()).getResource("/images/HermineRightSide.gif").toExternalForm(), 32, 32, true, true);
	ImagePattern gifHermineRightPattern = new ImagePattern(gifHermineRight);
	Image gifHermineBack = new Image((getClass()).getResource("/images/HermineUpSide.gif").toExternalForm(), 32, 32, true, true);
	ImagePattern gifHermineBackPattern = new ImagePattern(gifHermineBack);

	// HAGRID GIFs
	Image gifHagridFront = new Image((getClass().getResource("/images/HagridDownSide.gif").toExternalForm()), 32, 32, true, true);
	ImagePattern gifHagridFrontPattern = new ImagePattern(gifHagridFront);
	Image gifHagridLeft = new Image((getClass().getResource("/images/HagridLeftSide.gif").toExternalForm()), 32, 32, true, true);
	ImagePattern gifHagridLeftPattern = new ImagePattern(gifHagridLeft);
	Image gifHagridRight = new Image((getClass().getResource("/images/HagridRightSide.gif").toExternalForm()), 32, 32, true, true);
	ImagePattern gifHagridRightPattern = new ImagePattern(gifHagridRight);
	Image gifHagridBack = new Image((getClass().getResource("/images/HagridUpSide.gif").toExternalForm()), 32, 32, true, true);
	ImagePattern gifHagridBackPattern = new ImagePattern(gifHagridBack);

	// DUMBLEDORE GIFs
	Image gifDumbledoreFront = new Image((getClass()).getResource("/images/DumbledoreDownSide.gif").toExternalForm(), 32, 32, true, true);
	ImagePattern gifDumbledoreFrontPattern = new ImagePattern(gifDumbledoreFront);
	Image gifDumbledoreLeft = new Image((getClass()).getResource("/images/DumbledoreLeftSide.gif").toExternalForm(), 32, 32, true, true);
	ImagePattern gifDumbledoreLeftPattern = new ImagePattern(gifDumbledoreLeft);
	Image gifDumbledoreRight = new Image((getClass()).getResource("/images/DumbledoreRightSide.gif").toExternalForm(), 32, 32, true, true);
	ImagePattern gifDumbledoreRightPattern = new ImagePattern(gifDumbledoreRight);
	Image gifDumbledoreBack = new Image((getClass()).getResource("/images/DumbledoreUpSide.gif").toExternalForm(), 32, 32, true, true);
	ImagePattern gifDumbledoreBackPattern = new ImagePattern(gifDumbledoreBack);

	// GINNY GIFs
	Image gifGinnyFront = new Image((getClass()).getResource("/images/GinnyDownSide.gif").toExternalForm(), 32, 32, true, true);
	ImagePattern gifGinnyFrontPattern = new ImagePattern(gifGinnyFront);
	Image gifGinnyLeft = new Image((getClass()).getResource("/images/GinnyLeftSide.gif").toExternalForm(), 32, 32, true, true);
	ImagePattern gifGinnyLeftPattern = new ImagePattern(gifGinnyLeft);
	Image gifGinnyRight = new Image((getClass()).getResource("/images/GinnyRightSide.gif").toExternalForm(), 32, 32, true, true);
	ImagePattern gifGinnyRightPattern = new ImagePattern(gifGinnyRight);
	Image gifGinnyBack = new Image((getClass()).getResource("/images/GinnyUpSide.gif").toExternalForm(), 32, 32, true, true);
	ImagePattern gifGinnyBackPattern = new ImagePattern(gifGinnyBack);

	// NEVILLE GIFs
	Image gifNevilleFront = new Image((getClass()).getResource("/images/NevilleDownSide.gif").toExternalForm(), 32, 32, true, true);
	ImagePattern gifNevilleFrontPattern = new ImagePattern(gifNevilleFront);
	Image gifNevilleLeft = new Image((getClass()).getResource("/images/NevilleLeftSide.gif").toExternalForm(), 32, 32, true, true);
	ImagePattern gifNevilleLeftPattern = new ImagePattern(gifNevilleLeft);
	Image gifNevilleRight = new Image((getClass()).getResource("/images/NevilleRightSide.gif").toExternalForm(), 32, 32, true, true);
	ImagePattern gifNevilleRightPattern = new ImagePattern(gifNevilleRight);
	Image gifNevilleBack = new Image((getClass()).getResource("/images/NevilleUpSide.gif").toExternalForm(), 32, 32, true, true);
	ImagePattern gifNevilleBackPattern = new ImagePattern(gifNevilleBack);

	// MOODY GIFs
	Image gifMoodyFront = new Image((getClass()).getResource("/images/MoodyDownSide.gif").toExternalForm(), 32, 32, true, true);
	ImagePattern gifMoodyFrontPattern = new ImagePattern(gifMoodyFront);
	Image gifMoodyLeft = new Image((getClass()).getResource("/images/MoodyLeftSide.gif").toExternalForm(), 32, 32, true, true);
	ImagePattern gifMoodyLeftPattern = new ImagePattern(gifMoodyLeft);
	Image gifMoodyRight = new Image((getClass()).getResource("/images/MoodyRightSide.gif").toExternalForm(), 32, 32, true, true);
	ImagePattern gifMoodyRightPattern = new ImagePattern(gifMoodyRight);
	Image gifMoodyBack = new Image((getClass()).getResource("/images/MoodyUpSide.gif").toExternalForm(), 32, 32, true, true);
	ImagePattern gifMoodyBackPattern = new ImagePattern(gifMoodyBack);

	// SNAPE GIFs
	Image gifSnapeFront = new Image((getClass()).getResource("/images/SnapeDownSide.gif").toExternalForm(), 32, 32, true, true);
	ImagePattern gifSnapeFrontPattern = new ImagePattern(gifSnapeFront);
	Image gifSnapeLeft = new Image((getClass()).getResource("/images/SnapeLeftSide.gif").toExternalForm(), 32, 32, true, true);
	ImagePattern gifSnapeLeftPattern = new ImagePattern(gifSnapeLeft);
	Image gifSnapeRight = new Image((getClass()).getResource("/images/SnapeRightSide.gif").toExternalForm(), 32, 32, true, true);
	ImagePattern gifSnapeRightPattern = new ImagePattern(gifSnapeRight);
	Image gifSnapeBack = new Image((getClass()).getResource("/images/SnapeUpSide.gif").toExternalForm(), 32, 32, true, true);
	ImagePattern gifSnapeBackPattern = new ImagePattern(gifSnapeBack);

	// MONSTER
	Image imageDementor = new Image((getClass().getResource("/images/Dementor.png").toExternalForm()), 32, 32, true, true);
	ImagePattern dementorPattern = new ImagePattern(imageDementor);
	Image imageBellatrix = new Image((getClass().getResource("/images/Bellatrix.png").toExternalForm()), 32, 32, true, true);
	ImagePattern bellatrixPattern = new ImagePattern(imageBellatrix);
	Image imageDraco = new Image((getClass().getResource("/images/Draco.png").toExternalForm()), 32, 32, true, true);
	ImagePattern dracoPattern = new ImagePattern(imageDraco);
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
	Image imageBigPotion = new Image((getClass().getResource("/images/BigPotion.png").toExternalForm()), 32, 32, true, true);
	ImagePattern bigPotionPattern = new ImagePattern(imageBigPotion);

	Image attackMa = new Image((getClass().getResource("/hp/media/attack_1.png").toExternalForm()), 32, 32, true, true);
	ImagePattern attackMaPattern = new ImagePattern(attackMa);
	Image attackMaSelected = new Image((getClass().getResource("/hp/media/attack.png").toExternalForm()), 32, 32, true, true);
	ImagePattern attackMaSelectedPattern = new ImagePattern(attackMaSelected);
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
	Image attackGoblin = new Image((getClass().getResource("/images/AttackWolf.png").toExternalForm()), 32, 32, true, true);
	ImagePattern attackGoblinPattern = new ImagePattern(attackGoblin);
	Image attackFinalBoss = new Image((getClass().getResource("/images/AttackFinalBoss.png").toExternalForm()), 32, 32, true, true);
	ImagePattern attackFinalBossPattern = new ImagePattern(attackFinalBoss);

	Image windMagic = new Image((getClass().getResource("/images/WindMagic.png").toExternalForm()), 32, 32, true, true);
	ImagePattern windMagicPattern = new ImagePattern(windMagic);
	Image iceMagic = new Image((getClass().getResource("/images/AttackHP.png").toExternalForm()), 32, 32, true, true);
	ImagePattern iceMagicPattern = new ImagePattern(iceMagic);
	Image healMagic = new Image((getClass().getResource("/images/HealMagic.png").toExternalForm()), 32, 32, true, true);
	ImagePattern healMagicPattern = new ImagePattern(healMagic);
	Image windMagicSelected = new Image((getClass().getResource("/images/WindMagicSelected.png").toExternalForm()), 32, 32, true, true);
	ImagePattern windMagicSelectedPattern = new ImagePattern(windMagicSelected);
	Image iceMagicSelected = new Image((getClass().getResource("/images/IceMagicSelected.png").toExternalForm()), 32, 32, true, true);
	ImagePattern iceMagicSelectedPattern = new ImagePattern(iceMagicSelected);
	Image fireMagicSelected = new Image((getClass().getResource("/images/FireMagicSelected.png").toExternalForm()), 32, 32, true, true);
	ImagePattern fireMagicSelectedPattern = new ImagePattern(fireMagicSelected);

	Image gifSwordSlash = new Image((getClass().getResource("/images/swordSlash.gif").toExternalForm()), 32, 32, true, true);
	ImagePattern gifSwordSlashPattern = new ImagePattern(gifSwordSlash);
	Image gifFinalBossSlash = new Image((getClass().getResource("/images/FinalBossSlash.gif").toExternalForm()), 32, 32, true, true);
	ImagePattern gifFinalBossSlashPattern = new ImagePattern(gifFinalBossSlash);
	Image gifFireMagic = new Image((getClass().getResource("/images/FireMagic2.gif").toExternalForm()), 32, 32, true, true);
	ImagePattern gifFireMagicPattern = new ImagePattern(gifFireMagic);

	Image fireMagicBG = new Image((getClass().getResource("/images/FireMagicWhiteBG.png").toExternalForm()), 32, 32, true, true);
	ImagePattern fireMagicBGPattern = new ImagePattern(fireMagicBG);
	Image windMagicBG = new Image((getClass().getResource("/images/WindMagicWhiteBG.png").toExternalForm()), 32, 32, true, true);
	ImagePattern windMagicBGPattern = new ImagePattern(windMagicBG);
	Image iceMagicBG = new Image((getClass().getResource("/images/IceMagicWhiteBG.png").toExternalForm()), 32, 32, true, true);
	ImagePattern iceMagicBGPattern = new ImagePattern(iceMagicBG);

	Image imageLucius = new Image((getClass().getResource("/images/Lucius.png").toExternalForm()), 32, 32, true, true);
	ImagePattern luciusPattern = new ImagePattern(imageLucius);

	Image imageVoldemort = new Image((getClass().getResource("/images/Voldemort.png").toExternalForm()), 32, 32, true, true);
	ImagePattern voldemortPattern = new ImagePattern(imageVoldemort);





	public ImageHolderHP() {

	}
}
