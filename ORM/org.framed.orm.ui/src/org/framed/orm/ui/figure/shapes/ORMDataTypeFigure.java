package org.framed.orm.ui.figure.shapes;


import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.ToolbarLayout;
import org.eclipse.draw2d.XYLayout;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.widgets.Display;
import org.framed.orm.model.Segment;

/**
 * This figure is for {@link Shapes}s from type Datatype. The figure looks in the viewer/editor like this:
 *   ____________
 *  /____Name____\
 * | Attribute    |
 * |_List_________|
 * |Operation     |
 * \_List_______ /
 * 
 * The right side and bottom side of the figure have a border shadow.
 * The attribute list and operation list are added through child {@link Segment}s in the editparts.
 * @author Kay Bierzynski
 * */
public class ORMDataTypeFigure extends ORMShapeFigure {

  /**
   * The basic {@link ShadowOctagon}, which has at right and bottom side a border shadow and to
   * which all child figures(operation segment, name, attribute segment) are added.
   */
  private final ShadowOctagon rectangle;

  /**
   * The constructor of this class, where the constructor {@link ORMShapeFigure#ORMShapeFigure()}
   * is called, the basic {@link ShadowRectangle} is initialized and the child figures for the name(
   * {@link Label}) is added to the basic rectangle.
   * 
   * */
  public ORMDataTypeFigure() {

    super();

    rectangle = new ShadowOctagon();

    ToolbarLayout layout = new ToolbarLayout();
    layout.setSpacing(8); // set the initial heigth of the child figures
    setLayoutManager(new XYLayout());
    setBackgroundColor(ColorConstants.white);

    rectangle.setAntialias(SWT.ON);
    rectangle.setLayoutManager(layout);
    rectangle.setOpaque(true);
    setOpaque(true);
    
    // set name Label font
    Font font1 = new Font(Display.getCurrent(), "DataFigure", 8, SWT.ITALIC);
    getLabel().setFont(font1);
    
    // add name figure
    rectangle.add(getLabel());



    add(rectangle);

  }

  /**
   * A getter for the basic rectangle of this figure.
   * 
   * @return rectangle {@link ShadowOctagon}
   * */
  public ShadowOctagon getBasicRec() {
    return rectangle;
  }

  /** {@inheritDoc} */
  @Override
  protected void paintFigure(final Graphics graphics) {
    super.paintFigure(graphics);
    final Rectangle r = getBounds().getCopy();
    setConstraint(rectangle, new Rectangle(0, 0, r.width(), r.height()));
  }

  
  
  
}
