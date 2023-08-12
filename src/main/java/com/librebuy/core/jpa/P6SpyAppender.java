package com.librebuy.core.jpa;

import com.p6spy.engine.logging.Category;
import com.p6spy.engine.spy.appender.Slf4JLogger;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;

public class P6SpyAppender extends Slf4JLogger {
    // https://en.wikipedia.org/wiki/ANSI_escape_code
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";
    public static final String ANSI_BRIGHT_BLUE = "\u001B[94m";
    public static final String ANSI_BRIGHT_MAGENTA = "\u001B[95m";

    enum AnsiColor {
        RESET(),
        RED(31, 41),
        GREEN(32, 42),
        YELLOW(33, 43);

        private int fontColorCode;
        private int backgroundColorCode;

        private AnsiColor() {
            this(0, 0);
        }

        private AnsiColor(int fontColorCode, int backgroundColorCode) {
        }
    }

    private final Logger log;
    private String previousBatchSql;

    @Setter
    private static boolean enabled = true;
    private static final Pattern ALIAS_PATTERN = Pattern.compile("_\\d$");


    public P6SpyAppender() {
        log = LoggerFactory.getLogger("SQL");
    }

    @Override
    public void logSQL(int connectionId, String now, long elapsed, Category category, String prepared, String sql, String url) {
        if (shouldSkipLoggingSql(category, sql))
            return;

        String optimizedSql = optimizeSqlAliases(sql);
        optimizedSql = colorSql(optimizedSql);
        logSql(category, optimizedSql);
    }

    private boolean shouldSkipLoggingSql(Category category, String sql) {
        if (!enabled) return true;

        // Pomiń puste logi (commit)
        if (category == Category.COMMIT) return true;

        // Pomiń logi zmiany schematu
        if (sql.indexOf("set schema") == 0)
            return true;

        if (category == Category.ROLLBACK && sql.isEmpty())
            return true;

        // Pomiń zduplikowane logi 'batch' (insert, update, delete)
        if (category == Category.BATCH) {
            if (Objects.equals(previousBatchSql, sql))
                return true;

            previousBatchSql = sql;
        }
        return false;
    }

    /**
     * Funkcja usuwa aliasy kolumn oraz usuwa (dla zapytań z jednej tabeli) / aktualizuje (dla zapytań z wielu tabel) aliasy tabel.
     * <p>
     * Działa tylko dla hibernate-owych zapytań "SELECT"!
     */
    String optimizeSqlAliases(String sql) {
        if (!sql.startsWith("select"))
            return sql;

        final String[] items = sql.split(" ");

        // Zbierz wszystkie aliasy tablic ------------------------------------------------------------------------------
        Map<String, String> hibernateTableAliases = new HashMap<>(); // <tableAlias, tableName>

        for (int i = 0; i < items.length; i++) {
            final String item = items[i];

            if ("from".equals(item) || "join".equals(item)) {
                final String tableName = i < items.length - 1 ? items[i + 1] : null;
                final String tableAlias = i < items.length - 2 ? items[i + 2] : null;

                if (tableAlias != null && ALIAS_PATTERN.matcher(tableAlias).find())
                    hibernateTableAliases.put(tableAlias, tableName);
            }
        }

        // Wygeneruj nowe aliasy tablic --------------------------------------------------------------------------------
        Map<String, String> newTableAliases = new HashMap<>(); // <oldTableAlias, newTableAlias>
        for (String hibernateAlias : hibernateTableAliases.keySet()) {
            final String tableNameWithSchema = hibernateTableAliases.get(hibernateAlias);

            final int dotIndex = tableNameWithSchema.indexOf('.');
            final String schema = dotIndex > 0 ? tableNameWithSchema.substring(0, dotIndex) : null; // Schemat tabeli
            final String tableName = dotIndex > 0 ? tableNameWithSchema.substring(dotIndex + 1) : tableNameWithSchema; // Nazwa tabeli bez schematu
            final String firstSchemaLetter = schema != null ? (schema.charAt(0) + "_") : ""; // Pierwsza litera schematu tabeli

            final String[] tableNameParts = tableName.split("_");

            for (int wordTrimSize = 1; wordTrimSize < 20; wordTrimSize++) {
                StringBuilder sb = new StringBuilder(firstSchemaLetter);
                for (final String tableNameWord : tableNameParts) {
                    sb.append(tableNameWord, 0, Math.min(wordTrimSize, tableNameWord.length()));
                }

                final String generatedAlias = sb.toString();
                if (!newTableAliases.containsValue(generatedAlias)) {
                    newTableAliases.put(hibernateAlias, generatedAlias);
                    break;
                }
            }
        }

        String[] keys = newTableAliases.keySet().toArray(new String[0]);
        String[] values = newTableAliases.values().toArray(new String[0]);

        if (keys.length == 1) {
            keys = new String[]{keys[0] + ".", " " + keys[0]};
            values = new String[]{"", ""};
        }

        return StringUtils.replaceEach(sql, keys, values);
    }

    private static String colorSql(String sql) {
        String color = sql.startsWith("select") ? ANSI_BRIGHT_BLUE : ANSI_BRIGHT_MAGENTA;
        sql = color + sql + ANSI_RESET;
        return sql;
    }

    private void logSql(Category category, String sql) {
        if (Category.ERROR.equals(category)) {
            log.error(sql);
        } else if (Category.WARN.equals(category)) {
            log.warn(sql);
        } else if (Category.DEBUG.equals(category)) {
            log.debug(sql);
        } else {
            log.info(sql);
        }
    }

}