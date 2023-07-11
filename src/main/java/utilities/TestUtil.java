package utilities;

import org.apache.poi.ss.usermodel.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class TestUtil {

    public static final String excelFileLocation = "\\resources\\InputData.xlsx";

    public static FileInputStream file;
    public static Workbook book;




    public static HashMap<String, String> getDataFromExcel(String testID)  {
        List<String> allColumns = new ArrayList<>();
        List<String> columnData = new ArrayList<>();
        HashMap<String, String> testData = new HashMap<>();
        try{
            file = new FileInputStream(System.getProperty("user.dir") + excelFileLocation);
            book = WorkbookFactory.create(file);
        }catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }

        Sheet sheetName = book.getSheet("Customer Details");
        Row desiredRow = sheetName.getRow(0);

        for (Cell columns:
             desiredRow) {
            allColumns.add(columns.toString());
        }
//        Iterator<Cell> columns= desiredRow.cellIterator();
//        while(columns.hasNext()){
//            allColumns.add(columns.next().toString());
//        }

//        Iterator<Row> rows = sheetName.rowIterator();
//        while (rows.hasNext()){
//            Row row = rows.next();
//            columnData = readValues(row);
//            testData = addData(allColumns, columnData);
//            if(testData.get("TestID").equalsIgnoreCase(testID))
//                break;
//            else{
//                clearData(testData,columnData);
//            }
//        }

        for (Row rows: sheetName
             ) {
            columnData = readValues(rows);
            testData = addData(allColumns, columnData);
            if(testData.get("TestID").equalsIgnoreCase(testID))
                break;
            else{
                clearData(testData,columnData);
            }
        }
        return testData;
}

    private static void clearData(HashMap<String, String> testData, List<String> columnData) {
        testData.clear();
        columnData.clear();
    }

    private static HashMap<String, String> addData(List<String> allColumns, List<String> columnData) {
        HashMap<String, String> data = new HashMap<>();
        Iterator<String> columnNames = allColumns.iterator();
        Iterator<String> rowValues = columnData.iterator();
        while (columnNames.hasNext() && rowValues.hasNext()){
            data.put(columnNames.next(), rowValues.next().trim());
        }

//        for (String columnNames: allColumns
//             ) {
//            for (String rowValues: columnData
//                 ) {
//                data.put(columnNames, rowValues);
//            }
//
//        }
        return data;
    }

    private static List<String> readValues(Row row) {
        List<String> allColumnsValues = new ArrayList<>();
        Iterator<Cell> columns = row.iterator();
        while(columns.hasNext()){
            allColumnsValues.add(columns.next().toString());
        }
        return allColumnsValues;
    }
    }
