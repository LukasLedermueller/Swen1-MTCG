package at.fhtw;

import at.fhtw.httpserver.utils.Router;
import at.fhtw.httpserver.server.Server;
import at.fhtw.mtcg.service.cards.CardService;
import at.fhtw.mtcg.service.cards.DeckService;
import at.fhtw.mtcg.service.game.ScoreService;
import at.fhtw.mtcg.service.game.StatsService;
import at.fhtw.mtcg.service.packages.PackageService;
import at.fhtw.mtcg.service.packages.TransactionService;
import at.fhtw.mtcg.service.trading.TradingService;
import at.fhtw.mtcg.service.users.SessionService;
import at.fhtw.mtcg.service.users.UserService;
import at.fhtw.sampleapp.service.echo.EchoService;
import at.fhtw.sampleapp.service.weather.WeatherService;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        Server server = new Server(10001, configureRouter());
        try {
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Router configureRouter()
    {
        Router router = new Router();
        router.addService("/weather", new WeatherService());
        router.addService("/echo", new EchoService());
        router.addService("/users", new UserService());
        router.addService("/sessions", new SessionService());
        router.addService("/cards", new CardService());
        router.addService("/deck", new DeckService());
        router.addService("/packages", new PackageService());
        router.addService("/transactions", new TransactionService());
        router.addService("/stats", new StatsService());
        router.addService("/score", new ScoreService());
        router.addService("/tradings", new TradingService());
        return router;
    }
}
