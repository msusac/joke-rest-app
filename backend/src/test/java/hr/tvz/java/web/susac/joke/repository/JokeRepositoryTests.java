package hr.tvz.java.web.susac.joke.repository;

import hr.tvz.java.web.susac.joke.configuration.SchedulerConfig;
import hr.tvz.java.web.susac.joke.jobs.VerificationJob;
import hr.tvz.java.web.susac.joke.model.Category;
import hr.tvz.java.web.susac.joke.model.Joke;
import hr.tvz.java.web.susac.joke.model.User;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;

import javax.transaction.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@MockBean({SchedulerConfig.class, VerificationJob.class})
@TestPropertySource(locations = "classpath:application-test.properties")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class JokeRepositoryTests {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private JokeRepository jokeRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    @Order(1)
    public void findOneById(){
        Joke joke = jokeRepository.findOneById(1);

        assertNotNull(joke);
        assertEquals("Chuck Norris", joke.getCategory().getName());
    }

    @Test
    @Order(2)
    public void findAllNewest(){
        List<Joke> jokeList = jokeRepository.findAllNewest();

        assertNotNull(jokeList);
        assertEquals(5, jokeList.size());
        assertEquals("Programming", jokeList.get(0).getCategory().getName());
    }

    @Test
    @Order(3)
    public void findAllByCategoryPopular(){
        List<Joke> jokeList = jokeRepository.findAllByCategoryPopular("Chuck Norris");

        assertNotNull(jokeList);
        assertEquals(2, jokeList.size());
        assertEquals("Chuck Norris", jokeList.get(0).getCategory().getName());
    }

    @Test
    @Order(4)
    public void findAllByCategoryLikePopular(){
        List<Joke> jokeList = jokeRepository.findAllByCategoryLikePopular("Pro");

        assertNotNull(jokeList);
        assertEquals(3, jokeList.size());
        assertEquals("Programming", jokeList.get(0).getCategory().getName());
    }

    @Test
    @Order(5)
    public void findAllByUserPopular(){
        List<Joke> jokeList = jokeRepository.findAllByUserPopular("admin");

        assertNotNull(jokeList);
        assertEquals(3, jokeList.size());
    }

    @Test
    @Order(6)
    public void save(){
        Category category = categoryRepository.findOneByName("Programming");
        User user = userRepository.findOneByUsername("userone").get();

        Joke joke = new Joke();
        joke.setDescription("C++");
        joke.setCategory(category);
        joke.setUser(user);

        jokeRepository.save(joke);

        List<Joke> jokeList = jokeRepository.findAllByCategoryPopular("Programming");

        assertNotNull(jokeList);
        assertEquals(4, jokeList.size());
    }

    @Test
    @Order(7)
    public void deleteById(){
        Joke joke = jokeRepository.findOneById(4);

        jokeRepository.deleteById(joke.getId());

        List<Joke> jokeList = jokeRepository.findAllByCategoryPopular("Programming");

        joke = jokeRepository.findOneById(4);

        assertNull(joke);
        assertNotNull(jokeList);
        assertEquals(2, jokeList.size());
    }


}
