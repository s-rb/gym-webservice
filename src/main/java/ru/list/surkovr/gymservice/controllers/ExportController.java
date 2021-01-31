package ru.list.surkovr.gymservice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.list.surkovr.gymservice.domain.Exercise;
import ru.list.surkovr.gymservice.services.interfaces.ExerciseService;
import ru.list.surkovr.gymservice.services.interfaces.ExportService;

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
    public void exportExercises(HttpServletResponse response,
                                @RequestParam(required = false, defaultValue = "false") Boolean isOdtFormat,
                                @RequestParam(required = false, defaultValue = "false") Boolean hasToZip
    ) throws IOException {
        List<Exercise> exercises = exerciseService.findAll();

        final DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        String dateTime = LocalDateTime.now().format(formatter);
        String fileName;

        ServletOutputStream outputStream = response.getOutputStream();

        if (Boolean.TRUE.equals(isOdtFormat)) {
            response.setContentType("application/octet-stream");
            fileName = format("exercises_export_%s.zip", dateTime);
            exportService.writeExercisesToOutputStream(exercises, outputStream, true, hasToZip);
        } else {
            response.setContentType("text/csv");
            fileName = format("exercises_export_%s.csv", dateTime);
            exportService.writeExercisesToOutputStream(exercises, outputStream, hasToZip);
        }

        response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
    }
}
