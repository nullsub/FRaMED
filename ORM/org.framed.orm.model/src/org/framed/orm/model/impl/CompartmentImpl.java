/**
 */
package org.framed.orm.model.impl;

import org.eclipse.emf.ecore.EClass;
import org.framed.orm.model.Compartment;
import org.framed.orm.model.CompartmentDiagram;
import org.framed.orm.model.OrmPackage;
import org.framed.orm.model.Rolemodel;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Compartment</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.framed.orm.model.impl.CompartmentImpl#getCompartmentDiagram <em>Compartment Diagram</em>}</li>
 *   <li>{@link org.framed.orm.model.impl.CompartmentImpl#getRolemodel <em>Rolemodel</em>}</li>
 *   <li>{@link org.framed.orm.model.impl.CompartmentImpl#getParentRolemodel <em>Parent Rolemodel</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class CompartmentImpl extends TypeImpl implements Compartment {
  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected CompartmentImpl() {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass() {
    return OrmPackage.Literals.COMPARTMENT;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public CompartmentDiagram getCompartmentDiagram() {
    return (CompartmentDiagram) eGet(OrmPackage.Literals.COMPARTMENT__COMPARTMENT_DIAGRAM, true);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setCompartmentDiagram(CompartmentDiagram newCompartmentDiagram) {
    eSet(OrmPackage.Literals.COMPARTMENT__COMPARTMENT_DIAGRAM, newCompartmentDiagram);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Rolemodel getRolemodel() {
    return (Rolemodel) eGet(OrmPackage.Literals.COMPARTMENT__ROLEMODEL, true);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setRolemodel(Rolemodel newRolemodel) {
    eSet(OrmPackage.Literals.COMPARTMENT__ROLEMODEL, newRolemodel);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Rolemodel getParentRolemodel() {
    return (Rolemodel) eGet(OrmPackage.Literals.COMPARTMENT__PARENT_ROLEMODEL, true);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setParentRolemodel(Rolemodel newParentRolemodel) {
    eSet(OrmPackage.Literals.COMPARTMENT__PARENT_ROLEMODEL, newParentRolemodel);
  }

} //CompartmentImpl
