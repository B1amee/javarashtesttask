package com.game.services;


import com.game.entity.Player;
import com.game.entity.Profession;
import com.game.entity.Race;
import com.game.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class PlayerServices {

    private List<Player> playerList;

    @Autowired
    private PlayerRepository playerRepository;


    @Transactional
    public void findAll() {
        playerList = new ArrayList<>(playerRepository.findAll());
    }


    public List<Player> getFilterList(String name, String title, Race race, Profession profession,
                                      Long after, Long before, Boolean banned, Integer minExperience, Integer maxExperience, Integer minLevel,
                                      Integer maxLevel) {
        List<Player> resultList = new ArrayList<>();
        for (Player player : getPlayerList()) {
            boolean flag = true;
            if (name != null && !player.getName().contains(name)) flag = false;
            if (flag && title != null && !player.getTitle().contains(title)) flag = false;
            if (flag && race != null && player.getRace()!= race) flag = false;
            if (flag && profession != null && player.getProfession() != profession) flag = false;
            if (flag && after != null && player.getBirthday().before(new Date(after))) flag = false;
            if (flag && before != null && player.getBirthday().after(new Date(before))) flag = false;
            if (flag && banned != null && !player.getBanned().equals(banned)) flag = false;
            if (flag && minExperience != null && player.getExperience().compareTo(minExperience) < 0) flag = false;
            if (flag && maxExperience != null && player.getExperience().compareTo(maxExperience) > 0) flag = false;
            if (flag && minLevel != null && player.getLevel().compareTo(minLevel) < 0) flag = false;
            if (flag && maxLevel != null && player.getLevel().compareTo(maxLevel) > 0) flag = false;
            if (flag) {
                resultList.add(player);
            }
        }
        playerList = resultList;
        return resultList;
    }

    public List<Player> getPlayerList() {
        findAll();
        return playerList;
    }

    public boolean cratePlayer(Player player) {
        boolean flag = true;
        if (player == null) flag = false;
        if (flag && player.getName() == null) flag = false;
        if (flag &&  (player.getName().length() > 12 || player.getName().equals(""))) flag = false;
        if (flag && (player.getTitle() == null || player.getTitle().length() > 30)) flag = false;
        if (flag && player.getRace() == null) flag = false;
        if (flag && player.getProfession() == null) flag = false;
        if (flag && player.getBirthday() == null && player.getBirthday().getTime() < 0) flag = false;
        if (flag && player.getExperience() == null) flag = false;
        if (flag && (!(player.getExperience() > 0L) || !(player.getExperience() < 10000000L))) flag = false;
        if (flag) {
            Integer level = Math.toIntExact(Math.round((Math.sqrt(2500 + 200.0 * player.getExperience()) - 50) / 100));
            Integer untilNextLevel = 50 * (level + 1) * (level + 2) - player.getExperience();
            player.setLevel(level);
            player.setUntilNextLevel(untilNextLevel);
            System.out.println(player);
            playerRepository.saveAndFlush(player);
        }
        return flag;
    }
}
