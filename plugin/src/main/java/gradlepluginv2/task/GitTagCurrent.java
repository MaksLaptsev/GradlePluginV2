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

public class GitTagCurrent extends DefaultTask {
    private final Logger log;

    public GitTagCurrent() {
        this.setGroup("Custom gradle plugin homework");
        log = LogManager.getLogger(GitTagCurrent.class);
        Configurator.setRootLevel(Level.INFO);
    }

    @TaskAction
    public void isCurrentTagExist(){
        boolean isTagExist;
        try{
            double currentTag = getCurrentTag(Commands.COMMAND_GET_CURRENT_TAG);
            isTagExist = currentTag != 0.0;
            this.getExtensions().add("result",isTagExist);

            if (isTagExist){
                throw new TaggedException("The tag already exists on the current commit!!");
            }else log.info("Is current tag exist: "+ false);
        }catch (TaggedException | IOException e){
            log.warn(e.getMessage());
        }
    }

    private double getCurrentTag(String command) throws IOException {
        double tag = 0.0;
        String[] out = out(command)
                .replace("\n","")
                .replace(":","")
                .split(" ");
        for (int i = 0; i < out.length; i++){
            if (out[i].equals("tag")){
                tag = TagUtils.getNumericTag(out[i+1]);
                return tag;
            }
        }
        return tag;
    }
}
