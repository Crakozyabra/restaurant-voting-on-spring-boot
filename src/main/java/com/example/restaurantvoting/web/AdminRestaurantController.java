package com.example.restaurantvoting.web;

import com.example.restaurantvoting.error.NotFoundException;
import com.example.restaurantvoting.model.Restaurant;
import com.example.restaurantvoting.repository.RestaurantRepository;
import com.example.restaurantvoting.to.restaurant.AdminRestaurantDto;
import com.example.restaurantvoting.to.restaurant.RestaurantWithoutMenuDto;
import com.example.restaurantvoting.util.ToUtil;
import com.example.restaurantvoting.util.ValidationUtil;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import static com.example.restaurantvoting.web.UserVoteController.RESTAURANTS_WITH_MENU_CACHE_NAME;

@RestController
@AllArgsConstructor
@RequestMapping(value = AdminRestaurantController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminRestaurantController {

    public static final String REST_URL = "/api/admin/restaurants";

    private RestaurantRepository restaurantRepository;

    @PostMapping
    public ResponseEntity<RestaurantWithoutMenuDto> create(
            @Valid @RequestBody RestaurantWithoutMenuDto restaurantWithoutMenuDto) {
        ValidationUtil.checkNew(restaurantWithoutMenuDto);
        Restaurant created = restaurantRepository.save(
                ToUtil.restaurantWithoutMenuDtoToRestaurant(restaurantWithoutMenuDto));
        URI uriOfCreatedResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}").buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfCreatedResource).body(ToUtil.restaurantToRestaurantWithoutMenuDto(created));
    }

    @GetMapping("/{id}")
    public RestaurantWithoutMenuDto get(@PathVariable Integer id) {
        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Restaurant not found"));
        return ToUtil.restaurantToRestaurantWithoutMenuDto(restaurant);
    }

    @GetMapping("/{id}/with-menu")
    public AdminRestaurantDto getWithMenu(@PathVariable Integer id,
        @Parameter(description = "Format example: 2024-02-29. Enter today date", required = true)
        @RequestParam LocalDate date) {
        Restaurant restaurant = restaurantRepository.getWithMenu(id, date);
        if (Objects.isNull(restaurant)) {
            throw new NotFoundException("Restaurant with menu not found");
        }
        return ToUtil.restaurantToAdminRestaurantDto(restaurant);
    }

    @GetMapping
    public List<RestaurantWithoutMenuDto> getAll() {
        return ToUtil.restaurantsToRestaurantWithoutMenuTos(restaurantRepository.findAll());
    }

    @GetMapping("/with-menu")
    public List<AdminRestaurantDto> getAllWithMenu(
            @Parameter(description = "Format example: 2024-02-29. Enter today date", required = true)
            @RequestParam LocalDate date) {
        return ToUtil.restaurantsToRestaurantsTos(restaurantRepository.getAllWithMenu(date));
    }

    @PutMapping("/{id}")
    @CacheEvict(value = RESTAURANTS_WITH_MENU_CACHE_NAME, allEntries = true)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody RestaurantWithoutMenuDto restaurantWithoutMenuDto,
                       @PathVariable Integer id) {
        ValidationUtil.assureIdConsistent(restaurantWithoutMenuDto, id);
        Restaurant restaurant = restaurantRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Restaurant not found"));
        restaurant.setName(restaurantWithoutMenuDto.getName());
        restaurantRepository.save(restaurant);
    }

    @DeleteMapping("/{id}")
    @CacheEvict(value = RESTAURANTS_WITH_MENU_CACHE_NAME, allEntries = true)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        restaurantRepository.deleteById(id);
    }
}
