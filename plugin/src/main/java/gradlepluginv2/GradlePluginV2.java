/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package gradlepluginv2;

import gradlepluginv2.task.GitDirectory;
import gradlepluginv2.task.GitTagCurrent;
import gradlepluginv2.task.GitTagNew;
import gradlepluginv2.task.UncommittedChanges;
import org.gradle.api.Project;
import org.gradle.api.Plugin;

public class GradlePluginV2 implements Plugin<Project> {
    public void apply(Project project) {
        // Register a task
        GitDirectory isGitDirectory = project
                .getTasks()
                .register("IsGitDirectory", GitDirectory.class)
                .get();
        UncommittedChanges hasUncommittedChanges = project
                .getTasks()
                .register("HasUncommittedChanges",UncommittedChanges.class)
                .get();
        GitTagCurrent isCurrentTagExist = project
                .getTasks()
                .register("IsCurrentTagExist",GitTagCurrent.class)
                .get();
        GitTagNew createGitTag = project
                .getTasks()
                .register("CreateGitTag",GitTagNew.class)
                .get();


        createGitTag
                .dependsOn("HasUncommittedChanges")
                .dependsOn("IsCurrentTagExist")
                .onlyIf(x->!hasUncommittedChanges.getState().getSkipped()
                        &&!isCurrentTagExist.getState().getSkipped()
                        &&!(boolean)isCurrentTagExist.getExtensions().getByName("result")
                        &&!(boolean)hasUncommittedChanges.getExtensions().getByName("result"));

        isCurrentTagExist
                .dependsOn("HasUncommittedChanges")
                .onlyIf(x->!hasUncommittedChanges.getState().getSkipped()&&
                        !(boolean)hasUncommittedChanges.getExtensions().getByName("result"));

        hasUncommittedChanges
                .dependsOn("IsGitDirectory")
                .onlyIf(x->(boolean)isGitDirectory.getExtensions().getByName("result"));

    }
}
