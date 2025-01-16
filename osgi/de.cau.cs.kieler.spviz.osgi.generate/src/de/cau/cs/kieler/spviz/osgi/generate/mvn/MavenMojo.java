// ******************************************************************************
//
// Copyright (c) 2024 by
// Scheidt & Bachmann System Technik GmbH, 24109 Melsdorf
//
// All rights reserved. The reproduction, distribution and utilisation of this document
// as well as the communication of its contents to others without explicit authorisation
// is prohibited. Offenders will be held liable for the payment of damages.
// All rights reserved in the event of the grant of a patent, utility model or design.
// (DIN ISO 16016:2007-12, Section 5.1)
//
// Alle Rechte vorbehalten. Weitergabe sowie Vervielfältigung dieses Dokuments,
// Verwertung und Mitteilung seines Inhalts sind verboten, soweit nicht ausdrücklich
// gestattet. Zuwiderhandlungen verpflichten zu Schadenersatz. Alle Rechte für den
// Fall der Patent-, Geschmacks- und Gebrauchsmustererteilung vorbehalten.
// (DIN ISO 16016:2007-12, Abschnitt 5.1)
//
// ******************************************************************************

package de.cau.cs.kieler.spviz.osgi.generate.mvn;

import de.cau.cs.kieler.spviz.osgi.generate.OSGiModelDataGenerator;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.File;
import java.lang.System.Logger;
import java.util.Optional;

/**
 *
 */
@Mojo(name = "generate-spviz-osgi")
public class MavenMojo extends AbstractMojo {
    static final Logger LOGGER = System.getLogger(MavenMojo.class.getName());
    @Parameter(name = "name", property = "SPVizName")
    private String name;

    @Parameter(name = "sourceDir", property = "SPVizSource")
    private String sourceDir;

    @Parameter(name = "targetDir", property = "SPVizTarget")
    private String targetDir;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        System.out.println(String.format("%s -> %s -> %s", name, sourceDir, targetDir));
        File src = new File(sourceDir);
        File trg = new File(targetDir);
        if (!src.exists()) {
            LOGGER.log(System.Logger.Level.ERROR, "Specify a valid source directory.");
            throw new MojoFailureException("Invalid source directory");
        }
        if (!trg.exists()) {
            LOGGER.log(System.Logger.Level.ERROR, "Specify a valid target directory.");
            throw new MojoFailureException("Invalid target directory");
        }
        if (name == null || name.isBlank()) {
            LOGGER.log(System.Logger.Level.ERROR, "Specify a valid name.");
            throw new MojoFailureException("Invalid name");
        }
        OSGiModelDataGenerator.generateData(sourceDir, name, Optional.of(targetDir));

        LOGGER.log(System.Logger.Level.INFO,
            "OSGi model generation has finished. The files can be found in " + targetDir);
    }

}
