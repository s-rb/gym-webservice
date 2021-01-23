package ru.list.surkovr.gymservice.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.list.surkovr.gymservice.domain.Exercise;
import ru.list.surkovr.gymservice.utils.DescriptionAnnotation;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ExportServiceImpl implements ExportService {

    public static final String EXPORT_CSV_DELIMITER = ";";
    public static final String EXPORT_CSV_CHARSET = "windows-1251";

    @Override
    public void writeExercisesToOutputStream(List<Exercise> exercises, OutputStream outputStream) {
        try (BufferedOutputStream bos = new BufferedOutputStream(outputStream)) {
            writeHeader(bos, Exercise.class);
            for (Exercise exercise : exercises) {
                try {
                    String exerciseExportString = exercise.getExportString(EXPORT_CSV_DELIMITER);
                    String exercisesNewString = exerciseExportString + "\n";
                    outputStream.write(exercisesNewString.getBytes(EXPORT_CSV_CHARSET));
                } catch (IOException e) {
                    log.error("### In writeExercisesToOutputStream get exception while writing exercise: {}", exercise);
                }
            }
        } catch (IOException e) {
            log.error("### In writeExercisesToOutputStream get exception", e);
        }
    }

/* //TODO сделать универсальный метод выгрузки для всех полей помеченных аннотацией для любого класса
private void writeData(OutputStream outputStream, Object exercise, Class<?> clazz) {
        String dataLine = Arrays.stream(clazz.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(DescriptionAnnotation.class))
                .map(field -> field.get(new Object()))
                .collect(Collectors.joining(EXPORT_CSV_DELIMITER));
    }*/

    private void writeHeader(OutputStream outputStream, Class<?> clazz) throws IOException {
        String headerLine = Arrays.stream(clazz.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(DescriptionAnnotation.class))
                .map(field -> field.getAnnotation(DescriptionAnnotation.class).value())
                .collect(Collectors.joining(EXPORT_CSV_DELIMITER));
        String headerLineNextStr = headerLine + "\n";
        outputStream.write(headerLineNextStr.getBytes(EXPORT_CSV_CHARSET));
    }
}
