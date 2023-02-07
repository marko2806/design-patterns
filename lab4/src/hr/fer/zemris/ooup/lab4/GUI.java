package hr.fer.zemris.ooup.lab4;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JToolBar;

import hr.fer.zemris.ooup.lab4.geometry.Point;
import hr.fer.zemris.ooup.lab4.listeners.DocumentModelListener;
import hr.fer.zemris.ooup.lab4.renderers.G2DRendererImpl;
import hr.fer.zemris.ooup.lab4.renderers.Renderer;
import hr.fer.zemris.ooup.lab4.renderers.SVGRendererImpl;
import hr.fer.zemris.ooup.lab4.shapes.GraphicalObject;
import hr.fer.zemris.ooup.lab4.states.AddShapeState;
import hr.fer.zemris.ooup.lab4.states.EraserState;
import hr.fer.zemris.ooup.lab4.states.IdleState;
import hr.fer.zemris.ooup.lab4.states.SelectedShapeState;
import hr.fer.zemris.ooup.lab4.states.State;

public class GUI extends JFrame{
	private static final long serialVersionUID = 1L;
	private List<GraphicalObject> objects;
	private DocumentModel model;
	private State currentState;
	
	public GUI(List<GraphicalObject> objects) {
		this.objects = objects;
		this.model = DocumentModel.getInstance();
		this.currentState = new IdleState();
		setSize(500,500);
		
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());
		
		JToolBar toolbar = new JToolBar();
		
		Canvas canvas = new Canvas();
		
		for(GraphicalObject object: this.objects) {
			Action action = new AbstractAction(object.getShapeName()) {
				private static final long serialVersionUID = 1L;

				@Override
				public void actionPerformed(ActionEvent e) {
					if(currentState != null) {
						currentState.onLeaving();
					}
					currentState = new AddShapeState(model, object);
				}
			};
			toolbar.add(action);
		}
		
		Action selectAction = new AbstractAction("Selektiranje") {
			private static final long serialVersionUID = 1L;
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(currentState != null) {
					currentState.onLeaving();
				}
				currentState = new SelectedShapeState(model);
			}
		};
		toolbar.add(selectAction);
		

		Action eraseAction = new AbstractAction("Brisalo") {
			private static final long serialVersionUID = 1L;
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(currentState != null) {
					currentState.onLeaving();
				}
				currentState = new EraserState(model);
			}
		};
		
		Action svgExport = new AbstractAction("SVG Export") {
			
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				String fileName = JOptionPane.showInputDialog("File name");
				if(fileName != null) {
					SVGRendererImpl r = new SVGRendererImpl(fileName);
					for(GraphicalObject o: model.list()) {
						o.render(r);
					}
					try {
						r.close();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		};
		toolbar.add(svgExport);
		toolbar.add(eraseAction);
		toolbar.setRequestFocusEnabled(false);
		canvas.requestFocusInWindow();
		cp.add(toolbar, BorderLayout.NORTH);
		cp.addKeyListener(canvas);
		cp.addMouseListener(canvas);
		cp.addMouseMotionListener(canvas);
		cp.add(new Canvas(), BorderLayout.CENTER);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
	}
	

	
	public class Canvas extends JComponent implements DocumentModelListener, KeyListener, MouseListener, MouseMotionListener{

		private static final long serialVersionUID = 1L;

		public Canvas() {
			model.addDocumentModelListener(this);
			addKeyListener(this);
			setFocusable(true);
			requestFocusInWindow();
		}
	
		
		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2d = (Graphics2D)g;
			Renderer r = new G2DRendererImpl(g2d);
			for(GraphicalObject object: model.list()) {
				object.render(r);
				currentState.afterDraw(r, object);
			}
			currentState.afterDraw(r);
			requestFocusInWindow();
		}

		@Override
		public void documentChange() {
			repaint();
		}

		@Override
		public void mouseDragged(MouseEvent e) {
			Point mousePosition = new Point(e.getPoint().x, (int)e.getPoint().y - 30);
			currentState.mouseDragged(mousePosition);
		}

		@Override
		public void mouseMoved(MouseEvent e) {}

		@Override
		public void mouseClicked(MouseEvent e) {}

		@Override
		public void mousePressed(MouseEvent e) {
			Point mousePosition = new Point(e.getPoint().x, (int)e.getPoint().y - 30);
			currentState.mouseDown(mousePosition, e.isShiftDown(), e.isControlDown());
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			Point mousePosition = new Point(e.getPoint().x, (int)e.getPoint().y - 30);
			currentState.mouseUp(mousePosition, e.isShiftDown(), e.isControlDown());
		}

		@Override
		public void mouseEntered(MouseEvent e) {}

		@Override
		public void mouseExited(MouseEvent e) {}

		@Override
		public void keyTyped(KeyEvent e) {}

		@Override
		public void keyPressed(KeyEvent e) {
			currentState.keyPressed(e.getKeyCode());
		}

		@Override
		public void keyReleased(KeyEvent e) {}
	}

}
