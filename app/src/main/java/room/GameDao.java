package room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface GameDao {

    @Query("SELECT * FROM game")
    Flowable<List<Game>> getAll();

    @Insert
    void insert(Game g);

    @Delete
    void delete(Game g);

    @Update
    void update(Game g);
}
