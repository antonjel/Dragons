public class Game {
    private int gameId;
    private String name;
    private int attack;
    private int armor;
    private int agility;
    private int endurance;

    public Game(int gameId, String name, int attack, int armor, int agility, int endurance){
        this.gameId = gameId;
        this.name = name;
        this.attack = attack;
        this.armor = armor;
        this.agility = agility;
        this.endurance = endurance;
    }

    public int getGameId() {
        return gameId;
    }

    public int getAttack() {
        return attack;
    }

    public int getArmor() {
        return armor;
    }

    public int getAgility() {
        return agility;
    }

    public int getEndurance() {
        return endurance;
    }

}
