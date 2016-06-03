/**
 *
 * $Id$
 */
package org.framed.orm.transformation.test.model.test.validation;

import org.framed.orm.model.Model;

/**
 * A sample validator interface for {@link org.framed.orm.transformation.test.model.test.TestCase}.
 * This doesn't really do anything, and it's not a real EMF artifact. It was generated by the
 * org.eclipse.emf.examples.generator.validator plug-in to illustrate how EMF's code generator can
 * be extended. This can be disabled with -vmargs
 * -Dorg.eclipse.emf.examples.generator.validator=false.
 */
public interface TestCaseValidator {
  boolean validate();

  boolean validateTitle(String value);

  boolean validateDescription(String value);

  boolean validateDesciption(String value);

  boolean validateFramedModel(Model value);

  boolean validateCromModel(crom_l1_composed.Model value);
}
