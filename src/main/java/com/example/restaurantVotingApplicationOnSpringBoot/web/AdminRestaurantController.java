package com.example.restaurantVotingApplicationOnSpringBoot.web;

import com.example.restaurantVotingApplicationOnSpringBoot.error.NotFoundException;
import com.example.restaurantVotingApplicationOnSpringBoot.model.Restaurant;
import com.example.restaurantVotingApplicationOnSpringBoot.repository.RestaurantRepository;
import com.example.restaurantVotingApplicationOnSpringBoot.to.restaurant.AdminRestaurantDto;
import com.example.restaurantVotingApplicationOnSpringBoot.to.restaurant.RestaurantWithoutMenuDto;
import com.example.restaurantVotingApplicationOnSpringBoot.util.ToUtil;
import com.example.restaurantVotingApplicationOnSpringBoot.util.ValidationUtil;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Objects;

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
    public AdminRestaurantDto getWithMenu(@PathVariable Integer id) {
        Restaurant restaurant = restaurantRepository.getWithMenu(id);
        if (Objects.isNull(restaurant)) {
            throw new NotFoundException("Restaurant not found");
        }
        return ToUtil.restaurantToAdminRestaurantDto(restaurant);
    }

    @GetMapping("with-menu")
    public List<AdminRestaurantDto> getAllWithMenu() {
        return ToUtil.restaurantsToRestaurantsTos(restaurantRepository.getAllWithMenu());
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody RestaurantWithoutMenuDto restaurantWithoutMenuDto,
                       @PathVariable Integer id) {
        ValidationUtil.assureIdConsistent(restaurantWithoutMenuDto, id);
        restaurantRepository.save(ToUtil.restaurantWithoutMenuDtoToRestaurant(restaurantWithoutMenuDto));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        restaurantRepository.deleteById(id);
    }
}
