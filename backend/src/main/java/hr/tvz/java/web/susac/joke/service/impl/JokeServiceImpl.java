package hr.tvz.java.web.susac.joke.service.impl;

import hr.tvz.java.web.susac.joke.dto.JokeDTO;
import hr.tvz.java.web.susac.joke.dto.CategorySearchDTO;
import hr.tvz.java.web.susac.joke.model.Category;
import hr.tvz.java.web.susac.joke.model.Joke;
import hr.tvz.java.web.susac.joke.repository.CategoryRepository;
import hr.tvz.java.web.susac.joke.repository.JokeRepository;
import hr.tvz.java.web.susac.joke.service.JokeService;
import hr.tvz.java.web.susac.joke.util.converter.ConverterUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
public class JokeServiceImpl implements JokeService {

    private final CategoryRepository categoryRepository;
    private final JokeRepository jokeRepository;
    private final ConverterUtil<Joke, JokeDTO> converter;

    @Override
    public JokeDTO findOneById(Integer id) {
        Joke joke = jokeRepository.findOneById(id);

        if(Objects.isNull(joke)) return null;

        return converter.convertToDTO(joke);
    }

    @Override
    public List<JokeDTO> findAllByCategory(String name) {
        List<Joke> jokeList = jokeRepository.findAllByCategoryDateDesc(name);

        return jokeList.stream().map(converter::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<JokeDTO> findAllByParam(CategorySearchDTO categorySearchDTO) {
        List<Joke> jokeList = jokeRepository.findAllByCategoryLikeDateDesc(categorySearchDTO.getName());

        return jokeList.stream().map(converter::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<JokeDTO> findAllLatest() {
        List<Joke> jokeList = jokeRepository.findAllDateDesc();

        return jokeList.stream().map(converter::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public JokeDTO save(JokeDTO jokeDTO) {
        Joke joke = converter.convertToEntity(jokeDTO);

        Category category = categoryRepository.findOneByName(jokeDTO.getCategory());

        if(Objects.isNull(category)){
            category = new Category();
            category.setName(jokeDTO.getCategory());

            categoryRepository.save(category);

            category = categoryRepository.findOneByName(jokeDTO.getCategory());
        }

        joke.setCategory(category);
        jokeRepository.save(joke);

        return converter.convertToDTO(joke);
    }

    @Override
    public void deleteById(Integer id) {
        jokeRepository.deleteById(id);
    }
}