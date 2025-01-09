package com.example.cardapio.controller;

import com.example.cardapio.food.Food;
import com.example.cardapio.food.FoodRepository;
import com.example.cardapio.food.FoodRequestDTO;
import com.example.cardapio.food.FoodResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // Indica que essa classe é um controller da aplicação
@RequestMapping("food") // Mapeia o request
public class FoodController {

    @Autowired
    private FoodRepository repository;

    @PostMapping
    public void saveFood(@RequestBody FoodRequestDTO data) {
        Food foodData = new Food(data);
        repository.save(foodData);
        return;
    }

    @GetMapping // Quando o EndPoint food for chamado
    public List<FoodResponseDTO> getAll() {
       List<FoodResponseDTO> foodList = repository.findAll().stream().map(FoodResponseDTO::new).toList();
       return foodList;

    }

    @PutMapping("/{id}")
    public ResponseEntity<Food> updateFood(@PathVariable Long id, @RequestBody FoodRequestDTO foodRequestDTO) {
        return repository.findById(id)
                .map(food -> {
                    food.setTitle(foodRequestDTO.title());
                    food.setPrice(foodRequestDTO.price());
                    food.setImage(foodRequestDTO.image());

                    Food updatedFood = repository.save(food);
                    return ResponseEntity.ok().body(updatedFood);
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
