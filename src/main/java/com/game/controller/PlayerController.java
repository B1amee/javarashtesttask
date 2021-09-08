package com.game.controller;

import com.game.entity.Player;
import com.game.entity.Profession;
import com.game.entity.Race;
import com.game.services.PlayerServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping
public class PlayerController {

    @Autowired
    private PlayerServices playerServices;

    @RequestMapping(value = "/rest/players", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<List<Player>> getPlayersLis(
            @RequestParam(defaultValue = "ID", value = "order", required = false) String order,
            @RequestParam(defaultValue = "3", value = "pageSize", required = false) Integer pageSize,
            @RequestParam(defaultValue = "0", value = "pageNumber", required = false) Integer pageNumber,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "race", required = false) Race race,
            @RequestParam(value = "profession", required = false) Profession profession,
            @RequestParam(value = "after", required = false) Long after,
            @RequestParam(value = "before", required = false) Long before,
            @RequestParam(value = "banned", required = false) Boolean banned,
            @RequestParam(value = "minExperience", required = false) Integer minExperience,
            @RequestParam(value = "maxExperience", required = false) Integer maxExperience,
            @RequestParam(value = "minLevel", required = false) Integer minLevel,
            @RequestParam(value = "maxLevel", required = false) Integer maxLevel) {
        ResponseEntity<List<Player>> responseEntity = null;
        List<Player> playerList = playerServices.getPlayerList();
        if (name != null ||
                title != null ||
                race != null ||
                profession != null ||
                after != null ||
                before != null ||
                banned != null ||
                minExperience != null ||
                maxExperience != null ||
                minLevel != null ||
                maxLevel != null) {
            playerList = playerServices.getFilterList(name, title, race, profession, after, before, banned, minExperience, maxExperience, minLevel, maxLevel);
        }
        if (order.equals("ID")) {
            playerList.sort(Comparator.comparingLong(Player::getId));
        } else if (order.equals("NAME")) {
            playerList.sort(Comparator.comparing(Player::getName));
        } else if (order.equals("EXPERIENCE")) {
            playerList.sort(Comparator.comparingLong(Player::getExperience));
        } else if (order.equals("BIRTHDAY")) {
            playerList.sort(Comparator.comparing(Player::getBirthday));
        }

        if (playerList == null) {
            responseEntity = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            List<Player> sortList = new ArrayList<>();
            for (int i = (pageNumber + 1) * pageSize - pageSize; i < (pageNumber + 1) * pageSize && i < playerList.size(); i++) {
                sortList.add(playerList.get(i));
            }
            responseEntity = new ResponseEntity<>(sortList, HttpStatus.OK);
        }
        return responseEntity;
    }
    @RequestMapping(value = "rest/players/count")
    public ResponseEntity<Integer> getCount(
            @RequestParam(defaultValue = "ID", value = "order", required = false) String order,
            @RequestParam(defaultValue = "3", value = "pageSize", required = false) Integer pageSize,
            @RequestParam(defaultValue = "0", value = "pageNumber", required = false) Integer pageNumber,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "race", required = false) Race race,
            @RequestParam(value = "profession", required = false) Profession profession,
            @RequestParam(value = "after", required = false) Long after,
            @RequestParam(value = "before", required = false) Long before,
            @RequestParam(value = "banned", required = false) Boolean banned,
            @RequestParam(value = "minExperience", required = false) Integer minExperience,
            @RequestParam(value = "maxExperience", required = false) Integer maxExperience,
            @RequestParam(value = "minLevel", required = false) Integer minLevel,
            @RequestParam(value = "maxLevel", required = false) Integer maxLevel) {
        ResponseEntity<Integer> responseEntity = null;
        List<Player> playerList = playerServices.getFilterList(name, title, race, profession, after, before, banned, minExperience, maxExperience, minLevel, maxLevel);
        Integer count = playerList.size();
        if (count == null) {
            responseEntity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            responseEntity = new ResponseEntity<>(count, HttpStatus.OK);
        }
        return responseEntity;
    }

    @RequestMapping(value = "rest/players", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Player> cratePlayer(@RequestBody Player player) {
        ResponseEntity<Player> responseEntity = null;
        boolean flag = playerServices.cratePlayer(player);
        if (flag) {
            responseEntity = new ResponseEntity<>(player, HttpStatus.OK);
        } else {
            responseEntity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return responseEntity;
    }
}
