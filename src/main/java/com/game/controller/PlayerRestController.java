package com.game.controller;

import com.game.entity.Player;
import com.game.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
public class PlayerRestController {

    private  PlayerService playerService;

    @Autowired
    public PlayerRestController(PlayerService playerService) {
        this.playerService = playerService;
    }

//    @RequestMapping(value = "rest/players/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @GetMapping(value = "rest/players/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Player> getPlayer(@PathVariable("id") Long playerId) {
        ResponseEntity<Player> responseEntity = null;
        if (playerId == null) {
            responseEntity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            Player player = playerService.getById(playerId);
            if (player == null) {
                responseEntity = new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                responseEntity = new ResponseEntity<>(player, HttpStatus.OK);
            }
        }
        return  responseEntity;
    }
//    @RequestMapping(value = "/rest/players", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping(value = "/rest/players",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Player> savePlayer(@RequestBody @Validated Player player) {
        HttpHeaders httpHeaders = new HttpHeaders();
        ResponseEntity<Player> responseEntity = null;
        if (player == null) {
            responseEntity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            playerService.save(player);
            responseEntity = new ResponseEntity<>(player, httpHeaders, HttpStatus.CREATED);
        }
        return responseEntity;
    }

//    @RequestMapping(value = "/rest/players", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @GetMapping(value = "/rest/players", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Player>> getAllPlayers() {
        List<Player> players = playerService.getAll();
        ResponseEntity<List<Player>> responseEntity = null;
        if (players.isEmpty()) {
            responseEntity = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            responseEntity = new ResponseEntity<>(players, HttpStatus.OK);
        }
        return responseEntity;
    }

    @GetMapping(value = "/rest/players/count", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Integer> getCount() {
        ResponseEntity<Integer> responseEntity = null;
        Integer count = playerService.getPlayersCount();
        if (count == null) {
            responseEntity = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            responseEntity = new ResponseEntity<>(count, HttpStatus.OK);
        }
        return responseEntity;
    }
}
