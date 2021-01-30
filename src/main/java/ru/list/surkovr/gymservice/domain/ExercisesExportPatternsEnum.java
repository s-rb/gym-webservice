package ru.list.surkovr.gymservice.domain;

public enum ExercisesExportPatternsEnum {

    ID("$id$", "\\$id\\$"),
    NAME("$name$", "\\$name\\$"),
    DESCRIPTION("$description$", "\\$description\\$"),
    DATE("$date$", "\\$date\\$"),
    TAGS("$tags$", "\\$tags\\$");

    private String pattern;
    private String patternEscapedCharacters;

    ExercisesExportPatternsEnum(String pattern, String patternEscapedCharacters) {
        this.pattern = pattern;
        this.patternEscapedCharacters = patternEscapedCharacters;
    }

    public String getPattern() {
        return pattern;
    }

    public String getPatternEscapedCharacters() {
        return patternEscapedCharacters;
    }
}
