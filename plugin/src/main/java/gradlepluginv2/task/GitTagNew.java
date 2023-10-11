package gradlepluginv2.task;

import static gradlepluginv2.utils.TagUtils.out;

import gradlepluginv2.exeptions.TaggedException;
import gradlepluginv2.utils.Commands;
import gradlepluginv2.utils.TagUtils;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;
import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;
import java.io.IOException;

public class GitTagNew extends DefaultTask {
    private final Logger log;

    public GitTagNew() {
        this.setGroup("Custom gradle plugin homework");
        log = LogManager.getLogger(GitTagNew.class);
        Configurator.setRootLevel(Level.INFO);
    }

    @TaskAction
    public void createGitTag(){
        Process process;
        String lastTag;
        String branch;
        String newTag;

        try{
            process = Runtime.getRuntime().exec(Commands.COMMAND_GET_LAST_TAG);
            lastTag = TagUtils.getLastTagStr(process);
            branch = out(Commands.COMMAND_GET_BRANCH);
            newTag = TagUtils.createTag(lastTag,branch);

            Runtime.getRuntime().exec(Commands.COMMAND_CREATE_TAG+newTag);
            log.info("new created tag: "+newTag);
            Runtime.getRuntime().exec(Commands.COMMAND_PUSH_TAG+newTag);
            log.info("tag: "+newTag+" push: successful");
        }catch (TaggedException | IOException e){
            log.warn(e.getMessage());
        }
    }
}
