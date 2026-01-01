package com.pokemonreview.api.service;


import com.pokemonreview.api.dto.PokemonDto;
import com.pokemonreview.api.dto.PokemonResponse;
import com.pokemonreview.api.models.Pokemon;
import com.pokemonreview.api.repository.PokemonRepository;
import com.pokemonreview.api.service.impl.PokemonServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class PokemonServiceTest {
    @Mock
    private PokemonRepository pokemonRepository;

    @InjectMocks
    private PokemonServiceImpl pokemonService;

    private Pokemon pokemon;

    @BeforeEach
    void setUp() {
        pokemon = new Pokemon()
                .builder()
                .name("picka")
                .type("electric")
                .build();
    }

    @Test
    @DisplayName("Should create Pokemon successfully")
    void PokemonService_CreatePokemon_ReturnPokemonDto() {
        PokemonDto pokemonDto = PokemonDto.builder().name("picka").type("electric").build();


        when(pokemonRepository.save(Mockito.any(Pokemon.class))).thenReturn(pokemon);
        PokemonDto result = pokemonService.createPokemon(pokemonDto);

        Assertions.assertEquals(pokemonDto.getName(), result.getName());
        Assertions.assertEquals(pokemonDto.getType(), result.getType());
        Assertions.assertNotNull(result);

        verify(pokemonRepository,times(1)).save(Mockito.any(Pokemon.class));
    }

    @Test
    @DisplayName("Should get all Pokemon successfully")
    void PokemonService_GetAll_ReturnResponseDto() {
        Page<Pokemon> pokemonPage = Mockito.mock(Page.class);

        when(pokemonRepository.findAll(Mockito.any(Pageable.class))).thenReturn(pokemonPage);

        PokemonResponse savedPokemon = pokemonService.getAllPokemon(1,10);
        Assertions.assertNotNull(savedPokemon);
        verify(pokemonRepository,times(1)).findAll(Mockito.any(Pageable.class));
    }

    @Test
    @DisplayName("Should get Pokemon by Id")
    void PokemonService_GetPokemonById_ReturnResponseDto() {
        when(pokemonRepository.findById(1)).thenReturn(Optional.of(pokemon));
        PokemonDto poke = pokemonService.getPokemonById(1);

        Assertions.assertNotNull(poke);
        verify(pokemonRepository,times(1)).findById(1);
    }

    @Test
    @DisplayName("Should update Pokemon")
    void PokemonService_UpdatePokemon_ReturnResponseDto() {
        PokemonDto pokemonDto = PokemonDto.builder().name("picka").type("electric").build();

        when(pokemonRepository.findById(1)).thenReturn(Optional.of(pokemon));
        when(pokemonRepository.save(Mockito.any(Pokemon.class))).thenReturn(pokemon);

        PokemonDto updatedPoke = pokemonService.updatePokemon(pokemonDto, 1);
        Assertions.assertNotNull(updatedPoke);
        verify(pokemonRepository,times(1)).findById(1);
        verify(pokemonRepository,times(1)).save(Mockito.any(Pokemon.class));
    }

    @Test
    @DisplayName("Should Delete Pokemon")
    void PokemonService_DeletePokemon_ReturnResponseDto() {
        when(pokemonRepository.findById(1)).thenReturn(Optional.of(pokemon));
        Assertions.assertAll(()-> pokemonService.deletePokemonId(1));
    }
}
