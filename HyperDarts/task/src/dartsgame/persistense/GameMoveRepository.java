package dartsgame.persistense;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
interface GameMoveRepository extends CrudRepository<GameMoveEntity, Long> {
    List<GameMoveEntity> findAllByGameId(long gameId);

    GameMoveEntity findTopByGameIdOrderByMoveDesc(long gameId);

    GameMoveEntity findByGameIdAndMove(long gameId, int move);

    void deleteAllByGameIdAndMoveGreaterThan(long gameId, int move);
}
