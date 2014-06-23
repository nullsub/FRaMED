package org.framed.orm.ui.action;

import org.eclipse.gef.DefaultEditDomain;
import org.eclipse.gef.Request;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.gef.ui.actions.SaveAction;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.ui.IWorkbenchPart;
import org.framed.orm.ui.editPart.ORMGroupingEditPart;
import org.framed.orm.ui.editPart.types.ORMCompartmentEditPart;
import org.framed.orm.ui.editor.ORMGraphicalEditor;


/**
 * @author Kay Bierzynski
 * */
public class StepInAction extends SelectionAction {

  public static final String STEP_IN_ID = "StepIn";
  public static final String STEP_IN_REQUEST = "StepIn";

  private Request request;
  private AbstractGraphicalEditPart editPart;

  /**
   * Create a new instance of the class.
   * 
   * @param part
   */
  public StepInAction(IWorkbenchPart part) {
    super(part);
    setId(STEP_IN_ID);
    setText("Step in");
    request = new Request(STEP_IN_REQUEST);
  }

  /**
   * Execute the commands that perform the {@link StepInAction#STEP_IN_REQUEST GO_DOWN_TREE_REQUEST}
   * .
   * 
   * It is assumed that this method is executed directly after
   * {@link StepInAction#calculateEnabled() calculateEnabled()}
   */
  @Override
  public void run() {

    ORMGraphicalEditor editorPart =
        (ORMGraphicalEditor) ((DefaultEditDomain) editPart.getViewer().getEditDomain())
            .getEditorPart();

    SaveAction save = new SaveAction(editorPart);
    execute(editPart.getCommand(request));
    save.run();
    editorPart.getOwnViewer().getSelectionManager().deselectAll();
  }

  /**
   * {@inheritDoc}
   * <p>
   * The action is enabled if the selected entity on the editor is {@link ORMCompartmentEditPart}
   * or {@link ORMGroupingEditPart} instance
   * </p>
   */
  @Override
  protected boolean calculateEnabled() {
    if (getSelectedObjects().isEmpty() || getSelectedObjects().size() > 1) {
      return false;
    } else if (getSelectedObjects().get(0) instanceof ORMCompartmentEditPart
        || getSelectedObjects().get(0) instanceof ORMGroupingEditPart) {
      editPart = (AbstractGraphicalEditPart) getSelectedObjects().get(0);
      return !editPart.equals(editPart.getViewer().getRootEditPart().getContents());
    }
    return false;
  }

}
