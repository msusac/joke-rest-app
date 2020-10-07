package hr.tvz.java.web.susac.joke.service;

import hr.tvz.java.web.susac.joke.dto.JokeDTO;
import hr.tvz.java.web.susac.joke.dto.CategorySearchDTO;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class JokeServiceTests {
    
    @Autowired
    private JokeService jokeService;

    @Test
    @Order(1)
    public void findOneById(){
        JokeDTO jokeDTO = jokeService.findOneById(1);

        assertNotNull(jokeDTO);
        assertEquals("Chuck Norris", jokeDTO.getCategory());
    }

    @Test
    @Order(2)
    public void findAllLatest(){
        List<JokeDTO> jokeList = jokeService.findAllLatest();

        assertNotNull(jokeList);
        assertEquals(5, jokeList.size());
        assertEquals("Programming", jokeList.get(0).getCategory());
    }

    @Test
    @Order(3)
    public void findAllByCategory(){
        List<JokeDTO> jokeList = jokeService.findAllByCategory("Chuck Norris");

        assertNotNull(jokeList);
        assertEquals(2, jokeList.size());
        assertEquals("Chuck Norris", jokeList.get(0).getCategory());
    }

    @Test
    @Order(4)
    public void findAllByParam(){
        CategorySearchDTO categorySearchDTO = new CategorySearchDTO();
        categorySearchDTO.setName("Pro");

        List<JokeDTO> jokeList = jokeService.findAllByParam(categorySearchDTO);

        assertNotNull(jokeList);
        assertEquals(3, jokeList.size());
        assertEquals("Programming", jokeList.get(0).getCategory());
    }

    @Test
    @Order(5)
    public void save_new(){
        JokeDTO jokeDTO = new JokeDTO();
        jokeDTO.setCategory("New Joke");
        jokeDTO.setDescription("C++");

        jokeService.save(jokeDTO);

        List<JokeDTO> jokeList = jokeService.findAllByCategory("New Joke");

        assertNotNull(jokeList);
        assertEquals(1, jokeList.size());
        assertEquals("C++", jokeList.get(0).getDescription());
    }

    @Test
    @Order(7)
    public void save_existing(){
        JokeDTO jokeDTO = new JokeDTO();
        jokeDTO.setCategory("Programming");
        jokeDTO.setDescription("C++");

        jokeService.save(jokeDTO);

        List<JokeDTO> jokeList = jokeService.findAllByCategory("Programming");

        assertNotNull(jokeList);
        assertEquals(4, jokeList.size());
        assertEquals("C++", jokeList.get(0).getDescription());
    }

    @Test
    @Order(7)
    public void deleteById(){
        JokeDTO jokeDTO = jokeService.findOneById(4);

        jokeService.deleteById(jokeDTO.getId());

        List<JokeDTO> jokeList = jokeService.findAllByCategory("Programming");

        jokeDTO = jokeService.findOneById(4);

        assertNull(jokeDTO);
        assertNotNull(jokeList);
        assertEquals(2, jokeList.size());
    }
}