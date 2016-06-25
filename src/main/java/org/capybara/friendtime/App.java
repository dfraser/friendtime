package org.capybara.friendtime;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Hello world!
 *
 */
public class App extends Application
{

    private List<FriendClock> friends = new ArrayList<>();
    private final ScheduledExecutorService friendUpdateService = Executors.newSingleThreadScheduledExecutor();


    private void makeDummyData() {
        friends.add(new FriendClock("Dan", ZoneId.of("America/Toronto"),"Toronto"));
        friends.add(new FriendClock("Kat", ZoneId.of("Australia/Brisbane"),"Brisbane"));
        friends.add(new FriendClock("Wendy", ZoneId.of("Europe/Vienna"),"Vienna"));
    }



    @Override
    public void init() throws Exception {
        super.init();

        makeDummyData();
        // load prefs from disk etc
        friendUpdateService.scheduleAtFixedRate(new FriendUpdateTask(),0, 250, TimeUnit.MILLISECONDS);

    }

    @Override
    public void stop() throws Exception {
        super.stop();
        friendUpdateService.shutdownNow();
    }


    public void start(Stage primaryStage) throws Exception {

//        Button btn = new Button();
//        btn.setText("Say 'Hello World'");
//        btn.setOnAction((event) -> {
//            System.out.println("Hello World!");
//        });



        FlowPane root = new FlowPane();
//        root.getChildren().add(btn);

        for (FriendClock friend : friends) {
            friend.updateTime();
            root.getChildren().add(friend.getNode());
        }

        Scene scene = new Scene(root, 300, 250);

        primaryStage.setTitle("Friend Time");
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }

    private class FriendUpdateTask implements Runnable {


        @Override
        public void run() {
            friends.forEach(FriendClock::updateTime);
        }
    }
}
