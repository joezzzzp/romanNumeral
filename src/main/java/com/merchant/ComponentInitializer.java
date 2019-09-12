package com.merchant;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author created by zzz at 2019/9/12 16:49
 **/

class ComponentInitializer {

    private static final String CLASS_FILE_EXTEND_NAME = ".class";

    private static final Logger logger = LogManager.getLogger(ComponentInitializer.class);

    private String rootPackage;

    ComponentInitializer(String rootPackage) {
        this.rootPackage = rootPackage;
    }

    /**
     * Find all class in path
     * @param classLoader classLoader
     * @param path package path
     * @param classList found classes
     */
    private void getAllClass(ClassLoader classLoader, String path, List<String> classList) {
        URL url = classLoader.getResource(path);
        if (url == null) {
            return;
        }
        if (url.getFile() == null) {
            return;
        }
        File file = new File(url.getFile());
        if (file.isFile()) {
            if (file.getName().endsWith(CLASS_FILE_EXTEND_NAME)) {
                String fullPath = file.getPath();
                String neededPath = fullPath.substring(fullPath.indexOf(path.replace('/', File.separatorChar)));
                String finalClassName =
                        neededPath.replace(CLASS_FILE_EXTEND_NAME, "").replace(File.separatorChar, '.');
                classList.add(finalClassName);
            }
        } else if (file.isDirectory() && file.list() != null) {
            File[] files = file.listFiles(pathname -> pathname.isDirectory() || pathname.getName().endsWith(CLASS_FILE_EXTEND_NAME));
            if (files != null) {
                for (File item : files) {
                    getAllClass(classLoader, path + "/" + item.getName(), classList);
                }
            }
        }
    }

    /**
     * Find needed interface from all interfaces a class implemented
     * @param allInterfaces all interfaces a class implemented
     * @param validInterfaces needed interfaces
     * @return needed interface or null
     */
    private Class<?> findValidInterface(Class<?>[] allInterfaces, Class<?>... validInterfaces) {
        for (Class<?> i : allInterfaces) {
            for (Class<?> vi : validInterfaces) {
                if (i == vi) {
                    return i;
                }
            }
        }
        return null;
    }

    /**
     * Load all Extractors and QuestionHandlers
     */
    void initComponents(Map<Class<? extends Extractor>, Extractor> extractors, List<QuestionHandler> handlers) {
        Class<?>[] validInterfaces = {Extractor.class, QuestionHandler.class};
        String packagePath = rootPackage.replace('.', '/');
        List<String> classFiles = new LinkedList<>();
        ClassLoader cl = MerchantGuideApp.class.getClassLoader();
        getAllClass(cl, packagePath, classFiles);
        for (String path : classFiles) {
            try {
                Class<?> clazz = cl.loadClass(path);
                Class<?> inter = findValidInterface(clazz.getInterfaces(), validInterfaces);
                if (inter == null) {
                    continue;
                }
                if (inter == Extractor.class) {
                    Extractor instance = (Extractor) clazz.newInstance();
                    extractors.put(instance.getClass(), instance);
                }
                if (inter == QuestionHandler.class) {
                    QuestionHandler instance = (QuestionHandler) clazz.newInstance();
                    handlers.add(instance);
                }
            } catch (ClassNotFoundException e) {
                logger.error("can't find class", e);
            } catch (IllegalAccessException e) {
                logger.error("illegal access", e);
            } catch (InstantiationException e) {
                logger.error("can't create instance", e);
            }
        }
    }
}
