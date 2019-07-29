package idea.plugin.cf.testbox.action;

import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.vfs.VirtualFile;
import idea.plugin.cf.testbox.service.TestBoxFolderFinder;
import idea.plugin.cf.testbox.service.TestBoxService;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

public class RunTestAction extends AnAction {

    private TestBoxFolderFinder testBoxFolderFinder;
    private TestBoxService testBoxService;

    public RunTestAction() {
        this.testBoxFolderFinder = ServiceManager.getService(TestBoxFolderFinder.class);
        this.testBoxService = ServiceManager.getService(TestBoxService.class);
    }

    @Override
    public void actionPerformed(AnActionEvent e) {
        try {
            doActionPerform(e);
        } catch (Exception exception) {
            Notifications.Bus.notify(new Notification(
                    "CF Testbox Plugin",
                    "CF Testbox Plugin Error",
                    exception.getMessage() != null ? exception.getMessage() : exception.toString(),
                    NotificationType.ERROR
            ));
        }
    }

    @Override
    public void update(AnActionEvent e) {
        VirtualFile targetFile = e.getDataContext().getData(CommonDataKeys.VIRTUAL_FILE);
        e.getPresentation().setEnabledAndVisible(targetFile != null && targetFile.getPath().endsWith(".cfc"));
    }

    private void doActionPerform(AnActionEvent e) {
        Path specFile = getTargetSpecFile(e);
        Path testBoxFolderBase = testBoxFolderFinder.findTestBoxFolderBaseInParents(specFile);
        testBoxService.runTest(specFile, testBoxFolderBase);
    }

    private Path getTargetSpecFile(AnActionEvent e) {
        VirtualFile specFile = e.getDataContext().getData(CommonDataKeys.VIRTUAL_FILE);
        Objects.requireNonNull(specFile, "Spec file is unexpectedly null");
        return Paths.get(specFile.getPath());
    }
}
