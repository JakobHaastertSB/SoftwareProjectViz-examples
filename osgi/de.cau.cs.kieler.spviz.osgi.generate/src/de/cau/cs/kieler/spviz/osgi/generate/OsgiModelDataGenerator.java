
package de.cau.cs.kieler.spviz.osgi.generate;

import de.cau.cs.kieler.spviz.osgi.model.OSGiProject;

import java.io.File;
import java.io.IOException;
import java.lang.System.Logger;
import java.util.Optional;

/**
 * The model generator for OSGi projects.
 */
public final class OSGiModelDataGenerator {

    static final Logger LOGGER = System.getLogger(OSGiModelDataGenerator.class.getName());

    /**
     * Generates OSGi project data from a given project path. The generated Model
     * will be returned and also saved in a file.
     *
     * @param projectFilePath
     *            The path to the project root folder
     * @param projectName
     *            Descriptive name of the project
     * @return The generated OSGi project data.
     */
    public static OSGiProject generateData(final String projectFilePath, final String projectName,
        Optional<String> modelSaveFilePath) {

        final ReadProjectFiles reader = new ReadProjectFiles();
        LOGGER.log(System.Logger.Level.INFO, "Generating data for " + projectName);
        final OSGiProject project = reader.generateData(new File(projectFilePath), projectName);

        if (modelSaveFilePath.isPresent()) {

            LOGGER.log(System.Logger.Level.INFO, "Saving data for " + projectName);
            final String fileName = projectName + ".osgi";
            try {
                OSGiModelSaveAndLoadUtility.saveData(fileName, project, modelSaveFilePath.get());
            } catch (final IOException e) {
                LOGGER.log(System.Logger.Level.ERROR, "There was a Problem while saving.", e);
                e.printStackTrace();
            }
        }
        LOGGER.log(System.Logger.Level.INFO, "Finished");

        return project;
    }

}
