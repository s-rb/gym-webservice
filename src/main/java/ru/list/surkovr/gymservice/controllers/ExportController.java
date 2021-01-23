package ru.list.surkovr.gymservice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.list.surkovr.gymservice.domain.Exercise;
import ru.list.surkovr.gymservice.services.ExerciseService;
import ru.list.surkovr.gymservice.services.ExportService;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static java.lang.String.format;

@RestController
@RequestMapping("api/v1/export")
public class ExportController {

    @Autowired
    private final ExerciseService exerciseService;
    @Autowired
    private final ExportService exportService;

    public ExportController(ExerciseService exerciseService, ExportService exportService) {
        this.exerciseService = exerciseService;
        this.exportService = exportService;
    }

    @GetMapping("exercises")
    public void exportExercises(HttpServletResponse response) throws IOException {
        final DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        String dateTime = LocalDateTime.now().format(formatter);
        String fileName = format("exercises_export_%s.csv", dateTime);
        response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
        response.setContentType("text/csv");

        List<Exercise> exercises = exerciseService.findAll();

        ServletOutputStream outputStream = response.getOutputStream();
        exportService.writeExercisesToOutputStream(exercises, outputStream);
    }
}
