package com.merchant;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.net.URL;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * @author created by zzz at 2019/9/12 16:49
 **/

class ProcessorInitializer {

    private static final String CLASS_FILE_EXTEND_NAME = ".class";

    private static final Logger logger = LogManager.getLogger(ProcessorInitializer.class);

    private String rootPackage;

    private ClassLoader classLoader;

    ProcessorInitializer(ClassLoader classLoader, String rootPackage) {
        this.classLoader = classLoader;
        this.rootPackage = rootPackage;
    }

    /**
     * Find all class in path
     * @param path package path
     * @param classList found classes
     */
    private void recordClasses(String path, List<String> classList) {
        URL url = getUrl(path);
        if (null == url) {
            return;
        }
        File file = new File(url.getFile());
        if (file.isFile()) {
            loadFile(file, path, classList);
        } else if (file.isDirectory() && file.list() != null) {
            loadDirectory(file, path, classList);
        }
    }

    private URL getUrl(String path) {
        URL url = classLoader.getResource(path);
        if (url == null) {
            return null;
        }
        if (url.getFile() == null) {
            return null;
        }
        return url;
    }

    private void loadFile(File file, String path, List<String> classList) {
        if (file.getName().endsWith(CLASS_FILE_EXTEND_NAME)) {
            String fullPath = file.getPath();
            String neededPath = fullPath.substring(fullPath.indexOf(path.replace('/', File.separatorChar)));
            String finalClassName =
                    neededPath.replace(CLASS_FILE_EXTEND_NAME, "").replace(File.separatorChar, '.');
            classList.add(finalClassName);
        }
    }

    private void loadDirectory(File file, String path, List<String> classList) {
        File[] files = file.listFiles(pathname -> pathname.isDirectory() || pathname.getName().endsWith(CLASS_FILE_EXTEND_NAME));
        if (files != null) {
            Arrays.stream(files).forEach(item -> recordClasses(path + "/" + item.getName(), classList));
        }
    }

    /**
     * Load all Extractors and QuestionHandlers
     */
    void init(LineProcessor lineProcessor) {
        String packagePath = rootPackage.replace('.', '/');
        List<String> classFiles = new LinkedList<>();
        recordClasses(packagePath, classFiles);
        for (String path : classFiles) {
            try {
                Class<?> clazz = classLoader.loadClass(path);
                lineProcessor.addComponent(clazz);
            } catch (ClassNotFoundException e) {
                logger.error("can't find class", e);
            }
        }
        lineProcessor.init();
    }
}
