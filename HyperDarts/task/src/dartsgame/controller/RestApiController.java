package dartsgame.controller;

import dartsgame.models.StatusDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestApiController {

    @PostMapping("/api/game/create")
    private StatusDTO.UnderConstruction createGame() {
        return new StatusDTO.UnderConstruction();
    }

    @GetMapping("/api/game/list")
    private StatusDTO.UnderConstruction getGamesList() {
        return new StatusDTO.UnderConstruction();
    }

    @GetMapping("/api/game/join")
    private StatusDTO.UnderConstruction joinGame() {
        return new StatusDTO.UnderConstruction();
    }

    @GetMapping("/api/game/status")
    private StatusDTO.UnderConstruction getGameStatus() {
        return new StatusDTO.UnderConstruction();
    }

    @PostMapping("/api/game/throws")
    private StatusDTO.UnderConstruction setThrows() {
        return new StatusDTO.UnderConstruction();
    }

}
