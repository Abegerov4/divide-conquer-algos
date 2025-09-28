package metrics;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Utility for writing benchmark results to CSV
 */
public class CSVWriter implements AutoCloseable {
    private final FileWriter writer;
    private boolean headerWritten = false;

    public CSVWriter(String filename) throws IOException {
        this.writer = new FileWriter(filename);
    }

    public void writeHeader(String... columns) throws IOException {
        writeRow(Arrays.asList(columns));
        headerWritten = true;
    }

    public void writeRow(List<Object> values) throws IOException {
        StringBuilder line = new StringBuilder();
        for (int i = 0; i < values.size(); i++) {
            if (i > 0) line.append(",");
            line.append(escapeCsv(values.get(i).toString()));
        }
        line.append("\n");
        writer.write(line.toString());
    }

    public void writeRow(Object... values) throws IOException {
        writeRow(Arrays.asList(values));
    }

    private String escapeCsv(String value) {
        if (value.contains(",") || value.contains("\"") || value.contains("\n")) {
            return "\"" + value.replace("\"", "\"\"") + "\"";
        }
        return value;
    }

    @Override
    public void close() throws IOException {
        writer.close();
    }
}