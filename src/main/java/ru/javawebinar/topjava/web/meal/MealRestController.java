package ru.javawebinar.topjava.web.meal;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealTo;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.springframework.format.annotation.DateTimeFormat.ISO.DATE;
import static org.springframework.format.annotation.DateTimeFormat.ISO.DATE_TIME;
import static org.springframework.format.annotation.DateTimeFormat.ISO.TIME;

@RestController
@RequestMapping(MealRestController.REST_URL)
public class MealRestController extends AbstractMealController {
    static final String REST_URL = "/rest/meals";

    @Override
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<MealTo> getAll() {
        return super.getAll();
    }

    @Override
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Meal get(@PathVariable("id") int id) {
        return super.get(id);
    }

    @Override
    @DeleteMapping(value = "/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") int id) {
        super.delete(id);
    }

    @Override
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void update(@RequestBody Meal meal, @PathVariable("id") int id) {
        super.update(meal, id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Meal> createWithLocation(@RequestBody Meal meal) {
        Meal created = super.create(meal);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }

//    @GetMapping(value = "/filter", produces = MediaType.APPLICATION_JSON_VALUE)
//    public List<MealTo> getBetween(
//            @RequestParam("startDateTime") @DateTimeFormat(iso = DATE_TIME) LocalDateTime startDateTime,
//            @RequestParam("endDateTime") @DateTimeFormat(iso = DATE_TIME) LocalDateTime endDateTime) {
//
//        return super.getBetween(
//                startDateTime.toLocalDate(), startDateTime.toLocalTime(),
//                endDateTime.toLocalDate(), endDateTime.toLocalTime());
//    }

    @Override
    @GetMapping(value = "/filter", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<MealTo> getBetween(
            @RequestParam(value = "startDate", required = false) /*@DateTimeFormat(iso = DATE)*/ LocalDate startDate,
            @RequestParam(value = "startTime", required = false) /*@DateTimeFormat(iso = TIME)*/ LocalTime startTime,
            @RequestParam(value = "endDate", required = false) /*@DateTimeFormat(iso = DATE)*/ LocalDate endDate,
            @RequestParam(value = "endTime", required = false) /*@DateTimeFormat(iso = TIME)*/ LocalTime endTime) {

        return super.getBetween(startDate, startTime, endDate, endTime);
    }
}