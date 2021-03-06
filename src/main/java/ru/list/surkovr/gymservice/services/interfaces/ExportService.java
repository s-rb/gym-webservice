package ru.list.surkovr.gymservice.services.interfaces;

import ru.list.surkovr.gymservice.domain.Exercise;

import javax.servlet.ServletOutputStream;
import java.io.OutputStream;
import java.util.List;

public interface ExportService {

    void writeExercisesToOutputStream(List<Exercise> exercises, OutputStream outputStream, boolean hasToZip);

    void writeExercisesToOutputStream(List<Exercise> exercises, ServletOutputStream outputStream,
                                      boolean isOdtFormat, boolean hasToZip);

}
