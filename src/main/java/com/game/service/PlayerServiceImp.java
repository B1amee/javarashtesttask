package com.game.service;

import com.game.entity.Player;
import com.game.repository.PlayerRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlayerServiceImp implements PlayerService{

    private Logger log = Logger.getLogger(PlayerServiceImp.class);

    private PlayerRepository playerRepository;

    @Autowired
    public PlayerServiceImp(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @Override
    public List<Player> getAll() {
        log.info("IN PlayerServiceImp getAll ");
        return playerRepository.findAll();
    }

    @Override
    public Integer getPlayersCount() {
        log.info("IN PlayerServiceImp getPlayersCount ");
        return Long.valueOf(playerRepository.count()).intValue();
    }

    @Override
    public void save(Player player) {
        log.info("IN PlayerServiceImp save " + player);
        playerRepository.save(player);
    }

    @Override
    public Player getById(Long id) {
        log.info("IN PlayerServiceImp getById " + id);
        return playerRepository.getOne(id);
    }

    @Override
    public void update(Long id) {
        log.info("IN PlayerServiceImp update " + id);
        Player player = playerRepository.getOne(id);

    }

    @Override
    public void delete(Long id) {
        log.info("IN PlayerServiceImp delete " + id);
        playerRepository.deleteById(id);
    }
}
