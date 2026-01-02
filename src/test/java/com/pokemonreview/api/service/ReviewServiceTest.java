package com.pokemonreview.api.service;

import com.pokemonreview.api.dto.PokemonDto;
import com.pokemonreview.api.dto.ReviewDto;
import com.pokemonreview.api.models.Pokemon;
import com.pokemonreview.api.models.Review;
import com.pokemonreview.api.repository.PokemonRepository;
import com.pokemonreview.api.repository.ReviewRepository;
import com.pokemonreview.api.service.impl.ReviewServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.swing.text.html.Option;

import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReviewServiceTest {

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private PokemonRepository pokemonRepository;

    @InjectMocks
    private ReviewServiceImpl reviewService;

    private ReviewDto reviewDto;
    private Review review;
    private Pokemon pokemon;
    private PokemonDto  pokemonDto;

    @BeforeEach
    public void setup() {
         review = Review.builder().title("review1").content("content1").stars(4).build();
         reviewDto = ReviewDto.builder().title("review1").content("content1").stars(4).build();
         pokemon = new Pokemon().builder().name("picka").type("electric").build();
         pokemonDto =  PokemonDto.builder().name("picka").type("electric").build();
    }

    @Test
    @DisplayName("Should create Review and return ReviewDto")
    void Review_Create_ReturnReviewDto() {
        when(pokemonRepository.findById(pokemon.getId())).thenReturn(Optional.of(pokemon));
        when(reviewRepository.save(Mockito.any(Review.class))).thenReturn(review);

        ReviewDto savedReview = reviewService.createReview(pokemon.getId(), reviewDto);
        Assertions.assertNotNull(savedReview);
        verify(pokemonRepository,times(1)).findById(pokemon.getId());
        verify(reviewRepository,times(1)).save(Mockito.any(Review.class));
    }

    @Test
    @DisplayName("Should get review by id")
    void Review_GetReviewById_ReturnReviewDto() {
        review.setPokemon(pokemon);
        when(pokemonRepository.findById(pokemon.getId())).thenReturn(Optional.of(pokemon));
        when(reviewRepository.findById(review.getId())).thenReturn(Optional.of(review));

        ReviewDto result = reviewService.getReviewById(review.getId(),pokemon.getId());
        Assertions.assertNotNull(result);
    }
}
