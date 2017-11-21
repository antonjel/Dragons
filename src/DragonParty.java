import java.text.DecimalFormat;

public class DragonParty {
    private static int nrOfRuns = 100;
    private static int nrOfGamesPlayed = 0;
    public static int nrOfWins = 0;
    public static int nrOfLosses = 0;

    public static void main(String[] args){
        while (nrOfRuns > 0){
            nrOfGamesPlayed++;

            DataFetcher dataFetcher = new DataFetcher();
            Game gameDetails = dataFetcher.getGameData();

            System.out.println("Game #: " + nrOfGamesPlayed);
            System.out.print("Result for GameID " + gameDetails.getGameId() + ": ");

            String weatherCode = dataFetcher.getWeatherData(gameDetails.getGameId());

            DragonMaker dragonMaker = new DragonMaker();
            String dragon = dragonMaker.makeDecision(gameDetails, weatherCode);

            SolutionResponseSender solution = new SolutionResponseSender();
            solution.sendSolution(gameDetails.getGameId(), dragon);

            nrOfRuns--;
        }

        double winPercentage = (double) nrOfWins / nrOfGamesPlayed * 100;

        DecimalFormat df = new DecimalFormat("###.##");
        df.setMaximumFractionDigits(2);

        System.out.println("Games Played: " + nrOfGamesPlayed);
        System.out.println("Games Won: " + nrOfWins);
        System.out.println("Games Lost: " + nrOfLosses);
        System.out.println("Game Win Percentage: " + df.format(winPercentage) + "%");
    }
}
