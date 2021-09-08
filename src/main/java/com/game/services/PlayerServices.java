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

    @Autowired
    private PlayerRepository playerRepository;


    @Transactional
    public List<Player> findAll() {
        List<Player> playerList = new ArrayList<>(playerRepository.findAll());
        return playerList;
    }

    public Integer getCount() {
        return Long.valueOf(playerRepository.count()).intValue();
    }

    public List<Player> getFilterList(List<Player> playerList, String name, String title, Race race, Profession profession,
                                      Long after, Long before, Boolean banned, Integer minExperience, Integer maxExperience, Integer minLevel,
                                      Integer maxLevel) {
        List<Player> resultList = new ArrayList<>();
        for (Player player : playerList) {
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
        return resultList;
    }
}
