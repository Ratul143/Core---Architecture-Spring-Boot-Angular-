package util;

import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

@Configuration
public class ReportNameGenerator {

    public String customReportName(String reportName){
        LocalDateTime currentDateTime = LocalDateTime.now();
        return reportName+"_"+currentDateTime.getYear()+currentDateTime.getMonthValue()
                +currentDateTime.getDayOfMonth()+currentDateTime.getHour()+
                currentDateTime.getMinute()+currentDateTime.getSecond();
    }
}
