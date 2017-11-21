import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class DragonMaker {
    private int scaleThickness = 5;
    private int clawSharpness = 5;
    private int wingStrength = 5;
    private int fireBreath = 5;

    public String makeDecision(Game gameDetails, String weatherCode){
        JSONObject dragon = null;
        /* Weather Codes:
        NMR - normal
        SRO - storm
        FUNDEFINEDG - fog
        T E - long dry
        HVA - floods */
        switch (weatherCode) {
            case "NMR":
                dragon = pickDragon(gameDetails);
                break;
            case "SRO":
                // Dragon safe inside, storm will take care of the knight.
                break;
            case "FUNDEFINEDG":
                dragon = pickDragon(gameDetails);
                break;
            case "T E":
                // Balanced approach needed
                dragon = new JSONObject()
                        .put("dragon", new JSONObject()
                                .put("scaleThickness", scaleThickness)
                                .put("clawSharpness", clawSharpness)
                                .put("wingStrength", wingStrength)
                                .put("fireBreath", fireBreath));
                break;
            case "HVA":
                // Fire useless, sharper claws needed
                dragon = new JSONObject()
                        .put("dragon", new JSONObject()
                                .put("scaleThickness", 0)
                                .put("clawSharpness", 10)
                                .put("wingStrength", 10)
                                .put("fireBreath", 0));
                break;
            default:
                break;
        }
        if (dragon != null) {
            return dragon.toString();
        } else {
            return "";
        }
    }

    private JSONObject pickDragon(Game gameDetails){
        JSONObject dragon;

        ArrayList<KnightSkill> skills = new ArrayList<>();
        skills.add(new KnightSkill("attack", gameDetails.getAttack()));
        skills.add(new KnightSkill("armor", gameDetails.getArmor()));
        skills.add(new KnightSkill("agility", gameDetails.getAgility()));
        skills.add(new KnightSkill("endurance", gameDetails.getEndurance()));

        Collections.sort(skills, Comparator.comparing(KnightSkill::getValue));

        int iteration = 0;
        int overKill;
        int pointDeficit = 0;
        for (int i = 3; i >= 0; i--) {
            if (iteration == 0) {
                iteration++;
                if (skills.get(i).getValue() < 8) {
                    overKill = skills.get(i).getValue() + 2;
                    pointDeficit = 2;
                } else {
                    overKill = 10;
                    pointDeficit = 10 - skills.get(i).getValue();
                }
                switch (skills.get(i).getName()) {
                    case "attack":
                        scaleThickness = overKill;
                        break;
                    case "armor":
                        clawSharpness = overKill;
                        break;
                    case "agility":
                        wingStrength = overKill;
                        break;
                    case "endurance":
                        fireBreath = overKill;
                        break;
                    default:
                        break;
                }
            } else if (iteration < 3) {
                iteration++;
                if (skills.get(i).getValue() > 0 && pointDeficit > 0) {
                    pointDeficit--;
                    switch (skills.get(i).getName()) {
                        case "attack":
                            scaleThickness = skills.get(i).getValue() - 1;
                            break;
                        case "armor":
                            clawSharpness = skills.get(i).getValue() - 1;
                            break;
                        case "agility":
                            wingStrength = skills.get(i).getValue() - 1;
                            break;
                        case "endurance":
                            fireBreath = skills.get(i).getValue() - 1;
                            break;
                        default:
                            break;
                    }
                } else {
                    copySkill(skills.get(i));
                }
            } else {
                copySkill(skills.get(i));
            }}
        dragon = new JSONObject()
                .put("dragon", new JSONObject()
                        .put("scaleThickness", scaleThickness)
                        .put("clawSharpness", clawSharpness)
                        .put("wingStrength", wingStrength)
                        .put("fireBreath", fireBreath));
        return dragon;
    }

    private void copySkill(KnightSkill skill){
        switch (skill.getName()) {
            case "attack":
                scaleThickness = skill.getValue();
                break;
            case "armor":
                clawSharpness = skill.getValue();
                break;
            case "agility":
                wingStrength = skill.getValue();
                break;
            case "endurance":
                fireBreath = skill.getValue();
                break;
            default:
                break;
        }
    }
}
