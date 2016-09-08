package org.framed.orm.ui.editor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.gef.commands.CommandStackListener;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorActionBarContributor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.part.MultiPageEditorPart;
import org.framed.orm.featuremodel.FRaMEDConfiguration;
import org.framed.orm.featuremodel.FRaMEDFeature;
import org.framed.orm.featuremodel.FeaturemodelFactory;
import org.framed.orm.model.Model;
import org.framed.orm.model.Shape;
import org.framed.orm.model.OrmPackage;
import org.framed.orm.model.Type;
import org.osgi.framework.Bundle;

import de.ovgu.featureide.fm.core.FeatureModel;
import de.ovgu.featureide.fm.core.configuration.Configuration;
import de.ovgu.featureide.fm.core.configuration.Selection;
import de.ovgu.featureide.fm.core.io.UnsupportedModelException;
import de.ovgu.featureide.fm.core.io.xml.XmlFeatureModelReader;
import de.ovgu.featureide.fm.core.job.WorkMonitor;
import de.ovgu.featureide.fm.ui.editors.configuration.ConfigJobManager;


/**
 * 
 * This {@link MultiPageEditorPart} has as pages the behaviour {@link ORMGraphicalEditor}, the
 * data {@link ORMGraphicalEditor}, and the {@link FeatureModelConfigurationEditor}.
 * 
 * @author Kay Bierzynski
 * @author Lars Schütze
 * */
public class ORMMultiPageEditor extends MultiPageEditorPart implements ISelectionListener,
    CommandStackListener, IResourceChangeListener {

  
  private boolean autoSelectFeatures = true;
  
  private final ConfigJobManager configJobManager = new ConfigJobManager();

  /**
   * The behaviour {@link ORMGraphicalEditor}, which is manages through this editor.
   * */
  private ORMGraphicalEditor behaviourEditor;
  

  /**
   * The data {@link ORMGraphicalEditor}, which is manages through this editor.
   * */
  private ORMGraphicalEditor dataEditor;
  
  private FeatureModelConfigurationEditor featureModelConfigurationEditor;
  
  /**
   * The {@link EditorChangeNotifier} of this editor.
   * */
  private EditorChangeNotifier changeNotifier;
  /**
   * The input {@link Resource} of this editor, which contains the emf model.
   * */
  private Resource resource;
  /**
   * The name of the file, which contains the model resources. This variable is necessary for the
   * creation of custom title for this edito.
   */
  private String inputFilename;
  
  private Map<String, Set<Type>> configToPaletteMapping;

  /**
   * The constructor of this class, where {@link MultiPageEditorPart#MultiPageEditorPart()} is
   * called, inputFilename and changeNotifier are initialized and a listener to the model resources
   * is added.
   * */
  public ORMMultiPageEditor() {
    super();
    inputFilename = "";
    changeNotifier = null;
    ResourcesPlugin.getWorkspace().addResourceChangeListener(this);

  }


  /**
   * A setter for the {@link EditorChangeNotifier} of this editor.
   * */
  public void setEditorChangeNotifier(final EditorChangeNotifier changeNotifier) {
    this.changeNotifier = changeNotifier;
  }

  /**
   * This method creates a custom title for this editor out of resource file name, the genral model
   * element term and the specific model/{@link Type} element name of the model element which is the
   * content of the viewer of data/behaviour {@link ORMGraphicalEditor}.
   * */
  public void createCustomTitleForEditor(final Object model) {

    if (model instanceof Model || model instanceof Shape) {
      final String modelClassName = model.getClass().getSimpleName();
      setTitle(inputFilename + " " + modelClassName.substring(0, modelClassName.length() - 4));
      if (model instanceof Shape) {
        Shape shape = ((Shape) model);
        if (shape.getType() == Type.COMPARTMENT_TYPE) {
          setTitle(inputFilename + " " + "CompartmentType" + " " + shape.getName());
        }
        if (shape.getType() == Type.GROUP) {
          setTitle(inputFilename + " " + "Group" + " " + shape.getName());
        }
      }
    }
  }

  /**
   * A getter for name of input resource file.
   * 
   * @return inputFilename String
   * */
  public String getInputFileName() {
    return inputFilename;
  }

  /** {@inheritDoc} */
  @Override
  public void setTitleImage(final Image titleImage) {
    super.setTitleImage(titleImage);
  }

  /**
   * This method creates the beahvior {@link ORMGraphicalEditor} and adds the behaviour editor as a
   * page to this editor.
   */
  private void createBehaviorEditorPage() {
//    try {
      behaviourEditor = new ORMGraphicalEditor(this, resource, false, configToPaletteMapping);
//      int index = addPage(behaviourEditor, getEditorInput());
//      setPageText(index, "Behavior");
//    } catch (PartInitException e) {
//      ErrorDialog.openError(getSite().getShell(), "Error creating nested orm editor", null,
//          e.getStatus());
//    }
  }


  /**
   * This method creates the data {@link ORMGraphicalEditor} and adds the behaviour data as a page
   * to this editor.
   */
  private void createDataEditorPage() {
//    try {
      dataEditor = new ORMGraphicalEditor(this, resource, true, configToPaletteMapping);
//      int index = addPage(dataEditor, getEditorInput());
//      setPageText(index, "Data");
//    } catch (PartInitException e) {
//      ErrorDialog.openError(getSite().getShell(), "Error creating nested orm editor", null,
//          e.getStatus());
//    }
  }
  
 
  private void createFeatureModelConfigurationEditor() throws FileNotFoundException, UnsupportedModelException {
//    try {
      featureModelConfigurationEditor = new FeatureModelConfigurationEditor(this, resource);
      //setConfiguration();
      //featureModelConfigurationEditor.setOrmMultiPageEditor(this);
//      int index = addPage(featureModelConfigurationEditor, getEditorInput());
//      setPageText(index, "Configuration");
//    } catch (PartInitException e) {
//      ErrorDialog.openError(getSite().getShell(), "Error creating nested orm editor", null,
//          e.getStatus());
//    }
  }
  
 

  /** {@inheritDoc} In this method the title image of this editor is set as well. */
  @Override
  protected void createPages() {
    //Necessary to initialize it here, as the constructor of ORMMultiPageEditor is called after createPages()
    configToPaletteMapping = new HashMap<String, Set<Type>>();
    initializeConfigToPaletteMapping();
    
    //It is necessary to create the FeatureModelConfigurationEditor first, because the graphical editors
    //(behavior and data) rely on the standardFramedConfiguration which is created in this editor
    try {
      createFeatureModelConfigurationEditor();
    } catch (FileNotFoundException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (UnsupportedModelException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    
    createBehaviorEditorPage();
    createDataEditorPage();

    //In order to keep the order of the pages(Behavior, Data, Configuration) in the editor, 
    //we need to create the pages after calling the respective Editor constructors
    int index;
    try {
      index = addPage(behaviourEditor, getEditorInput());
      setPageText(index, "Behavior");
      index = addPage(dataEditor, getEditorInput());
      setPageText(index, "Data");
      index = addPage(featureModelConfigurationEditor, getEditorInput());    
      setPageText(index, "Configuration");
    } catch (PartInitException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }


    // set TitleImage of this ORMMultiPageEditor with background color of the viewer of the
    // behaivorEditor
    Image img =
        Activator.imageDescriptorFromPlugin(Activator.PLUGIN_ID, "icons/rosi-icon-smaller.gif")
            .createImage();
    img.setBackground(behaviourEditor.getGraphicalControl().getBackground());
    setTitleImage(img);
  }

  /**
   * {@inheritDoc} The implementation of the {@link ORMMultiPageEditor} tests if the editor input is
   * from type {@link IFileEditorInput}, adds a selection listener and initializes the input
   * resource.
   */
  @Override
  public void init(final IEditorSite site, final IEditorInput editorInput) throws PartInitException {
    if (!(editorInput instanceof IFileEditorInput)) {
      throw new PartInitException("Invalid Input: Must be IFileEditorInput");
    }
    super.init(site, editorInput);
    getSite().getWorkbenchWindow().getSelectionService().addSelectionListener(this);

    initializeResource(editorInput);
  }

  /**
   * This method initializes the model resource, which serves as input for this editor.
   * */
  private void initializeResource(final IEditorInput editorInput) {
    OrmPackage.eINSTANCE.eClass(); // This initializes the OrmPackage singleton implementation.
    ResourceSet resourceSet = new ResourceSetImpl();
    if (editorInput instanceof IFileEditorInput) {
      IFileEditorInput fileInput = (IFileEditorInput) editorInput;
      IFile file = fileInput.getFile();
      inputFilename = file.getName();
      resource = resourceSet.createResource(URI.createURI(file.getLocationURI().toString()));
      try {
        resource.load(null);
      } catch (IOException e) {
        // TODO do something smarter.
        e.printStackTrace();
        resource = null;
      }
    }
  }

  /**
   * {@inheritDoc} The implementation of the {@link ORMMultiPageEditor} saves the all editor, which
   * work on the same input resource and are registered through a page.
   */
  @Override
  public void doSave(final IProgressMonitor monitor) {
    for (int i = 0; i < getPageCount(); i++) {
      IEditorPart editorPart = getEditor(i);
      if (editorPart.isDirty()) {
        editorPart.doSave(monitor);
      }
    }
  }

  /**
   * Does at the moment nothing, because {@link ORMGraphicalEditor#doSaveAs()} isn't implemented.
   * */
  @Override
  public void doSaveAs() {

    if (behaviourEditor.equals(getActiveEditor())) {
      behaviourEditor.doSaveAs();
      setInput(behaviourEditor.getEditorInput());

    }

    if (dataEditor.equals(getActiveEditor())) {
      dataEditor.doSaveAs();
      setInput(dataEditor.getEditorInput());

    }
  }

  /**
   * {@inheritDoc} At the moment this method returns always true, because SaveAs functionalty isn't
   * implemented.
   */
  @Override
  public boolean isSaveAsAllowed() {
    return true;
  }

  /** This method enables select action for the active editor. */
  @Override
  public void selectionChanged(final IWorkbenchPart part, final ISelection selection) {
    if (this.equals(getSite().getPage().getActiveEditor())) {

      if (behaviourEditor.equals(getActiveEditor())) {
        behaviourEditor.selectionChanged(getActiveEditor(), selection);
      }
      if (dataEditor.equals(getActiveEditor()))
        dataEditor.selectionChanged(getActiveEditor(), selection);
    }
  }

  /**
   * {@inheritDoc} This method needs to be overriden so that when you switch between the editors the
   * actionbarcontributer takes the actionregistry/graphicalViewer/CommandStack from the active
   * editor.
   * */
  @Override
  protected void pageChange(final int newPageIndex) {
    super.pageChange(newPageIndex);
    IEditorPart activeEditor = getEditor(newPageIndex);
    //update the tree if the new page is the configuration editor
    if (activeEditor.getClass().getName().endsWith("FeatureModelConfigurationEditor")) {
      featureModelConfigurationEditor.updateTree();
    }
    else
    {
      //call the update editor type function in order to update the palette entry visibility as well
      ORMGraphicalEditor editor = (ORMGraphicalEditor) activeEditor;
      editor.pageChanged();
      IEditorActionBarContributor contributor = getEditorSite().getActionBarContributor();
      if (contributor != null && contributor instanceof ORMGraphicalEditorActionBarContributor) {
        ((ORMGraphicalEditorActionBarContributor) contributor).setActiveEditor(activeEditor);
      }
    }
    
  }

  /**
   * Called when the CommandStack state of the active editor has changed.
   * */
  @Override
  public void commandStackChanged(EventObject event) {
    if (this.equals(getSite().getPage().getActiveEditor())) {
      if (behaviourEditor.equals(getActiveEditor())) {
        behaviourEditor.commandStackChanged(event);
      }

      if (dataEditor.equals(getActiveEditor())) {
        dataEditor.commandStackChanged(event);
      }
    }

  }



  /**
   * Closes all project files on project close.
   */
  @Override
  public void resourceChanged(final IResourceChangeEvent event) {
    if (event.getType() == IResourceChangeEvent.PRE_CLOSE) {
      Display.getDefault().asyncExec(new Runnable() {

        public void run() {
          IWorkbenchPage[] pages = getSite().getWorkbenchWindow().getPages();
          for (int i = 0; i < pages.length; i++) {
            if (((FileEditorInput) behaviourEditor.getEditorInput()).getFile().getProject()
                .equals(event.getResource())) {
              IEditorPart editorPart = pages[i].findEditor(behaviourEditor.getEditorInput());
              pages[i].closeEditor(editorPart, true);
            }
          }
        }

      });
    }
  }

  /**
   * A getter for behaviour editor, which registered by this editor.
   * 
   * @reutrn {@link ORMGraphicalEditor}
   * */
  public ORMGraphicalEditor getBehaviorEditor() {
    return behaviourEditor;
  }

  /**
   * A getter for data editor, which registered by this editor.
   * 
   * @reutrn {@link ORMGraphicalEditor}
   * */
  public ORMGraphicalEditor getDataEditor() {
    return dataEditor;
  }

  /**
   * Sets the object obj as content in the behavior editor and data editor
   * 
   * @param obj The object which will be set as content
   */
  public void setContents(final Object obj) {
    /*
     * first, check which editor type we will get when setting the object as content. The palette
     * will be updated
     */

    getBehaviorEditor().getOwnViewer().setContents(obj);
    getDataEditor().getOwnViewer().setContents(obj);
  }
  

  public ConfigJobManager getConfigJobManager() {
    return configJobManager;
}
  
  public boolean isAutoSelectFeatures() {
    return autoSelectFeatures;
}
  
  
  private void initializeConfigToPaletteMapping() {

    //RML_Feature_Model
    fillConfigToPaletteMapping("RML_Feature_Model");
    
    //Role_Types
    fillConfigToPaletteMapping("Role_Types");
    
    //Role_Structure
    fillConfigToPaletteMapping("Role_Structure");
   
    //Role_Properties
    fillConfigToPaletteMapping("Role_Properties" );//Attributes, 
  //Drag&Drop, siehe unten bzgl. Attributes
    
    //Role_Behavior
    fillConfigToPaletteMapping("Role_Behavior", Type.FULFILLMENT);//Operations
    //Drag&Drop, siehe unten bzgl. Operations
    
    //Role_Inheritance
    fillConfigToPaletteMapping("Role_Inheritance", Type.INHERITANCE);// Inheritance (Step-In Ansicht)
    //Editpolicy: Erlaubt ziehen von Vererbungsrelation zwischen Rollentypen
    
    //Playable
    fillConfigToPaletteMapping("Playable"); //Keins
    
    //Players
    fillConfigToPaletteMapping("Players"); //Keins
    
    //Naturals
    fillConfigToPaletteMapping("Naturals"); //Keins
    
    //Roles
    fillConfigToPaletteMapping("Roles"); //(Step-in Ansicht) ist "Fills" (Fulfilment) (ggf. korrigieren) zu sehen
    //Editpolicy: Fill ziehen zwischen Rollentyp und Compartment, Impliziert containts_compartments
    
    //Compartments
    fillConfigToPaletteMapping("Compartments", Type.REFLEXIVE);//Keine
    
    //Dates
    fillConfigToPaletteMapping("Dates", Type.RELATIONSHIP_SHAPE_CHILD);// Keine
    
    //Dependent
    fillConfigToPaletteMapping("Dependent");//Keins
    
    //On_Compartments
    fillConfigToPaletteMapping("On_Compartments");//Keins

    //On_Relationships
    fillConfigToPaletteMapping("On_Relationships");//Keins
    
    //Role_Constraints
    fillConfigToPaletteMapping("Role_Constraints", Type.ACYCLIC);//Keins
    
    //Role_Implication
    fillConfigToPaletteMapping("Role_Implication", Type.FULFILLMENT);//(Step-in Ansicht) Role Implication
    
    //Role_Prohibition
    fillConfigToPaletteMapping("Role_Prohibition", Type.ACYCLIC); //(Step-in Ansicht)  Role Prohibition
    
    //Role_Equivalence
    fillConfigToPaletteMapping("Role_Equivalence", Type.REFLEXIVE);// (Step-in Ansicht) Role Equivalence 
    
    //Group_Constraints
    fillConfigToPaletteMapping("Group_Constraints", Type.FULFILLMENT); //Role Group
    
    //Occurence_Constraints
    fillConfigToPaletteMapping("Occurence_Constraints", Type.ROLE_EQUIVALENCE);//Keine
    //Label �ber Rollentypen
    
    //Relationships
    fillConfigToPaletteMapping("Relationships", Type.DATA_TYPE); //Relationship
    //Editpolicy: Erlaubt Zeichnen von Relationships zwischen Rollentypen
    
    //Relationship_Constraints
    fillConfigToPaletteMapping("Relationship_Constraints", Type.COMPARTMENT_TYPE);//Keins
    
    //Relationship_Cardinality
    fillConfigToPaletteMapping("Relationship_Cardinality");//Keins
    //Sind Labels an den Enden der Relationship (M�ssten auf leer gesetzt werden)
    
    //Intra_Relationship_Constraints
    fillConfigToPaletteMapping("Intra_Relationship_Constraints"); //reflexiv, irreflexiv, total, zyklisch, azyklisch
    
    //Parthood_Constraints
    fillConfigToPaletteMapping("Parthood_Constraints");//Keins
    
    //Inter_Relationship_Constraints
    fillConfigToPaletteMapping("Inter_Relationship_Constraints");// relationshipImplication, relationshipExclusion
    
    //Compartment_Types
    fillConfigToPaletteMapping("Compartment_Types");//Compartment, wenn nicht aktiviert, dann RoleModel (gibts noch nicht?)

    //Compartment_Structure
    fillConfigToPaletteMapping("Compartment_Structure");//Keins
    
    //Compartment_Properties
    fillConfigToPaletteMapping("Compartment_Properties", Type.GROUP);//Beide Ansichten: Attributes
    //Editpolicy�nderung: Drag&Drop von Attributen auf Compartment Modellelemente
    
    //Compartment_Behavior
    fillConfigToPaletteMapping("Compartment_Behavior", Type.GROUP);//Beide Ansichten: Operation
    ////Editpolicy�nderung: Drag&Drop von Operationen auf Compartment Modellelemente
    
    //Compartment_Inheritance
    fillConfigToPaletteMapping("Compartment_Inheritance"); //Inheritance
    //Editpolicy�nderung: Erlaubt Ziehen von Inheritance-Relation zwischen Datentypen
    
    //Participants
    fillConfigToPaletteMapping("Participants");//Keins
    
    //Contains_Compartments
    fillConfigToPaletteMapping("Contains_Compartments");//Keins (Ausgrauen, wird sp�ter hinzugef�gt)
    
    //Playable_by_Defining_Compartment
    fillConfigToPaletteMapping("Playable_by_Defining_Compartment", Type.GROUP);//Keins
    //Editpolicy�nderung (Wenn aktiviert, dann funktioniert Fulfilment von Compartment zu selbem Compartment)
    
    //Data_Types
    fillConfigToPaletteMapping("Data_Types", Type.DATA_TYPE); //DataType
    
    //Data_Type_Inheritance
    fillConfigToPaletteMapping("Data_Type_Inheritance", Type.DATA_TYPE);//Step-Out: Inheritance
    //Editpolicy�nderung: Erlaubt Ziehen von Inheritance-Relation zwischen Datentypen
  }
  
  private void fillConfigToPaletteMapping(String framedFeatureConfigName, Type... paletteEntryNames){
    Set<Type> templateSet = new HashSet<Type>();
    for (Type t : paletteEntryNames) {
      templateSet.add(t);
    }
    configToPaletteMapping.put(framedFeatureConfigName, templateSet);
  }
}
