package hr.fer.ooup.lab3;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;


public class TextEditor extends JFrame implements CursorObserver, TextObserver{
	private static final long serialVersionUID = 2608392480536950977L;
	private JPanel textPanel;
	private JPanel cursorPanel;
	private SelectionPanel selectionPanel;
	private List<RowComponent> rowComponents;
	private ClipboardStack clipboardStack;
	private UndoManager undoManager;
	private JComponent cursorComp;
	private JLayeredPane layeredPane;
	private JMenuBar menuBar;
	private TextEditorModel model = new TextEditorModel("");
	
	public TextEditor(TextEditorModel model) {
		this.model = model;
		clipboardStack = new ClipboardStack();
		undoManager = UndoManager.getInstance();
		rowComponents = new ArrayList<RowComponent>();
		
		this.setSize(500,500);
		this.setLayout(new BorderLayout());
		
		textPanel = new JPanel();
		textPanel.setLayout(new BoxLayout(textPanel,BoxLayout.Y_AXIS));
		textPanel.setBounds(0, 0, 500, 500);
		textPanel.setOpaque(false);
		
		cursorPanel = new JPanel();
		cursorPanel.setLayout(new BoxLayout(cursorPanel,BoxLayout.Y_AXIS));
		cursorPanel.setBounds(0,0, 500, 500);
		cursorPanel.setOpaque(false);
		
		selectionPanel = new SelectionPanel(model.getSelectionRange(),rowComponents);
		selectionPanel.setLayout(new BoxLayout(selectionPanel, BoxLayout.Y_AXIS));
		selectionPanel.setBounds(0,0, 500, 500);
		selectionPanel.setOpaque(false);
		
		layeredPane = new JLayeredPane();
		layeredPane.setBounds(0, 0, 500, 500);
		layeredPane.add(textPanel);
		layeredPane.add(cursorPanel);
		layeredPane.add(selectionPanel);
		
		refreshCursor();
		
		menuBar = createMenu();
		
		JPanel statusPanel = new JPanel();
		statusPanel.setBorder(new BevelBorder(BevelBorder.LOWERED));
		statusPanel.setPreferredSize(new Dimension(this.getWidth(), 16));
		statusPanel.setLayout(new BoxLayout(statusPanel, BoxLayout.X_AXIS));
		
		StatusLabel statusLabel = new StatusLabel(model);
		statusLabel.setHorizontalAlignment(SwingConstants.LEFT);
		statusPanel.add(statusLabel);
				
		model.attachCursorObserver(statusLabel);
		model.attachTextObserver(statusLabel);
				
		this.add(statusPanel, BorderLayout.SOUTH);
		this.add(menuBar, BorderLayout.NORTH);
		this.add(layeredPane, BorderLayout.CENTER);
		
		initializeKeyListeners();
				
		model.attachCursorObserver(this);
		model.attachTextObserver(this);
		
		refreshRows();
		
		undoManager.notifyObservers();
		clipboardStack.notifyObservers();
		model.notifyCursorChange();
		model.notifyTextChange();
		model.notifySelectionChange();
	}

	private void initializeKeyListeners() {
		this.setFocusTraversalKeysEnabled(false);
		
		this.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {}
			
			@Override
			public void keyReleased(KeyEvent e) {}
			
			@Override
			public void keyPressed(KeyEvent e) {
				LocationRange range = model.getSelectionRange();
				
                if ((e.getKeyCode() == KeyEvent.VK_C) && ((e.getModifiersEx() & KeyEvent.CTRL_DOWN_MASK) != 0)) {
                    if(range != null) {
                    	clipboardStack.putTextToStack(model.getTextFromSelection(range));
                    	
                    }
                }else if(e.getKeyCode() == KeyEvent.VK_TAB){
                	undoManager.push(new InsertTextAction(model, "    "));
                	
                }else if ((e.getKeyCode() == KeyEvent.VK_Z) && ((e.getModifiersEx() & KeyEvent.CTRL_DOWN_MASK) != 0)) {
                    undoManager.undo();
                }else if ((e.getKeyCode() == KeyEvent.VK_Y) && ((e.getModifiersEx() & KeyEvent.CTRL_DOWN_MASK) != 0)) {
                    undoManager.redo();
                }else if ((e.getKeyCode() == KeyEvent.VK_X) && ((e.getModifiersEx() & KeyEvent.CTRL_DOWN_MASK) != 0)) {
                    if(range != null) {
                    	clipboardStack.putTextToStack(model.getTextFromSelection(range));
                		undoManager.push(new DeleteRangeAction(model));
                		model.resetSelectionRange();
                    }
                }else if ((e.getKeyCode() == KeyEvent.VK_V) && ((e.getModifiersEx() & KeyEvent.CTRL_DOWN_MASK) != 0)) {
                    if(range != null) {
                    	String text = clipboardStack.readTextFromTopOfTheStack();
                		undoManager.push(new InsertTextAction(model, text));
                    }
                }else if ((e.getKeyCode() == KeyEvent.VK_V) && ((e.getModifiersEx() & KeyEvent.CTRL_DOWN_MASK & KeyEvent.SHIFT_DOWN_MASK) != 0)) {
                    if(range != null) {
                    	String text = clipboardStack.popTextFromStack();
                    	model.insert(text);
                    }
                }else if ((e.getKeyCode() == KeyEvent.VK_LEFT) && ((e.getModifiersEx() & KeyEvent.SHIFT_DOWN_MASK) != 0)) {
                    Location end = range.getEnd();
                    if(end.getX() > 0) {
                    	end.setX(end.getX() - 1);
                    }
                    range.setEnd(end);
                    model.setSelectionRange(range);
                    model.moveCursorLeft();
                    paintRange(range);
                }else if ((e.getKeyCode() == KeyEvent.VK_RIGHT) && ((e.getModifiersEx() & KeyEvent.SHIFT_DOWN_MASK) != 0)) {
                    Location end = range.getEnd();
                    end.setX(end.getX() + 1);
                    range.setEnd(end);
                    model.setSelectionRange(range);
                    model.moveCursorRight();
                    paintRange(range);
                }else if ((e.getKeyCode() == KeyEvent.VK_UP) && ((e.getModifiersEx() & KeyEvent.SHIFT_DOWN_MASK) != 0)) {
                    Location end = range.getEnd();
                    if(end.getY() > 0) {
                    	end.setY(end.getY() - 1);
                    }
                    range.setEnd(end);
                    model.setSelectionRange(range);
                    model.moveCursorUp();
                    paintRange(range);
                }else if ((e.getKeyCode() == KeyEvent.VK_DOWN) && ((e.getModifiersEx() & KeyEvent.SHIFT_DOWN_MASK) != 0)) {
                    Location end = range.getEnd();
                    end.setY(end.getY() + 1);
                    range.setEnd(end);
                    model.setSelectionRange(range);
                    model.moveCursorDown();
                    paintRange(range);
                }else if(e.getKeyCode() == KeyEvent.VK_LEFT){
                	model.moveCursorLeft();
                	model.resetSelectionRange();
                	paintRange(range);
                }else if(e.getKeyCode() == KeyEvent.VK_BACK_SPACE){
                	if(range.getStart().equals(range.getEnd())) {
                		undoManager.push(new DeleteBeforeAction(model));
                	}else {
                		undoManager.push(new DeleteRangeAction(model));
                	}
                	
                }else if(e.getKeyCode() == KeyEvent.VK_DELETE){
                	if(range.getStart().equals(range.getEnd())) {
                		undoManager.push(new DeleteAfterAction(model));	
                	}else {
                		undoManager.push(new DeleteRangeAction(model));
                	}                	
                }else if(e.getKeyCode() == KeyEvent.VK_RIGHT){
                	model.moveCursorRight();
                	model.resetSelectionRange();
                	paintRange(range);
                }else if(e.getKeyCode() == KeyEvent.VK_UP){
                	model.moveCursorUp();
                	model.resetSelectionRange();
                	paintRange(range);
                }else if(e.getKeyCode() == KeyEvent.VK_DOWN){
                	model.moveCursorDown();
                	model.resetSelectionRange();
                	paintRange(range);
                }else {
                	if(!e.isActionKey() && !e.isAltDown() && !e.isAltGraphDown() && !e.isControlDown()) {
                		char keyPressed = e.getKeyChar();
                		if(e.getKeyCode() != KeyEvent.VK_SHIFT) {
                			undoManager.push(new InsertCharAction(model, keyPressed));	
                		}
                    	
                	}
                }
			}
		});
	}
	
	@Override
	public void updateCursorLocation(Location loc) {
		cursorPanel.removeAll();
		refreshCursor();
		cursorPanel.revalidate();
	}
	@Override
	public void updateText() {
		textPanel.removeAll();
		rowComponents.removeAll(rowComponents);
		refreshRows();
		textPanel.revalidate();
	}
	
	private void refreshCursor() {
		cursorComp = new CursorComponent(model.getCursorLocation().getX(), model.getCursorLocation().getY()); 
		cursorPanel.add(cursorComp);
	}
	private void refreshRows() {
		Iterator<String> rowIterator = model.allLines();
		int rowCounter = 0;
		while(rowIterator.hasNext()) {
			String row = rowIterator.next();
			RowComponent rowComp = new RowComponent(rowCounter++, row);
			rowComponents.add(rowComp);
			textPanel.add(rowComp);
		}
	}
	
	public void paintRange(LocationRange range) {
		selectionPanel.setSelectionRange(model.getSelectionRange());
		selectionPanel.repaint();
		selectionPanel.revalidate();
	}
	
	private List<Plugin> getPlugins(){
		return PluginFactory.getPlugins("plugins/");
	}
	
	private JMenuBar createMenu() {
		menuBar = new JMenuBar();		
		addFileMenu();
		addEditMenu();
		addMoveMenu();
		addPluginsMenu();
		
		return menuBar;
	}
	private void addEditMenu() {
		JMenu editMenu = new JMenu("Edit");
		
		Action undoAction = new AbstractAction() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				undoManager.undo();
				
			}
			
		};
		undoAction.putValue(Action.NAME, "Undo");
		Action redoAction = new AbstractAction() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				undoManager.redo();
				
			}
			
		};
		redoAction.putValue(Action.NAME, "Redo");
		Action copyAction = new AbstractAction() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				LocationRange range = model.getSelectionRange();
				String text = model.getTextFromSelection(range);
				clipboardStack.putTextToStack(text);
			}
			
		};
		copyAction.putValue(Action.NAME, "Copy");
		Action pasteAction = new AbstractAction() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String text = clipboardStack.readTextFromTopOfTheStack();
				model.insert(text);
			}
			
		};
		pasteAction.putValue(Action.NAME, "Paste");
		Action deleteSelectionAction = new AbstractAction() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				LocationRange range = model.getSelectionRange();
				model.deleteRange(range);
			}
			
		};
		deleteSelectionAction.putValue(Action.NAME, "Delete selection");		
		Action clearDocumentAction = new AbstractAction() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Location start = new Location(0,0);
				model.setCursorToEnd();
				Location end = new Location(model.getCursorLocation().getX(), model.getCursorLocation().getY());
				model.deleteRange(new LocationRange(start,end));
				model.setCursorLocation(0, 0);
			}
		};
		clearDocumentAction.putValue(Action.NAME, "Clear document");
		Action cutAction = new AbstractAction() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				LocationRange range = model.getSelectionRange();
				String text = model.getTextFromSelection(range);
				clipboardStack.putTextToStack(text);
				model.deleteRange(range);
				model.resetSelectionRange();
			}
		};
		cutAction.putValue(Action.NAME, "Cut");
		UndoMenuItem undoItem = new UndoMenuItem(undoAction);
		undoManager.attach(undoItem);
		RedoMenuItem redoItem = new RedoMenuItem(redoAction);
		undoManager.attach(redoItem);
		PasteMenuItem pasteItem = new PasteMenuItem(pasteAction);
		clipboardStack.attachObserver(pasteItem);
		CopyMenuItem copyItem = new CopyMenuItem(copyAction);
		model.attachSelectionObserver(copyItem);
		CutMenuItem cutItem = new CutMenuItem(cutAction);
		model.attachSelectionObserver(cutItem);
		JMenuItem pasteAndTakeItem = new JMenuItem("Paste and take");
		DeleteSelectionMenuItem deleteSelectionItem = new DeleteSelectionMenuItem(deleteSelectionAction);
		model.attachSelectionObserver(deleteSelectionItem);
		JMenuItem clearDocumentItem = new JMenuItem(clearDocumentAction);
		editMenu.add(undoItem);
		editMenu.add(redoItem);
		editMenu.add(cutItem);
		editMenu.add(copyItem);
		editMenu.add(pasteItem);
		editMenu.add(pasteAndTakeItem);
		editMenu.add(deleteSelectionItem);
		editMenu.add(clearDocumentItem);
		menuBar.add(editMenu);
	}
	
	private void addMoveMenu() {
		JMenu moveMenu = new JMenu("Move");
		Action cursorStartAction = new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				model.setCursorLocation(0, 0);
			}
		};
		cursorStartAction.putValue(Action.NAME, "Cursor to document start");
		JMenuItem cursorStartItem = new JMenuItem(cursorStartAction);
		Action cursorEndAction = new AbstractAction() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				model.setCursorToEnd();
			}
		};
		cursorEndAction.putValue(Action.NAME, "Cursor to document end");
		JMenuItem cursorEndItem = new JMenuItem(cursorEndAction);
		moveMenu.add(cursorStartItem);
		moveMenu.add(cursorEndItem);
		menuBar.add(moveMenu);
	}
	
	private void addFileMenu() {
		JFrame parentFrame = this;
		JMenu fileMenu = new JMenu("File");
		Action openAction = new AbstractAction() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setDialogTitle("Specify a file to save");   
				 
				int userSelection = fileChooser.showOpenDialog(parentFrame);
				 
				if (userSelection == JFileChooser.APPROVE_OPTION) {
				    File fileToRead = fileChooser.getSelectedFile();
				    
				    try {
				    	FileReader fr = new FileReader(fileToRead);
						List<String> lines = Files.readAllLines(fileToRead.toPath());
						String text = "";
						for(String line: lines) {
							text += (line + "\n");
							System.out.println(line);
						}
						Location start = new Location(0,0);
						model.setCursorToEnd();
						Location end = new Location(model.getCursorLocation().getX(), model.getCursorLocation().getY());
						model.deleteRange(new LocationRange(start,end));
						model.setCursorLocation(0, 0);
						model.insert(text);
				    }catch(Exception exc) {
				    	exc.printStackTrace();
				    }
				    
				}
				
			}
		};
		openAction.putValue(Action.NAME, "Open");
		
		JMenuItem openItem = new JMenuItem(openAction);
		
		Action saveAction = new AbstractAction() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setDialogTitle("Specify a file to save");   
				 
				int userSelection = fileChooser.showSaveDialog(parentFrame);
				 
				if (userSelection == JFileChooser.APPROVE_OPTION) {
				    File fileToSave = fileChooser.getSelectedFile();
				    
				    System.out.println("Save as file: " + fileToSave.getAbsolutePath());
				    try {
				    	FileWriter fw = new FileWriter(fileToSave);
						Iterator<String> linesIterator = model.allLines();
						while(linesIterator.hasNext()) {
							String line = linesIterator.next();
							fw.write(line + "\n");
						}
						fw.flush();
				    }catch(Exception exc) {
				    	exc.printStackTrace();
				    }
				    
				}
				
			}
		};
		saveAction.putValue(Action.NAME, "Save");
		
		JMenuItem saveItem = new JMenuItem(saveAction);
		
		Action exitAction = new AbstractAction() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		};
		exitAction.putValue(Action.NAME, "Exit");
		JMenuItem exitItem = new JMenuItem(exitAction);
		fileMenu.add(openItem);
		fileMenu.add(saveItem);
		fileMenu.add(exitItem);
		menuBar.add(fileMenu);
	}
	
	private void addPluginsMenu() {
		JMenu pluginsMenu = new JMenu("Plugins");
		
		List<Plugin> plugins = getPlugins();
		for(Plugin plugin: plugins) {
			JMenuItem capitalizedIzem = new JMenuItem(new AbstractAction(plugin.getName()) {
				@Override
				public void actionPerformed(ActionEvent e) {
					plugin.execute(model, undoManager, clipboardStack);
				}
			});
			pluginsMenu.add(capitalizedIzem);	
		}
		menuBar.add(pluginsMenu);
	}

}
