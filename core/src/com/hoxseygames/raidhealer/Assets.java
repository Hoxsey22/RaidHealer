package com.hoxseygames.raidhealer;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.TextureLoader;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import java.util.ArrayList;

/**
 * Created by Hoxsey on 6/26/2017.
 */
public class Assets {
    //
    private final AssetManager manager;
    public final ArrayList<Vector2> raidPositions;
    public final ArrayList<Vector2> bossIconPosition;
    public final ArrayList<Vector2> talentPositions;


    // fonts
    private final String floatingFnt = "fonts/floating_font.fnt";
    private final String manaFnt = "fonts/mana_font.fnt";
    private final String cooldownFnt = "fonts/cooldown_font.fnt";
    public final String talentTooltipFont = "fonts/talent_tooltip_font.fnt";
    public final String mapTitle = "fonts/title_font.fnt";
    private final String mapDescription = "map_state/description.fnt";
    // --Commented out by Inspection (5/29/2018 8:13 PM):public String gameFont = "fonts/chela_one_regular.ttf";

    public final String gameFont16 = "fonts/game_font_small.fnt";
    public final String gameFont18 = "fonts/game_font_18.fnt";
    public final String gameFont24 = "fonts/game_font_medium.fnt";
    public final String gameFont32 = "fonts/game_font_large.fnt";
    public final String gameFont45 = "fonts/game_font_xlarge.fnt";
    public final String gameFontB16 = "fonts/game_font_small_border.fnt";
    public final String gameFontB24 = "fonts/game_font_medium_border.fnt";
    public final String gameFontB32 = "fonts/game_font_large_border.fnt";
    public final String gameFontB45 = "fonts/game_font_xlarge_border.fnt";
    public final String cdFont = "fonts/cd_font.fnt";


    // pngss
    private final String continuousRenewalIcon = "talent_state/continuous_renewal.png";
    public final String lifeboomIcon = "icons/lifeboom.png";
    public final String unstableMagicIcon = "icons/unstable_magic_icon.png";
    private final String burstHealerIcon = "talent_state/burst_healer.png";
    public final String innerFocusIcon = "icons/inner_focus.png";
    public final String diseaseIcon = "icons/disease_icon.png";
    public final String workTogetherIcon = "icons/work_together.png";
    public final String selectedTalent = "icons/selected.png";
    private final String talentWindow = "talent_state/talent_window.png";
    public final String talentBg = "talent_bg.png";
    private final String talentStateBg = "talent_state/bg.png";
    private final String doneButton = "talent_state/done_button.png";
    private final String toolTipFrame = "talent_state/tooltip_frame.png";
    private final String miniBossIcon = "icons/level_mini_boss.png";
    private final String bossIcon = "icons/level_boss.png";
    public final String mmBG = "main_menu_state/bg.png";
    public final String mmBG2 = "main_menu_state/bg2.png";
    public final String mmBG3 = "main_menu_state/bg3.png";
    public final String mmBG4 = "main_menu_state/main.png";
    public final String blackPanel = "black_panel.png";

    private final String mmPlayButtonIdle = "main_menu_state/play_button_idle.png";
    private final String mmPlayButtonHover = "main_menu_state/play_button_hover.png";
    private final String mmContinueButtonIdle = "main_menu_state/play_button_idle.png";
    private final String mmContinueButtonHover = "main_menu_state/play_button_hover.png";
    public final String dpsIcon = "icons/dps_role_icon.png";
    public final String healerIcon = "icons/healer_role_icon.png";
    public final String tankIcon = "icons/tank_role_icon.png";
    public final String healIcon = "icons/heal_icon.png";
    public final String renewIcon = "icons/renew_icon.png";
    public final String barrierIcon = "icons/barrier_icon.png";
    public final String flashIcon = "icons/flash_heal_icon.png";
    public final String deathIcon = "icons/death_icon.png";
    private final String hoggerName = "hogger_name.png";
    public final String battleBg1 = "battle_bg1.png";
    public final String battleBg2 = "battle_bg2.png";
    public final String battleBg3 = "battle_bg3.png";
    public final String blackBar = "black_bar.png";
    private final String whiteBar = "white_bar.png";
    private final String redBar = "red_bar.png";
    private final String redOutlineBar = "red_outline_bar.png";
    private final String greenBar = "green_bar.png";
    public final String yellowFill = "yellow_hp_fill.png";
    public final String redFill = "red_hp_fill.png";
    public final String greenFill = "green_hp_fill.png";
    public final String purpleFill = "purple_fill.png";
    public final String raidFrameIdle = "raid_frame_idle_v2.png";
    public final String raidFrameSelected = "raid_frame_selected_v2.png";
    public final String hpManaBar = "hp_mana_bar.png";
    public final String manaFill = "mana_fill.png";
    private final String castBar = "casting_bar.png";
    private final String cooldownBar = "cooldown_bar.png";
    public final String spellBar = "spell_bar.png";
    private final String bossLocation = "map_state/boss_location.png";
    public final String infoFrame = "map_state/info_frame.png";
    public final String infoFrame2 = "map_state/info_frame_2.png";
    private final String spellButton = "map_state/spells_button.png";
    private final String talentButton = "map_state/talent_button.png";
    private final String infoButton = "map_state/info_button.png";
    public final String startButton = "map_state/start_button.png";
    private final String selectedLevel = "map_state/selected_level.png";
    public final String bleedIcon = "icons/bleed_icon.png";
    private final String wampusCatName = "wampus_cat_name.png";
    public final String disableBG = "disable_bg.png";
    public final String endGameFrame = "end_game_frame.png";
    private final String finishButton = "finish_button.png";
    private final String resetButton = "reset_button.png";
    private final String leaveButton = "leave_button.png";
    private final String youWin = "you_win_text.png";
    private final String youWiped = "you_wiped_text.png";
    public final String mapOuterFrame = "map_state/map_outer_frame.png";
    public final String mapInnerFrame = "map_state/map_inner_frame.png";
    public final String button = "button.png";
    public final String smallButton = "small_button.png";
    public final String buttonHighlight = "button_select.png";
    public final String title = "logo/pocket_healer_logo.png";
    public final String poisonIcon = "icons/poison_icon.png";
    public final String agonyIcon = "icons/agony_icon.png";
    public final String corruptionIcon = "icons/corruption_icon.png";
    public final String seedOfCorruptionIcon = "icons/seed_of_corruption_icon.png";
    public final String venomIcon = "icons/venom_icon.png";

    private final String mapBg1 = "map_state/map.png";
    public final String biteIcon = "icons/bite_icon.png";
    public final String boilIcon = "icons/boil_icon.png";
    public final String smiteIcon = "icons/smite_icon.png";
    public final String holyNovaIcon = "icons/holy_nova_icon.png";
    public final String prayerOfMendingIcon = "icons/prayer_of_mending_icon.png";
    public final String greaterHealerIcon = "icons/greater_heal_icon.png";
    public final String lightWellIcon = "icons/light_well_icon.png";
    public final String divineHymnIcon = "icons/divine_hymn_icon.png";
    public final String renewingNovaIcon = "icons/renewing_nova_icon.png";
    public final String disciplineIcon = "icons/discipline_icon.png";
    public final String criticalHealer2Icon = "icons/critical_healerii_icon.png";
    private final String resurgenceIcon = "icons/resurgence_icon.png";
    public final String aodIcon = "icons/aod_icon.png";
    public final String superNovaIcon = "icons/super_nova_icon.png";
    public final String idleLine = "icons/idle_line.png";
    public final String selectedLine = "icons/selected_line.png";
    public final String shadowIcon = "icons/shadow_icon.png";
    private final String talentCalculatorFrame = "talent_calculator_frame.png";
    public final String blankIcon = "icons/blank_icon.png";
    public final String arrowPointer = "arrow.png";
    private final String defeatedBossIcon = "map_state/defeated_boss.png";
    public final String spellBG = "spell_bg.png";
    public final String dispelIcon = "icons/dispel_icon.png";
    public final String burnIcon = "icons/burn_icon.png";
    public final String penanceIcon = "icons/penance_icon.png";

    public final String healingAbsorbIcon = "icons/healing_absorb_icon.png";
    public final String webIcon = "icons/web_icon.png";
    public final String swarmingShadowIcon = "icons/swarming_shadow_icon.png";
    public final String divineProtectionIcon = "icons/divine_protection_icon.png";
    public final String blessedGardenIcon = "icons/blessed_garden_icon.png";
    public final String stoneSkinIcon = "icons/stone_skin_icon.png";
    public final String sunderIcon = "icons/sunder_icon.png";


    private final String map1 = "map_state/map_level_1.png";
    private final String map2 = "map_state/map_level_2.png";
    private final String map3 = "map_state/map_level_3.png";
    private final String pageRight = "map_state/page_right_icon.png";
    private final String pageLeft = "map_state/page_left_icon.png";
    public final String ripIcon = "icons/rip_icon.png";
    public final String igniteIcon = "icons/ignite_icon.png";

    public final String exitButton = "map_state/exit_button.png";

    //credits
    public final String creditsLayer1 = "credits_state/credits_layer_1.png";
    public final String creditsLayer2 = "credits_state/credits_layer_2.png";
    public final String creditsLayer3 = "credits_state/credits_layer_3.png";
    public final String creditsLayer4 = "credits_state/credits_layer_4.png";
    public final String creditsLayer5 = "credits_state/credits_layer_5.png";




    public final ArrayList<String> maps;

    // music
    public final String mainMusic = "sfx/main_music.ogg";
    public final String victoryMusic = "sfx/Music/victory_music.wav";
    public final String defeatMusic = "sfx/Music/defeat_music.wav";
    public final String battleMusic = "sfx/battle_music.ogg";
    public final String creditsMusic = "sfx/credit_music.ogg";
    public final String stage1BattleMusic = "sfx/Music/stage1_battle_music.wav";
    public final String stage2BattleMusic = "sfx/Music/stage2_battle_music.wav";
    public final String stage3BattleMusic = "sfx/Music/stage3_battle_music.wav";
    public final String lastBossBattle1Music = "sfx/Music/last_boss_1_battle_music.wav";
    public final String lastBossBattle2Music = "sfx/Music/last_boss_2_battle_music.wav";
    public final String lastBossBattle3Music = "sfx/Music/last_boss_3_battle_music.wav";

    // sounds
    // OLD CODE
    //public final String barrierSFX ="sfx/barrier_sfx.mp3";
    public final String castingSFX ="sfx/casting_sfx.mp3";
    public final String cdSFX ="sfx/SFX/cd_sfx.wav";
    public final String hotSFX ="sfx/hot_sfx.mp3";


    public final String barrierSFX = "sfx/SFX/p_spells/barrier_sfx.wav";
    public final String blessedGardenSFX = "sfx/SFX/p_spells/blessed_garden_sfx.wav";
    public final String dispelSFX = "sfx/SFX/p_spells/dispel_sfx.wav";
    public final String divineHymnSFX = "sfx/SFX/p_spells/divine_hymn_sfx.wav";
    public final String divineProtectionSFX = "sfx/SFX/p_spells/divine_protection_sfx.wav";
    public final String greaterHealSFX = "sfx/SFX/p_spells/greater_heal_sfx.wav";
    public final String healSFX = "sfx/SFX/p_spells/heal_sfx.wav";
    public final String holyNovaSFX = "sfx/SFX/p_spells/holy_nova_sfx.wav";
    public final String holyShockSFX = "sfx/SFX/p_spells/holy_shock_sfx.wav";
    public final String lightwellSFX = "sfx/SFX/p_spells/lightwell_sfx.wav";
    public final String renewSFX = "sfx/SFX/p_spells/renew_sfx.wav";
    public final String smiteSFX = "sfx/SFX/p_spells/smite_sfx.wav";
    //penance package
    public final String penanceHit1SFX = "sfx/SFX/p_spells/penance/penance_hit1_sfx.wav";
    public final String penanceHit2SFX = "sfx/SFX/p_spells/penance/penance_hit2_sfx.wav";
    public final String penanceHit3SFX = "sfx/SFX/p_spells/penance/penance_hit3_sfx.wav";
    public final String penanceHit4SFX = "sfx/SFX/p_spells/penance/penance_hit4_sfx.wav";
    public final String penanceTriggerSFX = "sfx/SFX/p_spells/penance/penance_trigger_sfx.wav";
    //pom
    public final String pomHitSFX = "sfx/SFX/p_spells/pom/pom_hit_sfx.wav";
    public final String pomTriggerSFX = "sfx/SFX/p_spells/pom/pom_trigger_sfx.wav";
    // boss sounds
    public final String firecastSFX = "sfx/SFX/b_spells/firecast_sfx.wav";
    public final String bigDebuffSFX = "sfx/SFX/b_spells/big_debuff_sfx.wav";
    public final String bigFireballSFX = "sfx/SFX/b_spells/big_fireball_sfx.wav";
    public final String debuffSFX = "sfx/SFX/b_spells/debuff_sfx.wav";
    public final String explosionSFX = "sfx/SFX/b_spells/explosion_sfx.wav";
    public final String finishImpactSFX = "sfx/SFX/b_spells/finish_impact_sfx.wav";
    public final String fireBreathSFX = "sfx/SFX/b_spells/fire_breath_sfx.wav";
    public final String fireballSFX = "sfx/SFX/b_spells/fireball_sfx.wav";
    public final String unstableProblastSFX = "sfx/SFX/b_spells/u_pyroblast_sfx.wav";
    public final String swordSwing1SFX = "sfx/SFX/b_spells/flurry/ss1_sfx.wav";
    public final String swordSwing2SFX = "sfx/SFX/b_spells/flurry/ss2_sfx.wav";
    public final String swordSwing3SFX = "sfx/SFX/b_spells/flurry/ss3_sfx.wav";
    public final String swordSwing4SFX = "sfx/SFX/b_spells/flurry/ss4_sfx.wav";
    public final String swordSwing5SFX = "sfx/SFX/b_spells/flurry/ss5_sfx.wav";
    public final String swordSwing6SFX = "sfx/SFX/b_spells/flurry/ss6_sfx.wav";
    public final String earthquake1SFX = "sfx/SFX/b_spells/earthquake/earthquake1_sfx.wav";
    public final String earthquake2SFX = "sfx/SFX/b_spells/earthquake/earthquake2_sfx.wav";
    public final String earthquake3SFX = "sfx/SFX/b_spells/earthquake/earthquake3_sfx.wav";
    public final String chargeSFX = "sfx/SFX/b_spells/charge_sfx.wav";
    public final String biteSFX = "sfx/SFX/b_spells/bite_sfx.wav";
    public final String stabSFX = "sfx/SFX/b_spells/stab_sfx.wav";
    public final String magicStabSFX = "sfx/SFX/b_spells/magic_stab_sfx.wav";
    public final String darkMagicSFX = "sfx/SFX/b_spells/dark_magic_sfx.wav";
    public final String electricStrikeSFX = "sfx/SFX/b_spells/electric_strike_sfx.wav";
    // skin
    private final String uiSkin = "pocket_healer_ui.json";

    public Assets() {
        manager = new AssetManager();
        raidPositions = new ArrayList<>();
        bossIconPosition = new ArrayList<>();
        talentPositions = new ArrayList<>();
        maps = new ArrayList<>();
        maps.add(map1);
        maps.add(map2);
        maps.add(map3);
    }

    public void load()  {
        loadImages();
        loadSounds();
        loadFonts();
        loadPositions();
        loadSkin();
    }

    private void loadImages() {
        TextureLoader.TextureParameter param = new TextureLoader.TextureParameter();
        param.minFilter = Texture.TextureFilter.MipMap;
        param.genMipMaps = true;

        manager.load(dpsIcon, Texture.class, param);
        manager.load(healerIcon, Texture.class, param);
        manager.load(tankIcon, Texture.class, param);

        manager.load(healIcon, Texture.class, param);
        manager.load(deathIcon, Texture.class, param);
        manager.load(renewIcon, Texture.class, param);
        manager.load(barrierIcon, Texture.class, param);
        manager.load(flashIcon, Texture.class, param);

        manager.load(blackBar, Texture.class, param);
        manager.load(whiteBar, Texture.class, param);
        manager.load(raidFrameIdle, Texture.class, param);
        manager.load(raidFrameSelected, Texture.class, param);
        manager.load(redBar, Texture.class, param);
        manager.load(redOutlineBar, Texture.class, param);
        manager.load(greenBar, Texture.class, param);
        manager.load(redFill, Texture.class, param);
        manager.load(yellowFill, Texture.class, param);
        manager.load(greenFill, Texture.class, param);
        manager.load(purpleFill, Texture.class, param);


        manager.load(hpManaBar, Texture.class, param);
        manager.load(manaFill, Texture.class, param);

        manager.load(castBar, Texture.class, param);
        manager.load(cooldownBar, Texture.class, param);

        manager.load(hoggerName, Texture.class, param);

        manager.load(battleBg1, Texture.class, param);
        manager.load(battleBg2, Texture.class, param);
        manager.load(battleBg3, Texture.class, param);

        manager.load(continuousRenewalIcon, Texture.class, param);
        manager.load(lifeboomIcon, Texture.class, param);
        manager.load(unstableMagicIcon, Texture.class, param);
        manager.load(burstHealerIcon, Texture.class, param);
        manager.load(innerFocusIcon, Texture.class, param);
        manager.load(workTogetherIcon, Texture.class, param);
        manager.load(selectedTalent, Texture.class, param);
        manager.load(talentWindow, Texture.class, param);
        manager.load(diseaseIcon, Texture.class, param);
        manager.load(spellBar, Texture.class, param);

        manager.load(talentBg, Texture.class, param);
        manager.load(doneButton, Texture.class, param);
        manager.load(toolTipFrame, Texture.class, param);
        manager.load(talentStateBg, Texture.class, param);

        manager.load(miniBossIcon, Texture.class, param);
        manager.load(bossIcon, Texture.class, param);

        manager.load(mmBG, Texture.class, param);
        manager.load(mmBG2, Texture.class, param);
        manager.load(mmBG3, Texture.class, param);
        manager.load(mmBG4, Texture.class, param);

        manager.load(mmPlayButtonIdle, Texture.class, param);
        manager.load(mmPlayButtonHover, Texture.class, param);
        manager.load(mmContinueButtonIdle, Texture.class, param);
        manager.load(mmContinueButtonHover, Texture.class, param);

        manager.load(bossLocation, Texture.class, param);
        manager.load(exitButton, Texture.class, param);
        manager.load(infoFrame, Texture.class, param);
        manager.load(infoFrame2, Texture.class, param);
        manager.load(mapBg1, Texture.class, param);
        manager.load(spellButton, Texture.class, param);
        manager.load(talentButton, Texture.class, param);
        manager.load(infoButton, Texture.class, param);
        manager.load(startButton, Texture.class, param);
        manager.load(selectedLevel, Texture.class, param);
        manager.load(bleedIcon, Texture.class, param);
        manager.load(wampusCatName, Texture.class, param);

        manager.load(disableBG, Texture.class, param);
        manager.load(endGameFrame, Texture.class, param);
        manager.load(finishButton, Texture.class, param);
        manager.load(resetButton, Texture.class, param);
        manager.load(leaveButton, Texture.class, param);
        manager.load(youWin, Texture.class, param);
        manager.load(youWiped, Texture.class, param);

        manager.load(mapInnerFrame, Texture.class, param);
        manager.load(mapOuterFrame, Texture.class, param);

        manager.load(button, Texture.class, param);
        manager.load(smallButton, Texture.class, param);

        manager.load(title, Texture.class, param);

        manager.load(poisonIcon, Texture.class, param);
        manager.load(diseaseIcon, Texture.class, param);

        manager.load(biteIcon, Texture.class, param);
        manager.load(boilIcon, Texture.class, param);

        manager.load(smiteIcon, Texture.class, param);
        manager.load(holyNovaIcon, Texture.class, param);
        manager.load(greaterHealerIcon, Texture.class, param);
        manager.load(lightWellIcon, Texture.class, param);
        manager.load(divineHymnIcon, Texture.class, param);

        manager.load(renewingNovaIcon, Texture.class, param);
        manager.load(disciplineIcon, Texture.class, param);
        manager.load(criticalHealer2Icon, Texture.class, param);
        manager.load(resurgenceIcon, Texture.class, param);
        manager.load(aodIcon, Texture.class, param);
        manager.load(superNovaIcon, Texture.class, param);
        manager.load(idleLine, Texture.class, param);
        manager.load(selectedLine, Texture.class, param);
        manager.load(shadowIcon, Texture.class, param);
        manager.load(talentCalculatorFrame, Texture.class, param);
        manager.load(buttonHighlight, Texture.class, param);
        manager.load(blankIcon, Texture.class, param);
        
        manager.load(arrowPointer, Texture.class, param);
        manager.load(defeatedBossIcon, Texture.class, param);
        manager.load(spellBG, Texture.class, param);

        manager.load(map1, Texture.class, param);
        manager.load(map2, Texture.class, param);
        manager.load(map3, Texture.class, param);
        manager.load(pageRight, Texture.class, param);
        manager.load(pageLeft, Texture.class, param);
        manager.load(prayerOfMendingIcon, Texture.class, param);
        manager.load(dispelIcon, Texture.class, param);
        manager.load(burnIcon, Texture.class, param);
        manager.load(agonyIcon, Texture.class, param);
        manager.load(corruptionIcon, Texture.class, param);
        manager.load(seedOfCorruptionIcon, Texture.class, param);

        manager.load(healingAbsorbIcon, Texture.class, param);
        manager.load(webIcon, Texture.class, param);
        manager.load(swarmingShadowIcon, Texture.class, param);
        manager.load(venomIcon, Texture.class, param);
        manager.load(penanceIcon, Texture.class, param);
        manager.load(divineProtectionIcon, Texture.class, param);
        manager.load(blessedGardenIcon, Texture.class, param);
        manager.load(stoneSkinIcon, Texture.class, param);

        manager.load(ripIcon, Texture.class, param);
        manager.load(igniteIcon, Texture.class, param);
        manager.load(sunderIcon, Texture.class, param);
        manager.load(creditsLayer1, Texture.class, param);
        manager.load(creditsLayer2, Texture.class, param);
        manager.load(creditsLayer3, Texture.class, param);
        manager.load(creditsLayer4, Texture.class, param);
        manager.load(creditsLayer5, Texture.class, param);
        manager.load(exitButton, Texture.class, param);

    }

    private void loadSounds() {
        manager.load(battleMusic, Music.class);
        manager.load(mainMusic, Music.class);
        manager.load(barrierSFX, Sound.class);
        manager.load(castingSFX, Sound.class);
        manager.load(healSFX, Sound.class);
        manager.load(hotSFX, Sound.class);
        manager.load(creditsMusic, Music.class);

        manager.load(blessedGardenSFX, Sound.class);
        manager.load(dispelSFX, Sound.class);
        manager.load(divineHymnSFX, Sound.class);
        manager.load(divineProtectionSFX, Sound.class);
        manager.load(greaterHealSFX, Sound.class);
        manager.load(holyNovaSFX, Sound.class);
        manager.load(holyShockSFX, Sound.class);
        manager.load(lightwellSFX, Sound.class);
        manager.load(renewSFX, Sound.class);
        manager.load(smiteSFX, Sound.class);

        manager.load(penanceHit1SFX, Sound.class);
        manager.load(penanceHit2SFX, Sound.class);
        manager.load(penanceHit3SFX, Sound.class);
        manager.load(penanceHit4SFX, Sound.class);
        manager.load(penanceTriggerSFX, Sound.class);

        manager.load(pomHitSFX, Sound.class);
        manager.load(pomTriggerSFX, Sound.class);

        manager.load(stage1BattleMusic, Music.class);
        manager.load(stage2BattleMusic, Music.class);
        manager.load(stage3BattleMusic, Music.class);
        manager.load(lastBossBattle1Music, Music.class);
        manager.load(lastBossBattle2Music, Music.class);
        manager.load(lastBossBattle3Music, Music.class);
        manager.load(victoryMusic, Music.class);
        manager.load(defeatMusic, Music.class);

        manager.load(firecastSFX, Sound.class);
        manager.load(bigDebuffSFX, Sound.class);
        manager.load(bigFireballSFX, Sound.class);
        manager.load(debuffSFX, Sound.class);
        manager.load(explosionSFX, Sound.class);
        manager.load(finishImpactSFX, Sound.class);
        manager.load(fireBreathSFX,Sound.class);
        manager.load(fireballSFX, Sound.class);
        manager.load(unstableProblastSFX, Sound.class);

        manager.load(swordSwing1SFX, Sound.class);
        manager.load(swordSwing2SFX, Sound.class);
        manager.load(swordSwing3SFX, Sound.class);
        manager.load(swordSwing4SFX, Sound.class);
        manager.load(swordSwing5SFX, Sound.class);
        manager.load(swordSwing6SFX, Sound.class);

        manager.load(earthquake1SFX,Sound.class);
        manager.load(earthquake2SFX,Sound.class);
        manager.load(earthquake3SFX,Sound.class);
        manager.load(chargeSFX,Sound.class);
        manager.load(stabSFX,Sound.class);
        manager.load(magicStabSFX,Sound.class);
        manager.load(biteSFX,Sound.class);
        manager.load(darkMagicSFX,Sound.class);
        manager.load(electricStrikeSFX,Sound.class);
        manager.load(cdSFX, Sound.class);

    }

    private void loadPositions() {
        // raid position
        for(int i = 0; i < 6; i++) {
            for(int j = 0; j < 3; j++) {
                //raidPositions.add(new Vector2(20+(j*154), 660-(i*79)));
                raidPositions.add(new Vector2(20+(j*147), 650-(i*70)));
            }
        }

        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 4; j++) {
                talentPositions.add(new Vector2(80+(i*120),600-(j*120)));
            }
        }

        // boss icon position
        bossIconPosition.add(new Vector2(100,270)); // wild boar
        bossIconPosition.add(new Vector2(50,30)); // giant hornet
        bossIconPosition.add(new Vector2(235,150)); // golem
        bossIconPosition.add(new Vector2(330,30)); // nax's wolf
        bossIconPosition.add(new Vector2(330,270)); // nax

        bossIconPosition.add(new Vector2(40,60)); // hogger
        bossIconPosition.add(new Vector2(60,230)); // wampus
        bossIconPosition.add(new Vector2(180,220)); // proctor
        bossIconPosition.add(new Vector2(310,220)); // apprentice
        bossIconPosition.add(new Vector2(315,40)); // sorcerer

        bossIconPosition.add(new Vector2(180,30)); // mother spider
        bossIconPosition.add(new Vector2(180,150)); // zombie horde
        bossIconPosition.add(new Vector2(220,250)); // blood queen
        bossIconPosition.add(new Vector2(160,250)); // Ion
        bossIconPosition.add(new Vector2(185,300)); // death dragon
        bossIconPosition.add(new Vector2(180,400)); // test boss

    }

    private void loadFonts() {
        manager.load(floatingFnt, BitmapFont.class);
        manager.load(manaFnt, BitmapFont.class);
        manager.load(cooldownFnt, BitmapFont.class);
        manager.load(talentTooltipFont, BitmapFont.class);
        manager.load(mapTitle, BitmapFont.class);
        manager.load(mapDescription, BitmapFont.class);

        manager.load(gameFont16,BitmapFont.class);
        manager.load(gameFont18,BitmapFont.class);
        manager.load(gameFont24,BitmapFont.class);
        manager.load(gameFont32,BitmapFont.class);
        manager.load(gameFont45,BitmapFont.class);
        manager.load(gameFontB16,BitmapFont.class);
        manager.load(gameFontB24,BitmapFont.class);
        manager.load(gameFontB32,BitmapFont.class);
        manager.load(gameFontB45,BitmapFont.class);
        manager.load(cdFont, BitmapFont.class);
    }

    private void loadSkin()  {
        manager.load(uiSkin, Skin.class);
    }

    public Texture getTexture(String filename)   {
        return manager.get(filename, Texture.class);
    }

    public Sound getSound(String filename)   {
        return manager.get(filename, Sound.class);
    }

    public Music getMusic(String filename)   {
        return manager.get(filename, Music.class);
    }

    public BitmapFont getFont(String filename)   {
        return manager.get(filename, BitmapFont.class);
    }

    public BitmapFont getFont(boolean isBorder)   {
        if(isBorder)    {
            return manager.get(gameFontB16, BitmapFont.class);
        }
        else {
            return manager.get(gameFont16, BitmapFont.class);
        }
    }

    public BitmapFont getFontTitle()   {
        return manager.get(mapTitle, BitmapFont.class);
    }

    public Skin getSkin()   {
        return manager.get(uiSkin, Skin.class);
    }

    public float getProgress()  {
        return manager.getProgress();
    }

    public boolean update() {
        return manager.update();
    }
}
