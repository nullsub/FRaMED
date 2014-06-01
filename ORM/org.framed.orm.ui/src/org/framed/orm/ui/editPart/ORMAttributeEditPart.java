package org.framed.orm.ui.editPart;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.text.TextFlow;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.gef.DragTracker;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.jface.viewers.TextCellEditor;
import org.framed.orm.model.Attribute;
import org.framed.orm.ui.editPart.types.ORMTypeEditPart;
import org.framed.orm.ui.editPolicy.ORMAttributeComponentEditPolicy;
import org.framed.orm.ui.editPolicy.ORMAttributeDirectEditPolicy;
import org.framed.orm.ui.editPolicy.ORMDragEditPartsTracker;
import org.framed.orm.ui.editor.ORMNodeCellEditorLocator;
import org.framed.orm.ui.editor.ORMNodeDirectEditManager;

/**
 * @author Kay Bierzynski
 * */
public class ORMAttributeEditPart extends AbstractGraphicalEditPart {

  private final ORMAttributeAdapter adapter;
  private ORMTypeEditPart parentEditPart;

  public ORMAttributeEditPart() {
    super();
    adapter = new ORMAttributeAdapter();
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.eclipse.gef.editparts.AbstractGraphicalEditPart#getDragTracker(org.eclipse.gef.Request)
   */
  @Override
  public DragTracker getDragTracker(Request request) {
    return new ORMDragEditPartsTracker(this);
  }

  public void setParentEditPart(ORMTypeEditPart pEditPart) {
    parentEditPart = pEditPart;
  }

  @Override
  protected IFigure createFigure() {
    ORMLabelFigure label = new ORMLabelFigure(this);
    return label;
  }

  @Override
  protected void createEditPolicies() {
    installEditPolicy(EditPolicy.DIRECT_EDIT_ROLE, new ORMAttributeDirectEditPolicy());
    installEditPolicy(EditPolicy.COMPONENT_ROLE, new ORMAttributeComponentEditPolicy());

  }

  @Override
  public void performRequest(Request req) {

    if (req.getType() == RequestConstants.REQ_DIRECT_EDIT) {
      performDirectEditing();
    }
  }

  private void performDirectEditing() {
    TextFlow textFlow = ((ORMLabelFigure) getFigure()).getTextFlow();
    ORMNodeDirectEditManager manager =
        new ORMNodeDirectEditManager(this, TextCellEditor.class, new ORMNodeCellEditorLocator(
            textFlow), textFlow);
    manager.show(); // refresh view
  }


  @Override
  protected void refreshVisuals() {
    final ORMLabelFigure figure = (ORMLabelFigure) getFigure();
    final Attribute model = (Attribute) getModel();

    // System.out.println("Attribute: parent: "+parentEditPart.getConstraints()+" :: "+model.getName());
    figure.setText((new ORMLabelFigure(this)).shortenLabel(model.getName(), figure.getTextFlow(),
        parentEditPart.getConstraints()));
    figure.setToolTip(new Label(model.getName()));
  }

  @Override
  public void activate() {
    if (!isActive()) {
      ((Attribute) getModel()).eAdapters().add(adapter);
    }
    super.activate();
  }

  @Override
  public void deactivate() {
    if (isActive()) {
      ((Attribute) getModel()).eAdapters().remove(adapter);
    }

    super.deactivate();
  }

  public class ORMAttributeAdapter implements Adapter {

    // Adapter interface
    @Override
    public void notifyChanged(Notification notification) {
      refreshVisuals();

    }

    @Override
    public Notifier getTarget() {
      return (Attribute) getModel();
    }

    @Override
    public void setTarget(Notifier newTarget) {
      // Do nothing.
    }

    @Override
    public boolean isAdapterForType(Object type) {
      return type.getClass().equals(Attribute.class);
    }
  }
}
