package com.jarias.armaspersonajes.app;

import com.jarias.armaspersonajes.mvc.Controller;
import com.jarias.armaspersonajes.mvc.Model;
import com.jarias.armaspersonajes.mvc.View;

public class App {
	public static void main(String[] args) {
		View view = new View();
		Model model = new Model();
		Controller controller = new Controller(view, model);
	}
}
