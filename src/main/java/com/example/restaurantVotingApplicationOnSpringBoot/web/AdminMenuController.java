package com.example.restaurantVotingApplicationOnSpringBoot.web;

import com.example.restaurantVotingApplicationOnSpringBoot.model.Menu;
import com.example.restaurantVotingApplicationOnSpringBoot.repository.MenuRepository;
import com.example.restaurantVotingApplicationOnSpringBoot.to.menu.AdminMenuDto;
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

@RestController
@AllArgsConstructor
@RequestMapping(value = AdminMenuController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminMenuController {

    public static final String REST_URL = "/api/admin/menu";

    private MenuRepository menuRepository;

    @PostMapping
    public ResponseEntity<String> create(@Valid @RequestBody AdminMenuDto adminMenuDto) {
        ValidationUtil.checkNew(adminMenuDto);
        Menu created = menuRepository.save(ToUtil.adminMenuDtoToMenu(adminMenuDto));
        URI uriOfCreatedResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}").buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfCreatedResource).build();
    }

    @GetMapping("/{id}")
    public AdminMenuDto get(@PathVariable Integer id) {
        return ToUtil.menuToAdminMenuDto(menuRepository.findById(id).orElseThrow(IllegalArgumentException::new));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody AdminMenuDto adminMenuDto, @PathVariable Integer id) {
        ValidationUtil.assureIdConsistent(adminMenuDto, id);
        menuRepository.save(ToUtil.adminMenuDtoToMenu(adminMenuDto));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        menuRepository.deleteById(id);
    }
}
