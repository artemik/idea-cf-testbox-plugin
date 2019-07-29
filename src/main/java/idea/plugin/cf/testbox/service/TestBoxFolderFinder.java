package idea.plugin.cf.testbox.service;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.nio.file.Path;
import java.util.Optional;
import java.util.stream.Stream;

public class TestBoxFolderFinder {

    private static final String TEST_BOX_FOLDER_NAME = "TestBox";

    public Path findTestBoxFolderBaseInParents(Path file) {
        File folder = file.getParent().toFile();

        while (folder != null && !findTestBoxFolderInChildren(folder).isPresent()) {
            folder = folder.getParentFile();
        }

        if (folder == null) {
            throw new RuntimeException("TestBox base folder not found");
        }

        return folder.toPath();
    }

    @NotNull
    private Optional<File> findTestBoxFolderInChildren(File folder) {
        return Stream.of(getFiles(folder))
                .filter(this::isTestBoxFolder)
                .findAny();
    }

    private File[] getFiles(File currentFolder) {
        File[] files = currentFolder.listFiles();
        if (files == null) {
            throw new RuntimeException("Failed to read files of " + currentFolder.getPath());
        }
        return files;
    }

    private boolean isTestBoxFolder(File file) {
        return file.isDirectory() && file.getName().equalsIgnoreCase(TEST_BOX_FOLDER_NAME);
    }
}
