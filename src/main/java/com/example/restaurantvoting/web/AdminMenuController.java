package com.example.restaurantvoting.web;

import com.example.restaurantvoting.error.NotFoundException;
import com.example.restaurantvoting.model.Menu;
import com.example.restaurantvoting.model.Restaurant;
import com.example.restaurantvoting.repository.MenuRepository;
import com.example.restaurantvoting.repository.RestaurantRepository;
import com.example.restaurantvoting.to.menu.AdminMenuDtoWithRestaurantId;
import com.example.restaurantvoting.to.menu.AdminMenuDto;
import com.example.restaurantvoting.util.ToUtil;
import com.example.restaurantvoting.util.ValidationUtil;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

import static com.example.restaurantvoting.web.UserVoteController.RESTAURANTS_WITH_MENU_CACHE_NAME;

@RestController
@AllArgsConstructor
@RequestMapping(value = AdminMenuController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminMenuController {

    public static final String REST_URL = "/api/admin/menu";

    private MenuRepository menuRepository;

    private RestaurantRepository restaurantRepository;

    @PostMapping
    @CacheEvict(value = RESTAURANTS_WITH_MENU_CACHE_NAME, allEntries = true)
    public ResponseEntity<AdminMenuDtoWithRestaurantId> create(@Valid @RequestBody AdminMenuDtoWithRestaurantId adminMenuDtoWithRestaurantId) {
        ValidationUtil.checkNew(adminMenuDtoWithRestaurantId);
        Menu fromTo = ToUtil.adminMenuDtoWithRestaurantIdToMenu(adminMenuDtoWithRestaurantId);
        Restaurant restaurant = restaurantRepository.getReferenceById(adminMenuDtoWithRestaurantId.getRestaurantId());
        fromTo.setRestaurant(restaurant);
        Menu created = menuRepository.save(fromTo);
        URI uriOfCreatedResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}").buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfCreatedResource).body(ToUtil.menuToAdminMenuDtoWithRestaurantId(created,
                adminMenuDtoWithRestaurantId.getRestaurantId()));
    }

    @GetMapping("/{id}")
    public AdminMenuDto get(@PathVariable Integer id) {
        return ToUtil.menuToAdminMenuDto(
                menuRepository.findById(id).orElseThrow(() -> new NotFoundException("Menu not found")));
    }

    @PutMapping("/{id}")
    @CacheEvict(value = RESTAURANTS_WITH_MENU_CACHE_NAME, allEntries = true)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody AdminMenuDtoWithRestaurantId adminMenuDtoWithRestaurantId, @PathVariable Integer id) {
        ValidationUtil.assureIdConsistent(adminMenuDtoWithRestaurantId, id);
        Restaurant restaurant = restaurantRepository.getReferenceById(adminMenuDtoWithRestaurantId.getRestaurantId());
        Menu menu = menuRepository.findById(id).orElseThrow(() -> new NotFoundException("Menu not found"));
        menu.setRestaurant(restaurant);
        menu.setPrice(adminMenuDtoWithRestaurantId.getPrice());
        menu.setName(adminMenuDtoWithRestaurantId.getName());
        menuRepository.save(menu);
    }

    @DeleteMapping("/{id}")
    @CacheEvict(value = RESTAURANTS_WITH_MENU_CACHE_NAME, allEntries = true)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        menuRepository.deleteById(id);
    }
}
