/*
 * SPViz - Kieler Software Project Visualization for Projects
 * 
 * A part of Kieler
 * https://github.com/kieler
 * 
 * Copyright 2021 by
 * + Christian-Albrechts-University of Kiel
 *   + Department of Computer Science
 *     + Real-Time and Embedded Systems Group
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 */

package de.cau.cs.kieler.spviz.springdi.generate;

import java.io.File;
import java.lang.System.Logger;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import de.cau.cs.kieler.spviz.springdi.model.SpringDIProject;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

/**
 * This class is a command line tool that executes the generation of the Spring DI model files.
 * The project parameters for this generation are configured via command line parameters.
 *
 * @author nre
 */
@Command(name = "springdimodelgen")
public class ConfigAndExecuteCli implements Runnable {
	
	static final Logger LOGGER = System.getLogger(ConfigAndExecuteCli.class.getName());

	/**
	 * The names of all projects, that should be documented.
	 */
	@Option(names = {"-N", "--names"}, paramLabel = "PROJECT-NAMES",
			description = "The names of all projects that should be documented.")
	private Map<String, String> projectNames = new HashMap<>();

	/**
	 * The paths to the folders of the projects, that should be documented.
	 */
	@Option(names = {"-P", "--paths"}, paramLabel = "PROJECT-PATHS",
			description = "The paths to the folders of the projects that should be documented.")
	private Map<String, File> projectPaths = new HashMap<>();

	/**
	 * The output path where the model is saved.
	 */
	@Option(names = {"-S", "-O", "--output"}, required = true, paramLabel = "OUTPUT",
			description = "The output path where the model is saved.")
	private String modelSaveFilePath;
	
	@Option(names = {"-h", "--help"}, usageHelp = true, description = "displays this help message.")
    protected boolean help;

	/**
	 * Executes the reading of project files and the creation of the documentation.
	 */
	public static void main(String[] args) {
		CommandLine cl = new CommandLine(new ConfigAndExecuteCli());
		System.exit(cl.execute(args));
	}
	
	@Override
	public void run() {
		if (projectPaths != null) {
			for (final Entry<String, File> projectPathEntry : projectPaths.entrySet()) {
				final String projectKey = projectPathEntry.getKey();

				if (!projectNames.containsKey(projectKey) || projectNames.get(projectKey) == null) {
					projectNames.put(projectKey, projectKey);
				}
				
				LOGGER.log(System.Logger.Level.DEBUG, "ProjectPath: " + projectPathEntry.getValue());
				LOGGER.log(System.Logger.Level.DEBUG, "ProjectName: " + projectNames.get(projectKey));
			}

			// Modelfile path
			Optional<String> optionalModelSaveFilePath = Optional.empty();
			if (!modelSaveFilePath.equals("")) {
				optionalModelSaveFilePath = Optional.of(modelSaveFilePath);
				LOGGER.log(System.Logger.Level.DEBUG, "Model Save Directory: " + modelSaveFilePath);
			}

			// Read the project Data
			final Map<String, SpringDIProject> projectMap = new HashMap<String, SpringDIProject>();
			for (final Entry<String, File> projectPathEntry : projectPaths.entrySet()) {
				LOGGER.log(System.Logger.Level.INFO, "Reading Project Data for " + projectNames.get(projectPathEntry.getKey()));
				final String projectKey = projectPathEntry.getKey();
				projectMap.put(projectKey, SpringDIModelDataGenerator.generateData(projectPathEntry.getValue().toString(),
						projectNames.get(projectKey), optionalModelSaveFilePath));
			}

			LOGGER.log(System.Logger.Level.INFO, "SpringDI model generation has finished. The files can be found in " + modelSaveFilePath);
		}
	}
	
}
