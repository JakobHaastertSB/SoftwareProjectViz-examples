
package de.cau.cs.kieler.spviz.osgi.generate;

import de.cau.cs.kieler.spviz.osgi.model.OSGiProject;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;

public class OSGiModelSaveAndLoadUtility {

    /**
     * Takes a OSGi project and saves the data under modelSaveFilePath/fileName.
     *
     * @param fileName
     *            the name for the file
     * @param data
     *            OSGiProject to save
     * @throws IOException
     */
    public static void saveData(final String fileName, final OSGiProject data, String modelSaveFilePath)
        throws IOException {
        final Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
        final Map<String, Object> m = reg.getExtensionToFactoryMap();
        m.put("osgi", new XMIResourceFactoryImpl());

        final ResourceSet resSet = new ResourceSetImpl();
        modelSaveFilePath = modelSaveFilePath.replace("\\", "/");
        if (!modelSaveFilePath.endsWith("/")) {
            modelSaveFilePath += "/";
        }
        final Resource resource = resSet.createResource(URI.createURI("file:///" + modelSaveFilePath + fileName));
        resource.getContents().add(data);
        resource.save(Collections.EMPTY_MAP);
    }

}
