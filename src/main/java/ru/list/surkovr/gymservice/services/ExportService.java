package ru.list.surkovr.gymservice.services;

import ru.list.surkovr.gymservice.domain.Exercise;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public interface ExportService {

    void writeExercisesToOutputStream(List<Exercise> exercises, OutputStream outputStream);
}
