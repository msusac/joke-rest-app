package hr.tvz.java.web.susac.joke.service;

import hr.tvz.java.web.susac.joke.dto.JokeDTO;
import hr.tvz.java.web.susac.joke.dto.CategorySearchDTO;
import hr.tvz.java.web.susac.joke.dto.user.UserDTO;

import java.util.List;

public interface JokeService {

    JokeDTO findOneById(Integer id);

    List<JokeDTO> findAllByCategory(String name);
    List<JokeDTO> findAllByParam(CategorySearchDTO categorySearchDTO);
    List<JokeDTO> findAllByUser(UserDTO user);
    List<JokeDTO> findAllLatest();

    JokeDTO save(JokeDTO jokeDTO);

    void deleteById(Integer id);
}
