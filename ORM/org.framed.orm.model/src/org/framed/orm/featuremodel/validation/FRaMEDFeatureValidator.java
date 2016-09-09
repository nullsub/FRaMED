/**
 *
 * $Id$
 */
package org.framed.orm.featuremodel.validation;

import org.framed.orm.featuremodel.FeatureName;


/**
 * A sample validator interface for {@link org.framed.orm.featuremodel.FRaMEDFeature}.
 * This doesn't really do anything, and it's not a real EMF artifact.
 * It was generated by the org.eclipse.emf.examples.generator.validator plug-in to illustrate how EMF's code generator can be extended.
 * This can be disabled with -vmargs -Dorg.eclipse.emf.examples.generator.validator=false.
 */
public interface FRaMEDFeatureValidator {
  boolean validate();

  boolean validateName(FeatureName value);

  boolean validateName(String value);

  boolean validateManuallySelected(boolean value);
}
