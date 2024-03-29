package ru.list.surkovr.gymservice.services;

import lombok.extern.slf4j.Slf4j;
import org.odftoolkit.odfdom.dom.element.table.TableTableCellElementBase;
import org.odftoolkit.odfdom.pkg.OdfElement;
import org.odftoolkit.simple.Document;
import org.odftoolkit.simple.common.navigation.InvalidNavigationException;
import org.odftoolkit.simple.common.navigation.TextNavigation;
import org.odftoolkit.simple.common.navigation.TextSelection;
import org.odftoolkit.simple.table.Cell;
import org.odftoolkit.simple.table.Row;
import org.odftoolkit.simple.table.Table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import ru.list.surkovr.gymservice.domain.*;
import ru.list.surkovr.gymservice.repositories.DocTemplateRepository;
import ru.list.surkovr.gymservice.services.interfaces.ExportService;
import ru.list.surkovr.gymservice.utils.DescriptionAnnotation;

import javax.servlet.ServletOutputStream;
import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import static java.util.Objects.isNull;
import static ru.list.surkovr.gymservice.controllers.ExportController.EXERCISES_EXPORT_CSV_FILE_NAME_FORMAT_STRING;

@Slf4j
@Service
public class ExportServiceImpl implements ExportService {

    public static final String EXPORT_CSV_DELIMITER = ";";
    public static final String EXPORT_CSV_CHARSET = "windows-1251";

    @Autowired
    private final DocTemplateRepository docTemplateRepository;

    public ExportServiceImpl(DocTemplateRepository docTemplateRepository) {
        this.docTemplateRepository = docTemplateRepository;
    }

    @Override
    public void writeExercisesToOutputStream(List<Exercise> exercises, OutputStream outputStream, boolean hasToZip) {
        String headerLineStr = getHeaderLineStr(Exercise.class);
        List<String> exercisesStrings = new LinkedList<>();
        for (Exercise exercise : exercises) {
            String exerciseExportString = exercise.getExportString(EXPORT_CSV_DELIMITER);
            String exercisesNewString = exerciseExportString + "\n";
            exercisesStrings.add(exercisesNewString);
        }

        if (!hasToZip) {
            try (BufferedOutputStream bos = new BufferedOutputStream(outputStream)) {
                writeLinesToOutputStream(headerLineStr, exercisesStrings, bos);
            } catch (IOException e) {
                log.error("### In writeExercisesToOutputStream get exception", e);
            }
        } else {
            try (ZipOutputStream zos = new ZipOutputStream(new BufferedOutputStream(outputStream))) {
                String fileName = String.format(EXERCISES_EXPORT_CSV_FILE_NAME_FORMAT_STRING, LocalDate.now());
                ZipEntry zipEntry = new ZipEntry(fileName);
                zos.putNextEntry(zipEntry);
                writeLinesToOutputStream(headerLineStr, exercisesStrings, zos);
                zos.closeEntry();
            } catch (IOException e) {
                log.error("### In writeExercisesToOutputStream get exception", e);
            }
        }
    }

    private void writeLinesToOutputStream(String headerLineStr, List<String> exercisesStrings,
                                          OutputStream outputStream) throws IOException {
        writeString(outputStream, headerLineStr);
        for (String exerciseStr : exercisesStrings) {
            try {
                writeString(outputStream, exerciseStr);
            } catch (IOException e) {
                log.error("### In writeLinesToOutputStream get exception while writing exercise: {}", exerciseStr);
            }
        }
    }

    @Override
    public void writeExercisesToOutputStream(List<Exercise> exercises, ServletOutputStream outputStream,
                                             boolean isOdtFormat, boolean hasToZip) {
        if (Boolean.FALSE.equals(isOdtFormat)) {
            writeExercisesToOutputStream(exercises, outputStream, hasToZip);
        } else {
            DocTemplate template = docTemplateRepository.findFirstByCode(DocTemplateCodeEnum.ALL_EXERCISES_ODT);
            if (isNull(template)) {
                log.error("### In writeExercisesToOutputStream found null template");
                return;
            }
            byte[] templateData = template.getData();

            try (InputStream inputStream = new ByteArrayInputStream(templateData.clone())) {
                if (Boolean.TRUE.equals(hasToZip)) {
                    Map<String, ByteArrayOutputStream> mapToZip = new HashMap<>();
                    processExportExerciseList(exercises, outputStream, template, inputStream, mapToZip);
                } else {
                    processExportExerciseList(exercises, outputStream, template, inputStream, null);
                }
            } catch (Exception e) {
                log.error("### In writeExercisesToOutputStream caught exception", e);
            }
        }
    }

    private void writeZipArchive(Map<String, ByteArrayOutputStream> docs, OutputStream outputStream) {
        if (CollectionUtils.isEmpty(docs) || isNull(outputStream)) {
            log.error("### In writeZipArchive get null or empty docs or ouptupstream");
            return;
        }

        final String exceptionMsg = "### In writeZipArchive caught exception";
        try (ZipOutputStream zos = new ZipOutputStream(new BufferedOutputStream(outputStream))) {
            docs.forEach((filename, byteArrayOutputStream) -> {
                ZipEntry zipEntry = new ZipEntry(filename);
                try {
                    zos.putNextEntry(zipEntry);
                    zos.write(byteArrayOutputStream.toByteArray());
                    zos.closeEntry();
                } catch (IOException e) {
                    log.error(exceptionMsg, e);
                } finally {
                    try {
                        byteArrayOutputStream.close();
                    } catch (IOException e) {
                        log.error(exceptionMsg, e);
                    }
                }
            });
        } catch (IOException e) {
            log.error(exceptionMsg, e);
        }
    }

    private void processExportExerciseList(List<Exercise> exercises, ServletOutputStream outputStream,
                                           DocTemplate template, InputStream inputStream,
                                           Map<String, ByteArrayOutputStream> mapToZip) throws Exception {
        Document doc = Document.loadDocument(inputStream);

        boolean hasSuccessfullyProcessedTable = false;
        for (Table table : doc.getTableList()) {
            if (hasSuccessfullyProcessedTable) {
                break;
            }
            final List<Row> rowList = table.getRowList();
            Row dataRow = rowList.get(rowList.size() - 1);
            final int cellCount = dataRow.getCellCount();
            for (int k = 0; k < cellCount; k++) {
                final String srcCellStringValue = dataRow.getCellByIndex(k).getStringValue();
                // Если нашли в конечной строке паттерн, обрабатываем все ячейки увеличивая таблицу
                if (srcCellStringValue.contains(ExercisesExportPatternsEnum.ID.getPattern())) {
                    for (int i = 0; i < exercises.size(); i++) {
                        Exercise exercise = exercises.get(i);
                        fillDataToNewRow(dataRow, cellCount, i, exercise, table.appendRow());
                    }
                    // Удаляем исходную строку
                    table.removeRowsByIndex(dataRow.getRowIndex(), 1);
                    hasSuccessfullyProcessedTable = true;
                    break;
                }
            }
        }
        // Заменяем все паттерны в оставшемся документе (помимо уже обработанной таблицы)
        replaceAllPatternsInCellExerciseListDoc(doc, template.getName());

        if (isNull(mapToZip)) {
            doc.save(outputStream);
        } else {
            try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
                doc.save(baos);
                mapToZip.put(template.getName() + LocalDate.now().format(DateTimeFormatter.ISO_DATE)
                        + "." + template.getMimeType().name().toLowerCase(), baos);
            }
            if (!CollectionUtils.isEmpty(mapToZip)) {
                writeZipArchive(mapToZip, outputStream);
            }
        }
    }

    private void replaceAllPatternsInCellExerciseListDoc(Document doc, String documentName) {
        try {
            replaceTextInOdfDocument(doc, ExercisesExportPatternsEnum.NAME.getPatternEscapedCharacters(),
                    documentName);
            replaceTextInOdfDocument(doc, ExercisesExportPatternsEnum.DATE.getPatternEscapedCharacters(),
                    LocalDate.now().format(DateTimeFormatter.ISO_DATE));
        } catch (InvalidNavigationException e) {
            log.error("In replaceAllPatternsInCellExerciseListDoc get exception for document: {}", doc.toString());
        }
    }

    private void replaceTextInOdfDocument(Document doc, String patternToReplace, String newText)
            throws InvalidNavigationException {
        TextNavigation search = new TextNavigation(patternToReplace, doc);
        while (search.hasNext()) {
            TextSelection item = (TextSelection) search.nextSelection();
            item.replaceWith(newText);
        }
    }

    private void fillDataToNewRow(Row srcRow, int cellCount, int counter, Exercise exercise, Row destRow) {
        for (int cellIndex = 0; cellIndex < cellCount; cellIndex++) {
            final Cell srcCell = srcRow.getCellByIndex(cellIndex);
            final Cell destCell = destRow.getCellByIndex(cellIndex);
            destCell.addParagraph(srcCell.getStringValue());
            replaceAllPatternsInExerciseListTemplate(destCell.getOdfElement(),
                    exercise, counter + 1);
        }
    }

    private void replaceAllPatternsInExerciseListTemplate(TableTableCellElementBase cell, Exercise exercise,
                                                          int orderNumber) {
        try {
            replaceTextInOdfDocument(cell, ExercisesExportPatternsEnum.ID.getPatternEscapedCharacters(),
                    String.valueOf(exercise.getId()));
            replaceTextInOdfDocument(cell, ExercisesExportPatternsEnum.NAME.getPatternEscapedCharacters(),
                    exercise.getName());
            replaceTextInOdfDocument(cell, ExercisesExportPatternsEnum.DESCRIPTION.getPatternEscapedCharacters(),
                    exercise.getDescription());
            replaceTextInOdfDocument(cell, ExercisesExportPatternsEnum.TAGS.getPatternEscapedCharacters(),
                    exercise.getTags().stream().map(Tag::getName).collect(Collectors.joining(", ")));
        } catch (InvalidNavigationException e) {
            log.error("In replaceAllPatternsInExerciseListTemplate get exception for document: {}", cell.toString());
        }
    }

    private void replaceTextInOdfDocument(OdfElement doc, String patternToReplace, String newText)
            throws InvalidNavigationException {
        TextNavigation search = new TextNavigation(patternToReplace, doc);
        replaceTextNavigation(newText, search);
    }

    private void replaceTextNavigation(String newText, TextNavigation search) throws InvalidNavigationException {
        if (!StringUtils.hasText(newText)) {
            newText = "";
        }
        while (search.hasNext()) {
            TextSelection item = (TextSelection) search.nextSelection();
            item.replaceWith(newText);
        }
    }

    private void writeString(OutputStream outputStream, String header) throws IOException {
        String headerLineNextStr = header + "\n";
        outputStream.write(headerLineNextStr.getBytes(EXPORT_CSV_CHARSET));
    }

    private String getHeaderLineStr(Class<?> clazz) {
        return Arrays.stream(clazz.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(DescriptionAnnotation.class))
                .map(field -> field.getAnnotation(DescriptionAnnotation.class).value())
                .collect(Collectors.joining(EXPORT_CSV_DELIMITER));
    }
}
