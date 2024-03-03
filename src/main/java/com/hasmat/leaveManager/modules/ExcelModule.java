package com.hasmat.leaveManager.modules;

import com.hasmat.leaveManager.model.Issue;
import com.hasmat.leaveManager.model.Leave;
import com.hasmat.leaveManager.model.User;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class ExcelModule {

    public static Workbook createExcelFile(String empId, List<Leave> leaves) {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("LeaveData");

        // Create cell style for data area (with borders)
        CellStyle cellStyleWithDataBorders = workbook.createCellStyle();
        cellStyleWithDataBorders.setBorderBottom(BorderStyle.THIN);
        cellStyleWithDataBorders.setBorderTop(BorderStyle.THIN);
        cellStyleWithDataBorders.setBorderLeft(BorderStyle.THIN);
        cellStyleWithDataBorders.setBorderRight(BorderStyle.THIN);

        int rowNum = 0;
        Row headerRow = sheet.createRow(rowNum++);
        headerRow.createCell(0).setCellValue("LeaveId");
        headerRow.createCell(1).setCellValue("Employee ID");
        headerRow.createCell(2).setCellValue("Leave Type");
        headerRow.createCell(3).setCellValue("Leave Category");
        headerRow.createCell(4).setCellValue("Leave From");
        headerRow.createCell(5).setCellValue("Leave Till");
        headerRow.createCell(6).setCellValue("Approved By");
        headerRow.createCell(7).setCellValue("Is Approved");
        headerRow.createCell(8).setCellValue("Created At");
        headerRow.createCell(9).setCellValue("Updated At");

        // Apply bold font style and light green background color to the header row
        Font boldFont = workbook.createFont();
        boldFont.setBold(true);
        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFont(boldFont);
        headerStyle.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerStyle.setBorderBottom(BorderStyle.THIN);
        headerStyle.setBorderTop(BorderStyle.THIN);
        headerStyle.setBorderLeft(BorderStyle.THIN);
        headerStyle.setBorderRight(BorderStyle.THIN);

        for (Cell cell : headerRow) {
            cell.setCellStyle(headerStyle);
        }

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        for (Leave leave : leaves) {
            Row dataRow = sheet.createRow(rowNum++);
            dataRow.createCell(0).setCellValue(leave.getLeaveId());
            dataRow.createCell(1).setCellValue(leave.getEmpId());
            dataRow.createCell(2).setCellValue(leave.getLeaveType());
            dataRow.createCell(3).setCellValue(leave.getLeaveCategory());
            dataRow.createCell(4).setCellValue(leave.getLeaveFrom().format(dateTimeFormatter));
            dataRow.createCell(5).setCellValue(leave.getLeaveTill().format(dateTimeFormatter));
            dataRow.createCell(6).setCellValue(leave.getApprovedBy());
            dataRow.createCell(7).setCellValue(leave.getIsApproved());
            dataRow.createCell(8).setCellValue(leave.getCreatedAt().format(dateTimeFormatter));
            LocalDateTime updatedAt = leave.getUpdatedAt();
            if (updatedAt != null) {
                dataRow.createCell(9).setCellValue(updatedAt.format(dateTimeFormatter));
            } else {
                dataRow.createCell(9).setCellValue("");
            }
        }

        // Remove borders from columns other than the data area
        for (int i = 0; i < 12; i++) {
            sheet.autoSizeColumn(i);
            for (int j = 1; j < sheet.getLastRowNum() + 1; j++) {
                Cell cell = sheet.getRow(j).getCell(i);
                if (cell == null) {
                    continue;
                }
                if (cell.getCellType() != CellType.BLANK) {
                    cell.setCellStyle(cellStyleWithDataBorders);
                }
            }
        }

        return workbook;
    }

    public static Workbook createExcelFileForLeaves(List<Leave> leaves) {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("LeaveData");

        // Create cell style for data area (with borders)
        CellStyle cellStyleWithDataBorders = workbook.createCellStyle();
        cellStyleWithDataBorders.setBorderBottom(BorderStyle.THIN);
        cellStyleWithDataBorders.setBorderTop(BorderStyle.THIN);
        cellStyleWithDataBorders.setBorderLeft(BorderStyle.THIN);
        cellStyleWithDataBorders.setBorderRight(BorderStyle.THIN);

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        if (!leaves.isEmpty()) {
            int rowNum = 0;

            // Populate header row
            Row headerRow = sheet.createRow(rowNum++);
            headerRow.createCell(0).setCellValue("LeaveId");
            headerRow.createCell(1).setCellValue("Employee ID");
            headerRow.createCell(2).setCellValue("Leave Type");
            headerRow.createCell(3).setCellValue("Leave Category");
            headerRow.createCell(4).setCellValue("Leave From");
            headerRow.createCell(5).setCellValue("Leave Till");
            headerRow.createCell(6).setCellValue("Approved By");
            headerRow.createCell(7).setCellValue("Is Approved");
            headerRow.createCell(8).setCellValue("Created At");
            headerRow.createCell(9).setCellValue("Updated At");

            // Apply bold font style and light green background color to the header row
            Font boldFont = workbook.createFont();
            boldFont.setBold(true);
            CellStyle headerStyle = workbook.createCellStyle();
            headerStyle.setFont(boldFont);
            headerStyle.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            headerStyle.setBorderBottom(BorderStyle.THIN);
            headerStyle.setBorderTop(BorderStyle.THIN);
            headerStyle.setBorderLeft(BorderStyle.THIN);
            headerStyle.setBorderRight(BorderStyle.THIN);

            for (Cell cell : headerRow) {
                cell.setCellStyle(headerStyle);
            }

            // Populate data rows
            for (Leave leave : leaves) {
                Row dataRow = sheet.createRow(rowNum++);
                dataRow.createCell(0).setCellValue(leave.getLeaveId());
                dataRow.createCell(1).setCellValue(leave.getEmpId());
                dataRow.createCell(2).setCellValue(leave.getLeaveType());
                dataRow.createCell(3).setCellValue(leave.getLeaveCategory());
                dataRow.createCell(4).setCellValue(leave.getLeaveFrom().format(dateTimeFormatter));
                dataRow.createCell(5).setCellValue(leave.getLeaveTill().format(dateTimeFormatter));
                dataRow.createCell(6).setCellValue(leave.getApprovedBy());
                dataRow.createCell(7).setCellValue(leave.getIsApproved());
                dataRow.createCell(8).setCellValue(leave.getCreatedAt().format(dateTimeFormatter));
                LocalDateTime updatedAt = leave.getUpdatedAt();
                if (updatedAt != null) {
                    dataRow.createCell(9).setCellValue(updatedAt.format(dateTimeFormatter));
                } else {
                    dataRow.createCell(9).setCellValue("");
                }
            }
        }

        // Remove borders from columns other than the data area
        for (int i = 0; i < 12; i++) {
            sheet.autoSizeColumn(i);
            for (int j = 1; j < sheet.getLastRowNum() + 1; j++) {
                Cell cell = sheet.getRow(j).getCell(i);
                if (cell == null) {
                    continue;
                }
                if (cell.getCellType() != CellType.BLANK) {
                    cell.setCellStyle(cellStyleWithDataBorders);
                }
            }
        }

        return workbook;
    }

    public static Workbook createExcelFileForUsers(List<User> users) {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("UserData");

        // Create cell style for data area (with borders)
        CellStyle cellStyleWithDataBorders = workbook.createCellStyle();
        cellStyleWithDataBorders.setBorderBottom(BorderStyle.THIN);
        cellStyleWithDataBorders.setBorderTop(BorderStyle.THIN);
        cellStyleWithDataBorders.setBorderLeft(BorderStyle.THIN);
        cellStyleWithDataBorders.setBorderRight(BorderStyle.THIN);

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        if (!users.isEmpty()) {
            int rowNum = 0;

            // Populate header row
            Row headerRow = sheet.createRow(rowNum++);
            headerRow.createCell(0).setCellValue("UserId");
            headerRow.createCell(1).setCellValue("First Name");
            headerRow.createCell(2).setCellValue("Last Name");
            headerRow.createCell(3).setCellValue("Employee ID");
            headerRow.createCell(4).setCellValue("Email");
            headerRow.createCell(5).setCellValue("Department");
            headerRow.createCell(6).setCellValue("Role");
            headerRow.createCell(7).setCellValue("Created At");
            headerRow.createCell(8).setCellValue("Updated At");

            // Apply bold font style and light blue background color to the header row
            Font boldFont = workbook.createFont();
            boldFont.setBold(true);
            CellStyle headerStyle = workbook.createCellStyle();
            headerStyle.setFont(boldFont);
            headerStyle.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            headerStyle.setBorderBottom(BorderStyle.THIN);
            headerStyle.setBorderTop(BorderStyle.THIN);
            headerStyle.setBorderLeft(BorderStyle.THIN);
            headerStyle.setBorderRight(BorderStyle.THIN);

            for (Cell cell : headerRow) {
                cell.setCellStyle(headerStyle);
            }

            // Populate data rows
            for (User user : users) {
                Row dataRow = sheet.createRow(rowNum++);
                dataRow.createCell(0).setCellValue(user.getUserId());
                dataRow.createCell(1).setCellValue(user.getUserFirstName());
                dataRow.createCell(2).setCellValue(user.getUserLastName());
                dataRow.createCell(3).setCellValue(user.getEmpId());
                dataRow.createCell(4).setCellValue(user.getUserEmail());
                dataRow.createCell(5).setCellValue(user.getUserDepartment());
                dataRow.createCell(6).setCellValue(user.getUserType());
                dataRow.createCell(7).setCellValue(user.getCreatedAt().format(dateTimeFormatter));
                LocalDateTime updatedAt = user.getUpdatedAt();
                if (updatedAt != null) {
                    dataRow.createCell(8).setCellValue(updatedAt.format(dateTimeFormatter));
                } else {
                    dataRow.createCell(8).setCellValue("");
                }
            }
        }

        // Remove borders from columns other than the data area
        for (int i = 0; i < 9; i++) {
            sheet.autoSizeColumn(i);
            for (int j = 1; j < sheet.getLastRowNum() + 1; j++) {
                Cell cell = sheet.getRow(j).getCell(i);
                if (cell == null) {
                    continue;
                }
                if (cell.getCellType() != CellType.BLANK) {
                    cell.setCellStyle(cellStyleWithDataBorders);
                }
            }
        }

        return workbook;
    }

    public static Workbook createExcelFileForIssues(List<Issue> issues) {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("IssuesData");

        // Create cell style for data area (with borders)
        CellStyle cellStyleWithDataBorders = workbook.createCellStyle();
        cellStyleWithDataBorders.setBorderBottom(BorderStyle.THIN);
        cellStyleWithDataBorders.setBorderTop(BorderStyle.THIN);
        cellStyleWithDataBorders.setBorderLeft(BorderStyle.THIN);
        cellStyleWithDataBorders.setBorderRight(BorderStyle.THIN);

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        if (!issues.isEmpty()) {
            int rowNum = 0;

            // Populate header row
            Row headerRow = sheet.createRow(rowNum++);
            headerRow.createCell(0).setCellValue("Issue Id");
            headerRow.createCell(1).setCellValue("Issue Title");
            headerRow.createCell(2).setCellValue("Issue Description");
            headerRow.createCell(3).setCellValue("Concerned Authority");
            headerRow.createCell(4).setCellValue("Reported By");
            headerRow.createCell(5).setCellValue("Status");
            headerRow.createCell(6).setCellValue("Created At");
            headerRow.createCell(7).setCellValue("Updated At");

            // Apply bold font style and light green background color to the header row
            Font boldFont = workbook.createFont();
            boldFont.setBold(true);
            CellStyle headerStyle = workbook.createCellStyle();
            headerStyle.setFont(boldFont);
            headerStyle.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            headerStyle.setBorderBottom(BorderStyle.THIN);
            headerStyle.setBorderTop(BorderStyle.THIN);
            headerStyle.setBorderLeft(BorderStyle.THIN);
            headerStyle.setBorderRight(BorderStyle.THIN);

            for (Cell cell : headerRow) {
                cell.setCellStyle(headerStyle);
            }

            // Populate data rows
            for (Issue issue : issues) {
                Row dataRow = sheet.createRow(rowNum++);
                dataRow.createCell(0).setCellValue(issue.getIssueId());
                dataRow.createCell(1).setCellValue(issue.getIssueTitle());
                dataRow.createCell(2).setCellValue(issue.getIssueDescription());
                dataRow.createCell(3).setCellValue(issue.getConcernedAuthority());
                dataRow.createCell(4).setCellValue(issue.getReportedBy());
                dataRow.createCell(5).setCellValue(issue.getStatus());
                dataRow.createCell(6).setCellValue(issue.getCreatedAt().format(dateTimeFormatter));
                LocalDateTime updatedAt = issue.getUpdatedAt();
                if (updatedAt != null) {
                    dataRow.createCell(7).setCellValue(updatedAt.format(dateTimeFormatter));
                } else {
                    dataRow.createCell(7).setCellValue("");
                }
            }
        }

        // Remove borders from columns other than the data area
        for (int i = 0; i < 8; i++) {
            sheet.autoSizeColumn(i);
            for (int j = 1; j < sheet.getLastRowNum() + 1; j++) {
                Cell cell = sheet.getRow(j).getCell(i);
                if (cell == null) {
                    continue;
                }
                if (cell.getCellType() != CellType.BLANK) {
                    cell.setCellStyle(cellStyleWithDataBorders);
                }
            }
        }

        return workbook;
    }

    public byte[] createExcelBytes(String empId, List<Leave> leaves) throws IOException {
        Workbook workbook = createExcelFile(empId, leaves);

        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            workbook.write(outputStream);
            return outputStream.toByteArray();
        } finally {
            workbook.close();
        }
    }

    public byte[] createExcelBytesForLeaves(List<Leave> leaves) throws IOException {
        Workbook workbook = createExcelFileForLeaves(leaves);

        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            workbook.write(outputStream);
            return outputStream.toByteArray();
        } finally {
            workbook.close();
        }
    }

    public byte[] createExcelBytesForUsers(List<User> users) throws IOException {
        Workbook workbook = createExcelFileForUsers(users);

        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            workbook.write(outputStream);
            return outputStream.toByteArray();
        } finally {
            workbook.close();
        }
    }

    public byte[] createExcelBytesForIssues(List<Issue> issues) throws IOException {
        Workbook workbook = createExcelFileForIssues(issues);

        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            workbook.write(outputStream);
            return outputStream.toByteArray();
        } finally {
            workbook.close();
        }
    }
}
