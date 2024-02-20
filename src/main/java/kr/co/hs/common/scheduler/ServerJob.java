package kr.co.hs.common.scheduler;

import kr.co.hs.project.service.ProjectService;
import org.codehaus.plexus.util.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.IOException;

@Component
public class ServerJob {
    @Autowired
    ServletContext context;

    @Autowired
    ProjectService projectService;

    @Scheduled(fixedDelay = 300000)
    public void deleteFileExcel() throws InterruptedException, IOException {
        File directory = new File(context.getRealPath("resources/Upload/excel_file"));
        if(!directory.exists()){
            directory.mkdirs();
        }
        try {
            FileUtils.cleanDirectory(directory);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Scheduled(cron = "0 01 00 * * *")
    public void projectInsertTask() throws Exception{
        try {
            projectService.scheduleAutoInsert();
            System.out.println("DXD---------------------------------------------DXD");
        }catch (Exception e){
            System.out.println(e);
        }
    }
}
