package room;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.MapInfo;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Game implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "GameName")
    private String gameName;

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    @ColumnInfo(name = "Player1")
    private String player1;

    @ColumnInfo(name = "Player2")
    private String player2;

    @MapInfo(keyColumn = "Player1",valueColumn = "Player2")

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPlayer1() {
        return player1;
    }

    public void setPlayer1(String player1) {
        this.player1 = player1;
    }

    public String getPlayer2() {
        return player2;
    }

    public void setPlayer2(String player2) {
        this.player2 = player2;
    }
}
