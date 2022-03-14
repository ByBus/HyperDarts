package dartsgame.persistense;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameRepository extends CrudRepository<GameEntity, Long> {

    List<GameEntity> findAllByPlayerOneOrPlayerTwoAndGameStatusNotOrderByIdDesc(String playerOne, String playerTwo, GameStatus gameStatus);

    List<GameEntity> findAllByOrderByIdDesc();

}
