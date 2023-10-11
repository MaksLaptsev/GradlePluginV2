package gradlepluginv2.task;

import static gradlepluginv2.utils.TagUtils.out;
import gradlepluginv2.exeptions.TaggedException;
import gradlepluginv2.utils.Commands;
import gradlepluginv2.utils.TagUtils;
import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;
import java.io.IOException;

public class GitTagNew extends DefaultTask {

    public GitTagNew() {
        this.setGroup("Custom gradle plugin homework");
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
            System.out.println("new created tag: "+newTag);
            Runtime.getRuntime().exec(Commands.COMMAND_PUSH_TAG+newTag);
        }catch (TaggedException | IOException e){
            System.out.println(e.getMessage());
        }
    }
}
