/**
 *
 * $Id$
 */
package org.framed.orm.geometry.validation;

import org.eclipse.emf.common.util.EList;

import org.framed.orm.geometry.Point;

/**
 * A sample validator interface for {@link org.framed.orm.geometry.RelativePoint}.
 * This doesn't really do anything, and it's not a real EMF artifact.
 * It was generated by the org.eclipse.emf.examples.generator.validator plug-in to illustrate how EMF's code generator can be extended.
 * This can be disabled with -vmargs -Dorg.eclipse.emf.examples.generator.validator=false.
 */
public interface RelativePointValidator {
	boolean validate();

	boolean validateReferencePoints(EList<Point> value);

	boolean validateDistances(EList<Point> value);
}
