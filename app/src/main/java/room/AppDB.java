package room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import room.Game;

@Database(entities = {Game.class},version = 1)
public abstract class AppDB extends RoomDatabase {
    public abstract GameDao gameDao();
}
