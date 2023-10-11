package gradlepluginv2.task;

import gradlepluginv2.exeptions.UncommittedException;
import gradlepluginv2.utils.Commands;
import gradlepluginv2.utils.TagUtils;
import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;

import java.io.IOException;

import static gradlepluginv2.utils.TagUtils.out;

public class UncommittedChanges extends DefaultTask {

    public UncommittedChanges() {
        this.setGroup("Custom gradle plugin homework");
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
            }else this.getExtensions().add("result",false);
        }catch (UncommittedException | IOException e){
            System.out.println(e.getMessage());
        }
    }
}
