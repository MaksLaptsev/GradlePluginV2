package gradlepluginv2.task;

import static gradlepluginv2.utils.TagUtils.out;

import gradlepluginv2.exeptions.GitNotFoundException;
import gradlepluginv2.utils.Commands;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;
import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;
import java.io.IOException;

public class GitDirectory extends DefaultTask {
    private final Logger log;

    public GitDirectory() {
        this.setGroup("Custom gradle plugin homework");
        log = LogManager.getLogger(GitDirectory.class);
        Configurator.setRootLevel(Level.INFO);
    }

    @TaskAction
    public void isGirDirectory(){
        boolean isGitDir;
        try{
            isGitDir = Boolean.parseBoolean(out(Commands.COMMAND_IS_GIT_DIRECT)
                    .replace("\n", "").split(" ")[0]);
            this.getExtensions().add("result", isGitDir);
            log.info("Is git directory: "+isGitDir);
            if (!isGitDir){
                throw new GitNotFoundException("Is not a Git directory!!");
            }
        }catch (GitNotFoundException | IOException e){
            log.warn(e.getMessage());
        }
    }
}
