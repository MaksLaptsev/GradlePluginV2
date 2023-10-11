package gradlepluginv2.task;

import gradlepluginv2.exeptions.GitNotFoundException;
import gradlepluginv2.utils.Commands;
import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;

import java.io.IOException;

import static gradlepluginv2.utils.TagUtils.out;

public class GitDirectory extends DefaultTask {

    public GitDirectory() {
        this.setGroup("Custom gradle plugin homework");
    }

    @TaskAction
    public void isGirDirectory(){
        boolean isGitDir;
        try{
            isGitDir = Boolean.parseBoolean(out(Commands.COMMAND_IS_GIT_DIRECT)
                    .replace("\n", "").split(" ")[0]);
            this.getExtensions().add("result", isGitDir);
            if (!isGitDir){
                throw new GitNotFoundException("Is not a Git directory!!");
            }
        }catch (GitNotFoundException | IOException e){
            System.out.println(e.getMessage());
        }
    }
}
