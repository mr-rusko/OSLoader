package org.ui.hs;

import javax.swing.*;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import org.client.Constants;

/**
 * Created by Kenneth on 6/15/2014.
 */
class HiscoresLookup {

    private static final String[] SKILL_NAMES = {
            "Overall", "Attack", "Defence", "Strength", "Constitution", "Ranged", "Prayer",
            "Magic", "Cooking", "Woodcutting", "Fletching", "Fishing", "Firemaking", "Crafting",
            "Smithing", "Mining", "Herblore", "Agility", "Thieving", "Slayer", "Farming",
            "Runecrafting", "Hunter", "Construction", "Summoning", "Dungeoneering", "Divination"};
    private final Map<Integer, Skill> skillMap = new LinkedHashMap<>();

    public HiscoresLookup(String username, boolean isRS3, boolean  isIron) {
        String page = "";
        try {
        	if (isIron){
        		page = Utilities.downloadString(isRS3 ? Constants.RS3_HISCORES_IRON + username : Constants.OLDSCHOOL_HISCORES_IRON + username, true);
        	} else {
        		page = Utilities.downloadString(isRS3 ? Constants.RS3_HISCORES_URL + username : Constants.OLDSCHOOL_HISCORES_URL + username, true);
        	}
            
        } catch (IOException exception) {
            exception.printStackTrace();
            String[] options = new String[]{"OK"};
            JOptionPane.showOptionDialog(null, "The player you searched does not exist or is free to play!", "Error looking up player", JOptionPane.PLAIN_MESSAGE, JOptionPane.ERROR_MESSAGE, null, options, options[0]);
        }

        int totalSkills = isRS3 ? 26 : 23;
        for (int i = 0; i <= totalSkills; i++) {
            String skillName = SKILL_NAMES[i];
            String skillData = page.split("\n")[i];
            skillMap.putIfAbsent(i, new Skill(skillName, Integer.parseInt(skillData.split(",")[0]), Integer.parseInt(skillData.split(",")[1]), Long.parseLong(skillData.split(",")[2])));
        }
    }

    public Map<Integer, Skill> getSkillMap() {
        return skillMap;
    }
}