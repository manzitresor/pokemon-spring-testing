package com.pokemonreview.api.respository;


import com.pokemonreview.api.models.Pokemon;
import com.pokemonreview.api.repository.PokemonRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class PokemonRepositoryTest {
    @Autowired
    private PokemonRepository pokemonRepository;

    @Test
    public void PekemonRepository_save_all() {
        Pokemon pokemon = new Pokemon()
                .builder()
                .name("picka")
                .type("electric")
                .build();
        Pokemon savedPokemon = pokemonRepository.save(pokemon);

        Assertions.assertNotNull(savedPokemon);
    }

    @Test
    public void PokemonRepository_FindAll_ReturnAllPokemons() {
        Pokemon pokemon = new Pokemon()
                .builder()
                .name("picka")
                .type("electric")
                .build();
        Pokemon pokemen2 = new Pokemon()
                .builder()
                .name("picka2")
                .type("electric3")
                .build();

        pokemonRepository.save(pokemon);
        pokemonRepository.save(pokemen2);

        List<Pokemon> pokemons = pokemonRepository.findAll();

        Assertions.assertNotNull(pokemons);
        Assertions.assertEquals(pokemons.size(), 2);
    }

@Test
public void PokemonRepository_FindOne_ReturnPokemon() {
        Pokemon pokemon = new Pokemon()
                .builder()
                .name("picka")
                .type("electric")
                .build();

        pokemonRepository.save(pokemon);

        Pokemon pokemons = pokemonRepository.findById(pokemon.getId()).get();

        Assertions.assertNotNull(pokemons);
    }

    @Test
    public void PokemonRepository_FindType_ReturnPokemon() {
        Pokemon pokemon = new Pokemon()
                .builder()
                .name("picka")
                .type("electric")
                .build();
        pokemonRepository.save(pokemon);
        Pokemon savedPokemon = pokemonRepository.findByType(pokemon.getType()).get();
        Assertions.assertNotNull(savedPokemon);
    }
}
