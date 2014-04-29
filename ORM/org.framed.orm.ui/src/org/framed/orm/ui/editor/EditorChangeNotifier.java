/**
 * 
 */
package org.framed.orm.ui.editor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartListener;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.gef.commands.CommandStackEvent;
import org.eclipse.gef.commands.CommandStackEventListener;

/**
 * @author paul
 *
 *	Builds the subject part of an observer pattern. Listens to events of the command stack and leads them to the registered observers.
 *	This class is needed, because Java forbids multi-inheritance...
 *	TODO: If there is already an observer without the need to subclass CommandStackEventListener use that one instead of this helper class
 *
 */
public class EditorChangeNotifier implements EditPartListener,CommandStackEventListener {

	private List<ORMGraphicalEditorPalette> observers = new ArrayList<ORMGraphicalEditorPalette>();
	private static EditorChangeNotifier inst = null;
	
	private EditorChangeNotifier(){
		
	}
	
	public static EditorChangeNotifier instance(){
		if(inst == null) inst = new EditorChangeNotifier();
		return inst;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.gef.commands.CommandStackEventListener#stackChanged(org.eclipse.gef.commands.CommandStackEvent)
	 */
	@Override
	public void stackChanged(CommandStackEvent event) {
		System.out.println("Stack changed"+event.getCommand());
	}
	
	public void register(ORMGraphicalEditorPalette observer){
		observers.add(observer);
	}
	
	public void unregister(ORMGraphicalEditorPalette observer){
		observers.remove(observer);
	}

	@Override
	public void childAdded(EditPart child, int index) {
		System.out.println("childAdded");
		Iterator<ORMGraphicalEditorPalette> it = observers.iterator();

		while(it.hasNext())
		{
			it.next().update();
		}		
	}

	@Override
	public void partActivated(EditPart editpart) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void partDeactivated(EditPart editpart) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removingChild(EditPart child, int index) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void selectedStateChanged(EditPart editpart) {
		// TODO Auto-generated method stub
		
	}

}
