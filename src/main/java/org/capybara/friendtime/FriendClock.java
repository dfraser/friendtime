package org.capybara.friendtime;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.text.Text;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;

/**
 * Created by dfraser on 2016-06-25.
 */
public class FriendClock {
    private final String name;
    private final ZoneId zoneId;
    private final String location;

    private DateTimeFormatter friendTimeFormat = DateTimeFormatter.ofPattern("EEE HH:mm:ss");


    private StringProperty localTime = new SimpleStringProperty();

    public FriendClock(String name, ZoneId timeZone, String location) {
        this.name = name;
        this.zoneId = timeZone;
        this.location = location;
        updateTime();
    }

    public String getName() {
        return name;
    }

    public ZoneId getZoneId() {
        return zoneId;
    }

    public String getLocation() {
        return location;
    }

    public ZonedDateTime getTime() {
        return Instant.now().atZone(zoneId);
    }

    public String getFormattedDateTime(DateTimeFormatter format) {
        return format.format(Instant.now().atZone(zoneId));
    }

    public synchronized StringProperty localTimeProperty() {
        return localTime;
    }

    public synchronized void updateTime() {
        localTime.setValue(getFormattedDateTime(friendTimeFormat));
    }

    public GridPane getNode() {
        GridPane gp = new GridPane();
        gp.setPrefWidth(300);
        gp.setPadding(new Insets(10));
        gp.setHgap(10);
        gp.setVgap(5);
//        gp.setGridLinesVisible(true);
        Text name = new Text(getName());
        gp.setHalignment(name, HPos.LEFT);
        Text zone = new Text(getZoneId().toString());
        gp.setHalignment(zone, HPos.RIGHT);
        Text time = new Text();
        time.textProperty().bind(localTimeProperty());
        gp.setHalignment(time, HPos.RIGHT);
        Text city = new Text(getLocation());
        gp.setHalignment(city, HPos.LEFT);
        GridPane.setHgrow(time, Priority.ALWAYS);
        gp.add(name,0,0);
        gp.add(time,1,0);
        gp.add(zone,1,1);
        gp.add(city,0,1);
        return gp;
    }


    @Override
    public String toString() {
        return "FriendClock{" +
                "name='" + name + '\'' +
                ", zoneId=" + zoneId +
                ", location='" + location + '\'' +
                '}';
    }
}
