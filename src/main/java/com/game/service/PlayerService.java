package com.game.service;

import com.game.entity.Player;

import java.util.List;

public interface PlayerService {
    List<Player> getAll();
    Integer getPlayersCount();
    void save(Player player);
    Player getById(Long id);
    void update(Long id);
    void delete(Long id);
}
