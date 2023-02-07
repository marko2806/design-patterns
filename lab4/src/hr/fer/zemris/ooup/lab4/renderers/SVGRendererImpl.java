package hr.fer.zemris.ooup.lab4.renderers;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.ooup.lab4.geometry.Point;

public class SVGRendererImpl implements Renderer {

	private List<String> lines = new ArrayList<>();
	private String fileName;
	
	public SVGRendererImpl(String fileName) {
		this.fileName = fileName;
		lines.add("<svg  xmlns=\"http://www.w3.org/2000/svg\" xmlns:xlink=\"http://www.w3.org/1999/xlink\">");
	}

	public void close() throws IOException {
		lines.add("</svg>");
		FileWriter writer = new FileWriter(new File(fileName));
		
		for(String line: lines) {
			writer.write(line);
		}
		
		writer.close();
	}
	
	@Override
	public void drawLine(Point s, Point e) {
		StringBuilder sb = new StringBuilder();
		sb.append("<line ");
		sb.append("x1=\"" + s.getX() + "\" ");
		sb.append("y1=\"" + s.getY() + "\" ");
		sb.append("x2=\"" + e.getX() + "\" ");
		sb.append("y2=\"" + e.getY() + "\" ");
		sb.append("style=\"stroke:#0000FF;\"/>");
		lines.add(sb.toString());
	}

	@Override
	public void fillPoygon(Point[] points) {
		StringBuilder sb = new StringBuilder();
		sb.append("<polygon points=\" ");
		for(Point point: points) {
			sb.append(point.getX() + ","+ point.getY() +" ");
		}
		sb.append("\" ");
		sb.append("style=\"stroke:#FF0000; fill:#0000FF\"/>");
		lines.add(sb.toString());
	}

}