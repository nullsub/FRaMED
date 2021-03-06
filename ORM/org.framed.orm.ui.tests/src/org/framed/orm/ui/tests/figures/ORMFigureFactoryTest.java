package org.framed.orm.ui.tests.figures;

import static org.junit.Assert.*;

import org.eclipse.gef.EditPart;
import org.framed.orm.model.OrmFactory;
import org.framed.orm.model.Shape;
import org.framed.orm.model.Type;
import org.framed.orm.ui.editPart.ORMModelEditPart;
import org.framed.orm.ui.editPart.shape.ORMCompartmentEditPart;
import org.framed.orm.ui.editPart.shape.ORMSegmentEditPart;
import org.framed.orm.ui.editPart.shape.ORMShapeWithSegmentEditPart;
import org.framed.orm.ui.editPart.shape.ORMShapeWithoutSegmentEditPart;
import org.framed.orm.ui.figure.shapes.ORMCompartmentV1Figure;
import org.framed.orm.ui.figure.shapes.ORMCompartmentV2Figure;
import org.framed.orm.ui.figure.ORMFigureFactory;
import org.framed.orm.ui.figure.shapes.ORMGroupV1Figure;
import org.framed.orm.ui.figure.shapes.ORMGroupV2Figure;
import org.framed.orm.ui.figure.shapes.ORMNaturalTypeFigure;
import org.framed.orm.ui.figure.shapes.ORMRoleGroupFigure;
import org.framed.orm.ui.figure.shapes.ORMRoleTypeFigure;
import org.framed.orm.ui.figure.shapes.ORMRootModelFigure;
import org.framed.orm.ui.figure.shapes.PartFigure;
import org.junit.Test;

public class ORMFigureFactoryTest {

  @Test
  public void testCreateFigureOfRoleType() {
    EditPart part = new ORMShapeWithSegmentEditPart();
    Shape shape = OrmFactory.eINSTANCE.createShape();  
    shape.setType(Type.ROLE_TYPE);
    part.setModel(shape);
    
    assertTrue("Return ORMRoleTypeFigure", ORMFigureFactory.createFigure(part) instanceof ORMRoleTypeFigure);
  }
  
  @Test
  public void testCreateFigureOfRoleGroup() {
    EditPart part = new ORMShapeWithoutSegmentEditPart();
    Shape shape = OrmFactory.eINSTANCE.createShape();  
    shape.setType(Type.ROLE_GROUP);
    part.setModel(shape);
    
    assertTrue("Return ORMRoleGroupFigure", ORMFigureFactory.createFigure(part) instanceof ORMRoleGroupFigure);
  }
  
  @Test
  public void testCreateFigureOfDataType() {
    EditPart part = new ORMShapeWithSegmentEditPart();
    Shape shape = OrmFactory.eINSTANCE.createShape();  
    shape.setType(Type.DATA_TYPE);
    part.setModel(shape);
    
    fail("Not implemented yet.");
  }
  
  @Test
  public void testCreateFigureOfNatrualType() {
    EditPart part = new ORMShapeWithSegmentEditPart();
    Shape shape = OrmFactory.eINSTANCE.createShape();  
    shape.setType(Type.NATURAL_TYPE);
    part.setModel(shape);
    
    assertTrue("Return ORMNaturalTypeFigure", ORMFigureFactory.createFigure(part) instanceof ORMNaturalTypeFigure);
  }
  
  @Test
  public void testCreateFigureOfGroup() {
    EditPart part = new ORMShapeWithoutSegmentEditPart();
    Shape shape = OrmFactory.eINSTANCE.createShape();  
    shape.setType(Type.GROUP);
    part.setModel(shape);
    
    EditPart parentpart = new ORMModelEditPart();
    parentpart.setModel(OrmFactory.eINSTANCE.createModel());
    part.setParent(parentpart);
    
    assertTrue("Return ORMGroupingV1Figure", ORMFigureFactory.createFigure(part) instanceof ORMGroupV1Figure);
    
    parentpart.setModel(OrmFactory.eINSTANCE.createShape());
    
    assertTrue("Return ORMGroupingV2Figure", ORMFigureFactory.createFigure(part) instanceof ORMGroupV2Figure);
  }
  
  @Test
  public void testCreateFigureOfCompartmentType() {
    EditPart part = new ORMCompartmentEditPart();
    Shape shape = OrmFactory.eINSTANCE.createShape();  
    shape.setType(Type.COMPARTMENT_TYPE);
    part.setModel(shape);
    
    EditPart parentpart = new ORMModelEditPart();
    parentpart.setModel(OrmFactory.eINSTANCE.createModel());
    part.setParent(parentpart);
    
    assertTrue("Return ORMCompartmentV1Figure", ORMFigureFactory.createFigure(part) instanceof ORMCompartmentV1Figure);
    
    parentpart.setModel(OrmFactory.eINSTANCE.createShape());
    
    assertTrue("Return ORMCompartmentV2Figure", ORMFigureFactory.createFigure(part) instanceof ORMCompartmentV2Figure);
  }
  
  @Test
  public void testCreateFigureOfModel() {
    EditPart part = new ORMModelEditPart();
    part.setModel(OrmFactory.eINSTANCE.createModel());
    
    assertTrue("Return ORMModelFigure", ORMFigureFactory.createFigure(part) instanceof ORMRootModelFigure);
  }
  
  @Test
  public void testCreateFigureOfSegment() {
    EditPart part = new ORMSegmentEditPart();
    part.setModel(OrmFactory.eINSTANCE.createSegment());
    
    assertTrue("Return PartFigure()", ORMFigureFactory.createFigure(part) instanceof PartFigure);
  }

}
