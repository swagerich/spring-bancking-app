package com.erich.dev.util.printer;

import com.erich.dev.dto.TransactionDto;
import com.spire.xls.*;

import java.awt.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ExcelMethods {
    public static final String FILE_NAME = "transactions";
    public synchronized  static byte[] toExcel(List<TransactionDto> transactions) {

        String[] header = {"Id", "type", "amount", "destinationBank", "transactionDate", "userId"};

        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            Workbook workbook = new Workbook();
            Worksheet sheet = workbook.getWorksheets().get(0);

            // Encabezados
            for (int col = 0; col < header.length; col++) {
                sheet.getCellRange(1, col + 1).setValue(header[col]);
                sheet.getCellRange(1, col + 1).getStyle().setColor(Color.GREEN);
            }

            // Datos
            for (int row = 0; row < transactions.size(); row++) {
                TransactionDto objeto = transactions.get(row);

                CellRange cellRange = sheet.getCellRange(row + 2, 1);
                cellRange.getStyle().setColor(Color.YELLOW);
                cellRange.setText(objeto.getId().toString());

                sheet.getCellRange(row + 2, 2).setValue(objeto.getType().toString());
                sheet.getCellRange(row + 2, 3).setText(objeto.getAmount().toString());
                sheet.getCellRange(row + 2, 4).setText(objeto.getDestinationBank());
                sheet.getCellRange(row + 2, 5).setText(objeto.getTransactionDate().toString());
                sheet.getCellRange(row + 2, 6).setText(objeto.getUserId().toString());

                CellRange rowRange = sheet.getCellRange(1, 1, row + 2, header.length);
                rowRange.borderInside(LineStyleType.Thin, Color.BLACK);
                rowRange.borderAround(LineStyleType.Medium, Color.BLACK);
            }

            for (int col = 0; col < header.length; col++) {
                sheet.getAllocatedRange().autoFitColumns();
                sheet.getAllocatedRange().autoFitRows();
            }

            workbook.saveToStream(outputStream, FileFormat.Version2016);
            return outputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }
    public static String formatFileName(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String timestamp = sdf.format(new Date());
        String fileName = "archivo_" + timestamp + ".xlsx";
        return FILE_NAME + "/" + fileName;
    }
}

