package ru.javawebinar.topjava.web;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.util.MealsUtil;

@Controller
public class JspMealController {
    @Autowired
    private MealService service;

    @GetMapping("/meals")
    public String meals(Model model) {
        model.addAttribute("meals", MealsUtil.getWithExcess(service.getAll(SecurityUtil.authUserId()), SecurityUtil.authUserCaloriesPerDay()));
        return "meals";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") int id) {
        service.delete(id, SecurityUtil.authUserId());
        return "redirect:/meals";
    }

//    public String update(int id) {
//
//    }

//    @GetMapping("/mealForm/{id}")
//    public String edit(Model model, @PathVariable("id") int id) {
//        model.addAttribute("meal", service.get(id, SecurityUtil.authUserId()));
//        return "mealForm";
//    }

    @GetMapping("/mealForm")
    public String mealForm(Model model, @RequestParam(required = false) Integer id) {
        model.addAttribute("meal", service.get(id, SecurityUtil.authUserId()));
        return "mealForm";
    }

//    @RequestMapping("/remove/{id}")
//    public String removePart(@PathVariable("id") int id) {
//        this.partService.removePart(id);
//
//        return "redirect:/parts";
//    }
}
