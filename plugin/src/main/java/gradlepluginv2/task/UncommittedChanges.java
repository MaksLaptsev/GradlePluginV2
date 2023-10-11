package gradlepluginv2.task;

import static gradlepluginv2.utils.TagUtils.out;

import gradlepluginv2.exeptions.UncommittedException;
import gradlepluginv2.utils.Commands;
import gradlepluginv2.utils.TagUtils;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;
import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;
import java.io.IOException;

public class UncommittedChanges extends DefaultTask {
    private final Logger log;

    public UncommittedChanges() {
        this.setGroup("Custom gradle plugin homework");
        log = LogManager.getLogger(UncommittedChanges.class);
        Configurator.setRootLevel(Level.INFO);
    }

    @TaskAction
    public void hasUncommittedChanges(){
        boolean isUncommitted;
        boolean isUncommitted_cache;

        try{
            isUncommitted = !(out(Commands.COMMAND_DIFF) == null || out(Commands.COMMAND_DIFF).equals(""));
            isUncommitted_cache = !(out(Commands.COMMAND_DIFF_CACHE) == null || out(Commands.COMMAND_DIFF_CACHE).equals(""));


            if (isUncommitted || isUncommitted_cache){
                this.getExtensions().add("result",true);
                String uncommittedTag = TagUtils.createUncommittedTag(out(Commands.COMMAND_UNCOMM_TAG));
                throw new UncommittedException("There are uncommitted changes\n"+uncommittedTag);
            }else
            {
                this.getExtensions().add("result",false);
                log.info("Has uncommitted changes: "+false);
            }
        }catch (UncommittedException | IOException e){
            log.warn(e.getMessage());
        }
    }
}
