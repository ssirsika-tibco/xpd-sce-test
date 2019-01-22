/**
 * 
 */
package com.tibco.xpd.bom.db.api;

import java.util.Calendar;
import java.util.List;

import org.eclipse.datatools.modelbase.sql.tables.Table;
import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.uml2.uml.Model;

import com.tibco.xpd.bom.dbimport.XtendTransformDB2BOM;
import com.tibco.xpd.bom.resources.BOMResourcesPlugin;

/**
 * 
 * 
 * @author rsomayaj
 * @since 3.3 (25 Nov 2010)
 */
/**
 * 
 * 
 * @author rsomayaj
 * @since 3.3 (1 Dec 2010)
 */
public class DbToBomTransformUtil {

    /**
     * 
     */
    private static final String DATABASE = "database";

    /**
     * 
     */
    private static final String GENERATED_SOURCE = "generated_source";

    /**
     * 
     */
    private static final String GENERATED = "Generated";

    /**
     * Utility method to transform list of database table objects to Business
     * Object model.
     * 
     * @param ed
     * @param dbObjects
     *            - list of {@link Table}
     * @param modelObj
     * @return
     * @throws Exception
     */
    public static boolean transformDbTablesToBom(TransactionalEditingDomain ed,
            List dbObjects, EObject modelObj) throws Exception {
        return XtendTransformDB2BOM.getInstance().transform(ed,
                dbObjects,
                modelObj);
    }

    /**
     * Utility method to return whether the model is generated from a database
     * table or is a ResultSet for an SQL query that is generated from the
     * process.
     * 
     * @param model
     * @return
     *         <code>true<code> if there is an EAnnotation that has DBGenerated as its
     *         source <code>false<code> otherwise
     * 
     */
    public static boolean isModelDbGenerated(Model model) {
        List<EAnnotation> annotations = model.getEAnnotations();

        for (EAnnotation annotation : annotations) {
            if (GENERATED.equals(annotation.getSource())) {
                EMap<String, String> details = annotation.getDetails();
                String genSourceVal = details.get(GENERATED_SOURCE);
                if (DATABASE.equals(genSourceVal)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * @param model
     */
    public static void addDBGeneratedAnnotationToModel(Model model) {
        EAnnotation generatedAnnotation = model.createEAnnotation(GENERATED);

        EMap<String, String> details = generatedAnnotation.getDetails();
        details.put(GENERATED_SOURCE, DATABASE);
    }

    /**
     * @param model
     */
    public static void addAuthorTimestampAnnotationToModel(Model model,
            String user) {

        EAnnotation authorTimestampAnnotation =
                model
                        .createEAnnotation(BOMResourcesPlugin.ModelEannotationMetaSource);
        EMap<String, String> details = authorTimestampAnnotation.getDetails();

        details.put(BOMResourcesPlugin.ModelEannotationMetaSource_author, user);

        Calendar cal = Calendar.getInstance();
        long time = cal.getTimeInMillis();
        details.put(BOMResourcesPlugin.ModelEannotationMetaSource_datecreated,
                String.valueOf(time));

        details.put(BOMResourcesPlugin.ModelEannotationMetaSource_version,
                BOMResourcesPlugin.BOM_VERSION);

    }
}
