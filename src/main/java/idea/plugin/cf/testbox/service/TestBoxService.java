package idea.plugin.cf.testbox.service;

import com.intellij.ide.BrowserUtil;
import idea.plugin.cf.testbox.config.TestBoxConfig;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.nio.file.Path;

public class TestBoxService {

    private TestBoxConfig testBoxConfig;

    public TestBoxService(TestBoxConfig testBoxConfig) {
        this.testBoxConfig = testBoxConfig;
    }

    public void runTest(Path specFile, Path testBoxFolderBase) {
        Path relativeSpecFilePath = testBoxFolderBase.relativize(specFile);
        Path relativeSpecFileFolderPath = relativeSpecFilePath.getParent();

        String urlSpecFilePath = removeFileExtension(toUrlFormat(relativeSpecFilePath));
        String urlSpecFileFolderPath = toUrlFormat(relativeSpecFileFolderPath);

        runTest(urlSpecFileFolderPath, urlSpecFilePath);
    }

    private void runTest(String directory, String testBundles) {
        String url = String.format("%stestbox/system/runners/HTMLRunner.cfm?method=runRemote&directory=%s&testBundles=%s",
                testBoxConfig.getHost(),
                directory,
                testBundles
        );
        BrowserUtil.browse(url);
    }

    @NotNull
    private String toUrlFormat(Path path) {
        return path.toString().replace(File.separator, ".");
    }

    private String removeFileExtension(String fileName) {
        return fileName.substring(0, fileName.lastIndexOf('.'));
    }
}
