package dartsgame.persistense;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
interface GameRepository extends CrudRepository<GameEntity, Long> {

    List<GameEntity> findAllByPlayerOneAndGameStatusNotOrPlayerTwoAndGameStatusNotOrderByIdDesc(String playerOne,
                                                                                                GameStatus gameStatus1,
                                                                                                String playerTwo,
                                                                                                GameStatus gameStatus2);

    List<GameEntity> findAllByOrderByIdDesc();

    GameEntity findFirstByPlayerOneAndGameStatusInOrPlayerTwoAndGameStatusIn(String playerOne,
                                                                             List<GameStatus> statuses1,
                                                                             String playerTwo,
                                                                             List<GameStatus> statuses2,
                                                                             Sort sort);

}
