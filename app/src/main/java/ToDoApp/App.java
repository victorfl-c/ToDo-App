/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package ToDoApp;

import controller.ProjectController;
import java.sql.Connection;
import java.util.List;
import model.Project;
import util.ConnectionFactory;

public class App {

    public static void main(String[] args) {
       ProjectController projectController = new ProjectController();
       
       Project project = new Project();
       
       project.setName("Projeto teste");
       project.setDescription("Descrição");
       projectController.save(project);
       /*
       project.setId(1);
       project.setName("Novo nome do projeto");
       project.setDescription("Nova descrição");
       projectController.update(project);
       
       List<Project> projects = projectController.getAll();
       System.out.println("Total de projetos = " + projects.size());
       */
    }
}
