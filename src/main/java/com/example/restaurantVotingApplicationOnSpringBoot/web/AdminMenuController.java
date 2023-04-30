package com.example.restaurantVotingApplicationOnSpringBoot.web;

import com.example.restaurantVotingApplicationOnSpringBoot.error.NotFoundException;
import com.example.restaurantVotingApplicationOnSpringBoot.model.Menu;
import com.example.restaurantVotingApplicationOnSpringBoot.model.Restaurant;
import com.example.restaurantVotingApplicationOnSpringBoot.repository.MenuRepository;
import com.example.restaurantVotingApplicationOnSpringBoot.repository.RestaurantRepository;
import com.example.restaurantVotingApplicationOnSpringBoot.to.menu.AdminMenuDto;
import com.example.restaurantVotingApplicationOnSpringBoot.to.menu.AdminMenuDtoWithoutRestaurantId;
import com.example.restaurantVotingApplicationOnSpringBoot.util.ToUtil;
import com.example.restaurantVotingApplicationOnSpringBoot.util.ValidationUtil;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

import static com.example.restaurantVotingApplicationOnSpringBoot.web.UserVoteController.RESTAURANTS_WITH_VISIBLE_MENU_CACHE_NAME;

@RestController
@AllArgsConstructor
@RequestMapping(value = AdminMenuController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminMenuController {

    public static final String REST_URL = "/api/admin/menu";

    private MenuRepository menuRepository;

    private RestaurantRepository restaurantRepository;

    @PostMapping
    @CacheEvict(value = RESTAURANTS_WITH_VISIBLE_MENU_CACHE_NAME, allEntries = true)
    public ResponseEntity<AdminMenuDto> create(@Valid @RequestBody AdminMenuDto adminMenuDto) {
        ValidationUtil.checkNew(adminMenuDto);
        Menu fromTo = ToUtil.adminMenuDtoToMenu(adminMenuDto);
        Restaurant restaurant = restaurantRepository.getReferenceById(adminMenuDto.getRestaurantId());
        fromTo.setRestaurant(restaurant);
        Menu created = menuRepository.save(fromTo);
        URI uriOfCreatedResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}").buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfCreatedResource).body(ToUtil.menuToAdminMenuDto(created,
                adminMenuDto.getRestaurantId()));
    }

    @GetMapping("/{id}")
    public AdminMenuDtoWithoutRestaurantId get(@PathVariable Integer id) {
        return ToUtil.menuToAdminMenuDtoWithoutRestaurantId(
                menuRepository.findById(id).orElseThrow(() -> new NotFoundException("Menu not found")));
    }

    @PutMapping("/{id}")
    @CacheEvict(value = RESTAURANTS_WITH_VISIBLE_MENU_CACHE_NAME, allEntries = true)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody AdminMenuDto adminMenuDto, @PathVariable Integer id) {
        ValidationUtil.assureIdConsistent(adminMenuDto, id);
        Restaurant restaurant = restaurantRepository.getReferenceById(adminMenuDto.getRestaurantId());
        Menu menu = menuRepository.findById(id).orElseThrow(() -> new NotFoundException("Menu not found"));
        menu.setRestaurant(restaurant);
        menu.setEnabled(adminMenuDto.getEnabled());
        menu.setPrice(adminMenuDto.getPrice());
        menu.setName(adminMenuDto.getName());
        menuRepository.save(menu);
    }

    @DeleteMapping("/{id}")
    @CacheEvict(value = RESTAURANTS_WITH_VISIBLE_MENU_CACHE_NAME, allEntries = true)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        menuRepository.deleteById(id);
    }
}
