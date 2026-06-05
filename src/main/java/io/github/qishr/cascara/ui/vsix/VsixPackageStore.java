package io.github.qishr.cascara.ui.vsix;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import io.github.qishr.cascara.common.content.type.ContentTypeException;
import io.github.qishr.cascara.common.content.type.ContentTypeRegistry;
import io.github.qishr.cascara.common.content.type.ContentTypeStore;
import io.github.qishr.cascara.common.diagnostic.NoOpReporter;
import io.github.qishr.cascara.common.diagnostic.Reporter;
import io.github.qishr.cascara.common.io.filewatcher.FileWatcher;
import io.github.qishr.cascara.common.io.filewatcher.FileChangeHandler;
import io.github.qishr.cascara.common.io.filewatcher.FileChangeType;
import io.github.qishr.cascara.common.util.ContentType;
import io.github.qishr.cascara.common.util.Properties;
import io.github.qishr.cascara.lang.yaml.processor.YamlSerializer;
import io.github.qishr.cascara.ui.menu.ObservableMenuItem;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class VsixPackageStore implements AutoCloseable {
    private static final Path cascaraDir = Paths.get(System.getProperty("user.home")).resolve(".cascara");
    private static final Path packageDir = cascaraDir.resolve("packages");

    private final ObservableList<VsixPackageInfo> packagesList = FXCollections.observableArrayList();
    private FileWatcher fileWatcher;
    private static VsixPackageStore instance;

    private Reporter reporter = new NoOpReporter();

    private VsixPackageStore() {
        init();
    }

    @Override
    public void close() {
        if (fileWatcher != null) {
            fileWatcher.clear();
        }
    }

    private void init() {
        enumeratePackages();
        // TODO: File watcher
        try {
			fileWatcher = new FileWatcher();
            fileWatcher.watchDirectory(packageDir, new FileChangeHandler() {

				@Override
				public void handle(FileChangeType type, Path path) {
                    if (type == FileChangeType.CREATED) {
                        VsixPackageInfo info = readPackageInfo(path);
                        packagesList.add(info);
                    }
                    if (type == FileChangeType.DELETED) {
                        for (VsixPackageInfo info : packagesList) {
                            if (info.getPath().equals(path)) {
                                packagesList.remove(info);
                                return;
                            }
                        }
                    }
				}
            });
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    public static VsixPackageStore instance() {
        if (instance == null) {
            instance = new VsixPackageStore();
        }
        return instance;
    }

    public void setReporter(Reporter reporter) {
        if (reporter == null) {
            this.reporter = new NoOpReporter();
        } else {
            this.reporter = reporter;
        }
    }

    public ObservableList<VsixPackageInfo> getPackages() {
        // enumeratePackages();
        return packagesList;
    }

    private void enumeratePackages() {
        List<VsixPackageInfo> list = new ArrayList<>();
        if (Files.isDirectory(packageDir)) {
            try {
                Files.list(packageDir).forEach(path -> {
                    VsixPackageInfo info = readPackageInfo(path);
                    if (info != null) {
                        if (info.getPath() == null) {
                            System.out.println("Debug");
                        }
                        list.add(info);
                    }
                });
                packagesList.retainAll(list);
                packagesList.setAll(list);
            } catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
    }

    private VsixPackageInfo readPackageInfo(Path path) {
        try {
            VsixPackage pkg = VsixPackage.load(path);
            Properties manifest = pkg.getManifest();

            String name = manifest.getString("name");
            String displayName = manifest.getString("displayName");

            if (name == null || displayName == null) {
                return null;
            }
            VsixPackageInfo info = new VsixPackageInfo(path, name, displayName);
            return info;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

    public void install(Path packagePath) throws IOException {
        Path fileName = packagePath.getFileName();
        Files.copy(packagePath, packageDir.resolve(fileName));

    }
}
