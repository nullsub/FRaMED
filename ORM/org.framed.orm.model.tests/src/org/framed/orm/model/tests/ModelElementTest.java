/**
 */
package org.framed.orm.model.tests;

import junit.textui.TestRunner;
import org.framed.orm.model.ModelElement;
import org.framed.orm.model.OrmFactory;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>Model Element</b></em>'.
 * <!-- end-user-doc -->
 * @generated
 */
public class ModelElementTest extends NamedElementTest {

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(ModelElementTest.class);
	}

	/**
	 * Constructs a new Model Element test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ModelElementTest(String name) {
		super(name);
	}

	/**
	 * Returns the fixture for this Model Element test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected ModelElement getFixture() {
		return (ModelElement) fixture;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#setUp()
	 * @generated
	 */
	@Override
	protected void setUp() throws Exception {
		setFixture(OrmFactory.eINSTANCE.createModelElement());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#tearDown()
	 * @generated
	 */
	@Override
	protected void tearDown() throws Exception {
		setFixture(null);
	}

} //ModelElementTest
